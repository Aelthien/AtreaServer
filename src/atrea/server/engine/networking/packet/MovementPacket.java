package atrea.server.engine.networking.packet;

import lombok.Getter;

import javax.swing.text.Position;

public class MovementPacket {
    private @Getter
    Position target;

    public MovementPacket(Position target) {
        this.target = target;
    }
}