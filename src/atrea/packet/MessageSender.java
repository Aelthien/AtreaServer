package atrea.packet;

import atrea.game.content.items.Item;
import atrea.game.content.items.ItemContainer;
import atrea.game.content.items.ItemContainer.EContainerType;
import atrea.game.content.skills.ESkill;
import atrea.game.content.skills.Skill;
import atrea.game.entity.*;
import atrea.game.entity.components.impl.InventoryComponent;
import atrea.game.entity.components.impl.SkillsComponent;
import atrea.game.entity.components.impl.StatusComponent;
import atrea.main.Position;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.Channel;

import java.nio.charset.Charset;

import static atrea.server.OutgoingPacketConstants.*;

public class MessageSender {

    private final Channel channel;
    private Entity player;

    public MessageSender(Channel channel) {
        this.channel = channel;
    }

    public void sendChatMessages(String[] messages) {
        ByteBuf buffer = PooledByteBufAllocator.DEFAULT.buffer();

        buffer.retain();
        buffer.writeByte(11);
        buffer.writeByte(messages.length);

        for (String message : messages) {
            buffer.writeByte(message.length());
            buffer.writeCharSequence(message, Charset.defaultCharset());
        }

        send(buffer);
    }

    public void sendChatMessage(String message) {
        sendChatMessages(new String[]{message});
    }

    public void sendHealth(int health) {

    }

    public ByteBuf getBuffer() {
        ByteBuf buffer = PooledByteBufAllocator.DEFAULT.buffer();
        buffer.retain();

        return buffer;
    }

    public void sendLoginResponse(int response) {
        ByteBuf buffer = getBuffer();

        buffer.writeByte(LOGIN_RESPONSE);
        buffer.writeByte(response);

        send(buffer);
        /*ChannelFuture future = channel.writeAndFlush(buffer);

        if (response != NetworkOpcodes.LOGIN_SUCCESSFUL)
            future.addListener(ChannelFutureListener.CLOSE);

        future.awaitUninterruptibly();

        buffer.release();*/
    }

    public void sendRegisterResponse(int response) {
        ByteBuf buffer = getBuffer();

        buffer.writeByte(REGISTER_RESPONSE);
        buffer.writeByte(response);

        send(buffer);
    }

    public void sendHealth() {
        ByteBuf buffer = getBuffer();

        buffer.writeByte(30);
        buffer.writeInt(player.getComponent(StatusComponent.class).getHealth());

        send(buffer);
    }

    public void sendName() {
        ByteBuf buffer = getBuffer();

        String name = player.getName();

        System.out.println("Sending name: " + name);
        buffer.writeByte(SEND_NAME);
        buffer.writeByte(name.length());

        buffer.writeCharSequence(name, Charset.defaultCharset());

        send(buffer);
    }

    public void openInterface(int widgetCode) {
        ByteBuf buffer = getBuffer();

        buffer.writeByte(OPEN_INTERFACE);

        buffer.writeInt(widgetCode);

        send(buffer);
    }

    public void sendRegion() {
        ByteBuf buffer = getBuffer();

        buffer.writeByte(SEND_REGION);

        buffer.writeInt(player.getTransform().getRegionPosition().getX());
        buffer.writeInt(player.getTransform().getRegionPosition().getY());

        send(buffer);
    }

    public void sendSkill(ESkill skill) {
        ByteBuf buffer = getBuffer();

        buffer.writeByte(31);

        Skill skillToSend = player.getComponent(SkillsComponent.class).getSkill(skill);

        buffer.writeByte(skillToSend.getSkill().ordinal());
        buffer.writeByte(skillToSend.getCurrentLevel());
        buffer.writeByte(skillToSend.getMaxLevel());

        send(buffer);
    }

    public void sendUpdateEntity(Entity entity, boolean teleport) {
        ByteBuf buffer = getBuffer();

        buffer.writeByte(UPDATE_ENTITY);
        buffer.writeInt(entity.getEntityType().ordinal());
        buffer.writeInt(entity.getUid());
        buffer.writeInt(entity.getTransform().getPosition().getX());
        buffer.writeInt(entity.getTransform().getPosition().getY());
        buffer.writeByte(teleport ? 1 : 0);

        send(buffer);
    }

    public void sendInventory() {
        sendItemContainer(player.getComponent(InventoryComponent.class).getInventory());
    }

    public void sendEquipment() {
        //sendItemContaier(player.getEquipment());
    }

    public void sendBank() {

    }

    public void sendItemContainer(ItemContainer container) {
        Item[] items = container.getItems();

        for (int i = 0; i < items.length; i++) {
            //sendItem(items[i], i, container.getContainerType());
        }
    }

    public void sendItem(Item item, int slot, EContainerType container) {
        ByteBuf buffer = getBuffer();

        buffer.writeByte(SEND_ITEM);
        buffer.writeByte(container.ordinal());
        buffer.writeInt(slot);
        buffer.writeInt(item.getId());
        System.out.println(item.getId());
        buffer.writeInt(item.getAmount());

        send(buffer);
    }

    public void sendSkills() {
        for (ESkill skill : ESkill.values()) {
            sendSkill(skill);
        }
    }

    private void send(ByteBuf buffer) {
        ByteBuf newBuffer = getBuffer();

        int length = buffer.readableBytes();

        newBuffer.writeInt(length);
        newBuffer.writeBytes(buffer);

        channel.writeAndFlush(newBuffer);

        buffer.release();
        newBuffer.release();
    }

    public void setPlayer(Entity player) {
        this.player = player;
    }

    public void sendMove(Position position, boolean teleport, boolean pathEnd) {
        ByteBuf buffer = PooledByteBufAllocator.DEFAULT.buffer();

        buffer.writeByte(SEND_POSITION);
        buffer.writeInt(position.getX());
        buffer.writeInt(position.getY());
        buffer.writeByte(teleport == true ? 1 : 0);
        buffer.writeByte(pathEnd == true ? 1 : 0);

        send(buffer);
    }

    /*public void sendPath(List<Position> positions, boolean reset) {
        ByteBuf buffer = PooledByteBufAllocator.DEFAULT.buffer();

        int number = positions.size();

        buffer.writeByte(SEND_POSITIONS);
        buffer.writeByte(reset == true ? 1 : 0);

        for (int i = 0; i < number; i++) {
            buffer.writeInt(positions.get(i).getX());
            buffer.writeInt(positions.get(i).getY());
            buffer.writeByte(0);
            buffer.writeByte(i == number ? 1 : 0);
        }

        send(buffer);
    }*/

    public void sendPing() {
        ByteBuf buffer = getBuffer();

        buffer.writeByte(SEND_PING);

        send(buffer);
    }

    public void sendLogOut() {
        ByteBuf buffer = getBuffer();

        buffer.writeByte(LOG_OUT);

        send(buffer);
    }
}