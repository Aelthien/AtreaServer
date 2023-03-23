package atrea.server.engine.networking.packet;

import atrea.server.engine.accounts.Account;
import atrea.server.game.entities.ecs.Entity;
import atrea.server.game.entities.ecs.EntityManager;
import atrea.server.engine.main.GameManager;
import atrea.server.engine.networking.packet.outgoing.OutgoingPacket;
import atrea.server.game.entities.ecs.ChatMessage;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.Channel;
import lombok.Setter;

import java.nio.charset.Charset;

import static atrea.server.engine.networking.packet.outgoing.OutgoingPacketConstants.*;

public class MessageSender {

    private final Channel channel;
    private @Setter Account account;
    private EntityManager entityManager;
    private long serverTime;

    public MessageSender(Channel channel) {
        this.channel = channel;
        this.entityManager = GameManager.getEntityManager();
    }

    public void sendChatMessage(ChatMessage message) {
        ByteBuf buffer = getBuffer();

        buffer.writeByte(CHAT_MESSAGE);
        buffer.writeByte(message.getRank().ordinal());
        buffer.writeCharSequence(message.getClanTag(), Charset.defaultCharset());
        buffer.writeByte(message.getMessage().length());
        buffer.writeCharSequence(message.getMessage(), Charset.defaultCharset());

        send(buffer);
    }

    public ByteBuf getBuffer() {
        ByteBuf buffer = PooledByteBufAllocator.DEFAULT.buffer();
        buffer.retain();

        return buffer;
    }

    public void sendLoginResponse(int response) {
        ByteBuf buffer = getBuffer();

        System.out.println("Send login response");
        buffer.writeByte(LOGIN_RESPONSE);
        buffer.writeByte(response);

        send(buffer);
    }

    public void sendRegisterResponse(int response) {
        ByteBuf buffer = getBuffer();

        buffer.writeByte(REGISTER_RESPONSE);
        buffer.writeByte(response);

        send(buffer);
    }

    public void openInterface(int widgetCode) {
        ByteBuf buffer = getBuffer();

        buffer.writeByte(OPEN_INTERFACE);
        buffer.writeInt(widgetCode);

        send(buffer);
    }

    public void sendRegion(int regionX, int regionY) {
        ByteBuf buffer = getBuffer();

        buffer.writeByte(SEND_REGION);

        buffer.writeInt(regionX);
        buffer.writeInt(regionY);

        send(buffer);
    }

    private void send(ByteBuf buffer) {
        ByteBuf newBuffer = getBuffer();

        int length = buffer.readableBytes();

        newBuffer.writeInt(length);
        newBuffer.writeBytes(buffer);

        buffer.release();

        channel.writeAndFlush(newBuffer);

        newBuffer.release();
    }

    public void send(OutgoingPacket outgoingPacket) {
        send(outgoingPacket.getBuffer());
    }

    public void sendLogOut() {
        ByteBuf buffer = getBuffer();

        buffer.writeByte(LOG_OUT);

        send(buffer);
    }

    public void sendUpdatePlayerLocalEntities(Entity player) {
        ByteBuf buffer = getBuffer();

        buffer.writeByte(UPDATE_ENTITIES);

        if (entityManager.getEntityUpdating().updatePlayerLocalEntities(player, buffer)) {
            send(buffer);
        } else
            buffer.release();
    }

    public void sendTickData() {
        ByteBuf buffer = getBuffer();

        buffer.writeByte(SEND_TICK);

        send(buffer);
    }

    public void sendPing() {
        ByteBuf buffer = getBuffer();

        buffer.writeByte(SEND_PING);

        long time = System.currentTimeMillis();

        buffer.writeLong(time);

        send(buffer);
    }
}