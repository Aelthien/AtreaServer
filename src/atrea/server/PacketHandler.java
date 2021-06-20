package atrea.server;

import atrea.packet.*;
import atrea.packet.impl.LoginDetails;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.ReferenceCountUtil;

import java.sql.SQLException;

import static atrea.server.IncomingPacketConstants.*;

@Sharable
public class PacketHandler extends SimpleChannelInboundHandler<ByteBuf>
{
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws SQLException {
        msg.retain();

        PlayerSession playerSession = ctx.channel().attr(PlayerSessions.getSessionKey()).get();
        
        if (playerSession == null)
            throw new IllegalStateException("session == null");

        int size = msg.readInt();
        int code = msg.readByte();

        ByteBuf buffer = msg.slice(5, size);
        buffer.retain();

        if (code == PING)
            playerSession.getMessageSender().sendPing();
        else if (code == LOG_IN)
            playerSession.authorize(new LoginDetails(ctx, buffer));
        else if (code == REGISTRATION)
            playerSession.register(new RegisterDetails(ctx, buffer));
        else {
            playerSession.queuePacket(new GamePacket(code, buffer));
        }

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