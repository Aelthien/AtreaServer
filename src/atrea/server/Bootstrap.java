package atrea.server;

import atrea.game.GameEngine;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

public class Bootstrap
{
    private final int port = 45000;
    private GameEngine game;

    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;

    private final ChannelInitializer<SocketChannel> channelInitializer = new ChannelPipelineHandler();

    public void bind() throws InterruptedException
    {
        System.out.println("Binding...");
        bossGroup = new NioEventLoopGroup();
        workerGroup = new NioEventLoopGroup();

        try
        {
            ServerBootstrap bootstrap = new ServerBootstrap();

            bootstrap.group(bossGroup, workerGroup);
            bootstrap.channel(NioServerSocketChannel.class);
            bootstrap.localAddress(new InetSocketAddress(port));

            bootstrap.childHandler(channelInitializer)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            System.out.println("Server bound.");

            ChannelFuture future = bootstrap.bind().sync();
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
        }
    }

    public void stop() throws InterruptedException {
        bossGroup.shutdownGracefully().sync();
        workerGroup.shutdownGracefully().sync();
    }
}