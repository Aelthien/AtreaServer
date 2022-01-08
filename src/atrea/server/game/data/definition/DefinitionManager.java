package atrea.game.data.definition;

import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class DefinitionManager {

    private static final String DEFINITION_PATH = "data/definitions/";

    private static ObjectDefinition[] objectDefinitions;
    private static ItemDefinition[] itemDefinitions;
    private static NPCDefinition[] npcDefinitions;
    private static RecipeDefinition[] recipeDefinitions;

    private static <T> T load(String definition, Class type, Gson gson) throws FileNotFoundException {
        FileReader reader = new FileReader(DEFINITION_PATH + definition + "_definitions.json");

        return (T) gson.fromJson(reader, type);
    }

    private static void loadDefinitions() {
        Gson gson = new Gson();

        long start = System.currentTimeMillis();

        //try {
            //objectDefinitions = load("object", objectDefinitions.getClass(), gson);
            //itemDefinitions = load("item", itemDefinitions.getClass(), gson);
            //npcDefinitions = load("npc", npcDefinitions.getClass(), gson);
            //recipeDefinitions = load("recipe", recipeDefinitions.getClass(), gson);
        /*} /*catch (FileNotFoundException e) {
            e.printStackTrace();
        }*/

        //System.out.println("Loaded all definitions in " + (System.currentTimeMillis() - start) + "ms");
    }

    public static ObjectDefinition getObjectDefinition(int id) {
        return objectDefinitions[id];
    }

    public static ItemDefinition getItemDefinition(int id) {
        return itemDefinitions[id];
    }

    public static NPCDefinition getNPCDefinition(int id) {
        return npcDefinitions[id];
    }

    public static RecipeDefinition getRecipeDefinition(int id) {
        return recipeDefinitions[id];
    }

    public static void initialize() {
        loadDefinitions();
    }
}