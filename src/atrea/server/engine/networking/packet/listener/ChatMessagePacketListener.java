package atrea.server.engine.networking.packet.listener;

import atrea.server.engine.networking.session.Session;
import io.netty.buffer.ByteBuf;

import java.nio.charset.Charset;

public class ChatMessagePacketListener implements IPacketListener {

    @Override
    public void process(Session session, ByteBuf buffer) {
        EChatChannel channel = EChatChannel.values()[buffer.readByte()];
        int channelId = buffer.readByte();
        int messageLength = buffer.readByte();
        String message = buffer.readCharSequence(messageLength, Charset.defaultCharset()).toString();


    }
}