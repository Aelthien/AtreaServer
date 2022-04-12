package atrea.server.engine.networking.packet;

import io.netty.buffer.ByteBuf;
import lombok.Getter;

public class Packet {
    protected @Getter int code;
    protected @Getter ByteBuf buffer;
}