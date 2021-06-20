package atrea.packet.impl;

import atrea.main.Position;
import lombok.Getter;

public class MovementPacket {
    private @Getter Position target;

    public MovementPacket(Position target) {
        this.target = target;
    }
}