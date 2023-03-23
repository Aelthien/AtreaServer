package atrea.server.game.entities.ecs;

import atrea.server.engine.main.GameManager;
import atrea.server.game.content.interactions.EInteractionOption;
import atrea.server.game.entities.EntityStateInteractions;
import atrea.server.game.entities.ecs.command.EntityScript;
import atrea.server.game.entities.ecs.command.JsonManager;
import atrea.server.game.entities.ecs.command.Script;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.sun.security.auth.NTDomainPrincipal;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import lombok.Getter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static atrea.server.game.entities.ecs.EComponentType.INTERACTION;

public class InteractionComponent extends EntityComponent {

    private @Getter EntityStateInteractions[] interactions;
    private @Getter List<String> commands = new ArrayList<>();

    public InteractionComponent(Entity parent, String[] data) {
        super(parent);

        commands.add("{\"id\":0,\"duration\":20,\"message\":\"Using this entity\"}");
    }

    @Override public EComponentType getComponentType() {
        return INTERACTION;
    }

    public boolean interact(int interactingEntityId, EInteractionOption option) {
        int optionIndex = getOptionIndex(option);

        if (optionIndex == -1)
            return false;

        try {
        if (optionIndex == 0) {
            JsonReader reader = JsonManager.getGson(commands.get(0));
            reader.beginObject();
            System.out.println(reader.nextInt());
            //GameManager.getSystemManager().getSpeechSystem().setSpeech(id, reader.nextInt(), reader.nextString());
        }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    private int getOptionIndex(EInteractionOption option) {
        EntityStateInteractions stateInteractions = interactions[parent.getState()];

        for (int i = 0; i < stateInteractions.getInteractions().length; i++) {
            if (stateInteractions.getInteractions()[i] == option){
                return i;
            }
        }

        return -1;
    }
}