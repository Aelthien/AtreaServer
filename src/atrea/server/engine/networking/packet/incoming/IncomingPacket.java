package atrea.server.engine.networking.packet;

import io.netty.buffer.ByteBuf;

public class IncomingPacket extends Packet {

    public IncomingPacket(int code, ByteBuf buffer) {
        super(code, buffer);
    }
}