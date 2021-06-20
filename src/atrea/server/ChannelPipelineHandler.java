package atrea.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

public class ChannelPipelineHandler extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel)
    {
        final ChannelPipeline pipeline = socketChannel.pipeline();

        socketChannel.attr(PlayerSessions.getSessionKey()).setIfAbsent(new PlayerSession(socketChannel));

        pipeline.addLast(new PacketHandler());
    }
}