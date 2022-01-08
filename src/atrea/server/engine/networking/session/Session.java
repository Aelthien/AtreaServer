package atrea.server.engine.networking.session;

import atrea.server.engine.entities.Entity;
import atrea.server.engine.utilities.Delegate;
import atrea.server.engine.main.GameManager;
import atrea.server.engine.networking.databases.DatabaseManager;
import atrea.server.engine.networking.packet.MessageSender;
import atrea.server.engine.networking.packet.incoming.IncomingPacket;
import atrea.server.engine.networking.packet.RegisterDetails;
import atrea.server.engine.networking.packet.LoginDetails;
import atrea.server.engine.networking.packet.listener.PacketListeners;
import io.netty.channel.Channel;
import lombok.Getter;
import lombok.Setter;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

import static atrea.server.engine.networking.databases.NetworkOpcodes.*;
import static atrea.server.game.entities.EEntityType.*;

public class PlayerSession {
    private final int QUEUE_LIMIT = 10;

    private @Getter Entity player;
    private @Getter int userId;

    private final @Getter MessageSender messageSender;
    private final @Getter DatabaseManager databaseManager;

    private final @Getter Channel channel;

    private @Getter @Setter ESessionState sessionState;

    private final Queue<IncomingPacket> prioritizedIncomingPacketQueue = new ConcurrentLinkedQueue<>();
    private final Queue<IncomingPacket> incomingPacketQueue = new ConcurrentLinkedQueue<>();

    private final Map<Entity, Boolean> localEntities = new HashMap<>();

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
        System.out.println("Authorising");
        LoginResponse response = databaseManager.login(loginDetails);

        messageSender.sendLoginResponse(response.getCode());

        if (response.getCode() != LOGIN_SUCCESSFUL) {
            //future.addListener(ChannelFutureListener.CLOSE);
            databaseManager.closeConnection();
            System.out.println("Login failed");
            return;
        }

        player = GameManager.getEntityManager().createEntity(-1, Player);
        userId = response.getUserId();

        messageSender.setPlayer(player);

        player.setOnRemoveDelegate(new Delegate() {
            @Override public void execute() {
                requestLogOut();
            }
        });

        GameManager.getPlayerSessionManager().registerPlayerSession(player, this);
    }

    public void queuePacket(IncomingPacket incomingPacket) {
        System.out.println("Queue packet");
        int size = incomingPacketQueue.size() + prioritizedIncomingPacketQueue.size();

        if (size >= QUEUE_LIMIT)
            return;

        incomingPacketQueue.add(incomingPacket);
    }

    public void update() {
        messageSender.sendPing();
        processPacketQueues();
    }

    private void processPacket(IncomingPacket incomingPacket) {
        PacketListeners.getListener(incomingPacket.getCode()).processGamePacket(this, incomingPacket.getBuffer());
    }

    public void processPacketQueues() {

        if (incomingPacketQueue.isEmpty())
            return;

        int processed = 0;

        //System.out.println("Processing " + packetQueue.size() + " packets this tick");

        for (; processed < QUEUE_LIMIT; processed++) {
            IncomingPacket incomingPacket = incomingPacketQueue.poll();

            if (incomingPacket == null)
                break;

            processPacket(incomingPacket);
        }
    }

    public void addLocalEntity(Entity entity) {
        boolean needsUpdating = !localEntities.containsKey(entity);

        localEntities.put(entity, needsUpdating);
    }
}