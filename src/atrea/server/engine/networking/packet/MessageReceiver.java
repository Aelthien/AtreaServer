package atrea.server.engine.networking.packet;

import atrea.server.engine.main.GameEngine;
import atrea.server.engine.main.GameManager;
import atrea.server.engine.networking.packet.incoming.IncomingPacket;
import atrea.server.engine.networking.session.Session;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.ReferenceCountUtil;

import java.sql.SQLException;

import static atrea.server.engine.networking.packet.incoming.IncomingPacketConstants.*;

@Sharable
public class MessageReceiver extends SimpleChannelInboundHandler<ByteBuf> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws SQLException {
        msg.retain();

        Session session = ctx.channel().attr(GameManager.getSessionManager().getSessionKey()).get();
        
        if (session == null)
            throw new IllegalStateException("session == null");

        int size = msg.readInt();
        int code = msg.readByte();

        ByteBuf buffer = msg.slice(5, size);
        buffer.retain();

        //if (code == PING)
        if (code == LOG_IN)
            session.authorize(new LoginDetails(ctx, buffer));
        else if (code == REGISTRATION)
            session.register(new RegisterDetails(ctx, buffer));
        else if (code == PING)
            session.getMessageSender().sendPing();
        else
            session.queuePacket(new IncomingPacket(code, buffer));

        ReferenceCountUtil.release(msg);
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {

    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx)
    {
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
    {
        cause.printStackTrace();
        ctx.close();
    }
}