package atrea.server.engine.networking.packet.outgoing;

public class PingPacket extends OutgoingPacket {


    @Override public int getCode() {
        return 0;
    }
}