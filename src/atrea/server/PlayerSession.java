package atrea.server;

import atrea.game.entity.Entity;
import atrea.game.entity.Delegate;
import atrea.game.entity.Player;
import atrea.game.world.World;
import atrea.net.database.DatabaseManager;
import atrea.packet.MessageSender;
import atrea.packet.GamePacket;
import atrea.packet.RegisterDetails;
import atrea.packet.impl.LoginDetails;
import atrea.packet.listener.PacketListeners;
import io.netty.channel.Channel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import static atrea.net.database.NetworkOpcodes.*;

public class PlayerSession {
    private final int QUEUE_LIMIT = 10;

    private @Getter
    Player player;
    private final @Getter
    MessageSender messageSender;
    private final @Getter
    DatabaseManager databaseManager;

    private final @Getter
    Channel channel;

    private @Getter
    @Setter
    ESessionState sessionState;

    private final Queue<GamePacket> prioritizedPacketQueue = new ConcurrentLinkedQueue<>();
    private final Queue<GamePacket> packetQueue = new ConcurrentLinkedQueue<>();

    private final List<Entity> localPlayers = new ArrayList<>();
    private final List<Entity> localEntities = new ArrayList<>();

    private long lastPacketReceived;

    public PlayerSession(Channel channel) {
        this.channel = channel;
        this.messageSender = new MessageSender(channel);
        this.databaseManager = new DatabaseManager();
    }

    public void register(RegisterDetails registerDetails) {
        int response = databaseManager.register(registerDetails);
    }

    public void requestLogOut() {
        sessionState = ESessionState.REQUESTED_LOG_OUT;
        getMessageSender().sendLogOut();
    }

    public void authorize(LoginDetails loginDetails) {
        int response = databaseManager.login(loginDetails);

        System.out.println(loginDetails.getEmail());
        messageSender.sendLoginResponse(response);

        //ChannelFuture future = channel.writeAndFlush(response);

        if (response != LOGIN_SUCCESSFUL) {
            //future.addListener(ChannelFutureListener.CLOSE);
            databaseManager.closeConnection();
            System.out.println("Login fail");
            return;
        }

        player = World.addPlayer();

        messageSender.setPlayer(player);

        player.setOnRemoveDelegate(new Delegate() {
            @Override public void execute() {
                requestLogOut();
            }
        });

        PlayerSessions.registerPlayerSession(player, this);
    }

    public void queuePacket(GamePacket packet) {
        int size = packetQueue.size() + prioritizedPacketQueue.size();

        if (size >= QUEUE_LIMIT)
            return;

        packetQueue.add(packet);
    }

    public void update() {
        processPacketQueues();
    }

    private void processPacket(GamePacket packet) {
        PacketListeners.getListener(packet.getCode()).processGamePacket(this, packet.getBuffer());
    }

    public void processPacketQueues() {

        if (packetQueue.isEmpty())
            return;

        int processed = 0;

        //System.out.println("Processing " + packetQueue.size() + " packets this tick");

        for (; processed < QUEUE_LIMIT; processed++) {
            GamePacket packet = packetQueue.poll();

            if (packet == null)
                break;

            processPacket(packet);
        }

    }
}