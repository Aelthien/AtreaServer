package atrea.server.engine.networking.packet.incoming;

import atrea.server.engine.networking.packet.Packet;
import io.netty.buffer.ByteBuf;

public class IncomingPacket extends Packet {

    public IncomingPacket(int code, ByteBuf buffer) {
        this.code = code;
        this.buffer = buffer;
    }
}