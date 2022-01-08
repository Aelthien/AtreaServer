package atrea.server.engine.networking.io;

import atrea.server.engine.main.GameManager;
import atrea.server.engine.networking.packet.PacketHandler;
import atrea.server.engine.networking.session.PlayerSession;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

public class ChannelPipelineHandler extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) {
        final ChannelPipeline pipeline = socketChannel.pipeline();

        socketChannel.attr(GameManager.getPlayerSessionManager().getSessionKey()).setIfAbsent(new PlayerSession(socketChannel));

        pipeline.addLast(new PacketHandler());
    }
}