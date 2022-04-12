package atrea.server.engine.networking.packet.outgoing;

import atrea.server.engine.networking.packet.Packet;
import io.netty.buffer.PooledByteBufAllocator;

public abstract class OutgoingPacket extends Packet {

    public OutgoingPacket() {
        buffer = PooledByteBufAllocator.DEFAULT.buffer();
        buffer.writeByte(getCode());
    }

    public abstract int getCode();
}