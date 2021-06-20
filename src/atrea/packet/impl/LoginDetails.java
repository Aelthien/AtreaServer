package atrea.packet.impl;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.Getter;

import java.nio.charset.Charset;

public class LoginDetails
{
    private @Getter ChannelHandlerContext ctx;
    private @Getter String email;
    private @Getter String password;

    public LoginDetails(ChannelHandlerContext ctx, ByteBuf buffer)
    {
        this.ctx = ctx;

        int length = buffer.readUnsignedByte();
        String email = (String) buffer.readCharSequence(length, Charset.defaultCharset());

        length = buffer.readUnsignedByte();
        String password = (String) buffer.readCharSequence(length, Charset.defaultCharset());

        this.email = email;
        this.password = password;
    }
}