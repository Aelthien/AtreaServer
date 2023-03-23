package atrea.server.engine.networking.channels;

import atrea.server.engine.main.GameManager;
import atrea.server.engine.networking.packet.MessageReceiver;
import atrea.server.engine.networking.session.Session;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

public class ChannelPipelineHandler extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) {
        final ChannelPipeline pipeline = socketChannel.pipeline();

        socketChannel.attr(GameManager.getSessionManager().getSessionKey()).setIfAbsent(new Session(socketChannel));

        pipeline.addLast(new MessageReceiver());
    }
}