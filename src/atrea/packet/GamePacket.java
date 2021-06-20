package atrea.packet;

import io.netty.buffer.ByteBuf;
import lombok.Getter;

public class GamePacket {

    private @Getter int code;
    private @Getter ByteBuf buffer;

    public GamePacket(int code, ByteBuf buffer) {
        this.code = code;
        this.buffer = buffer;
    }
}