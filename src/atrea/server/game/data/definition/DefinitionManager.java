package atrea.server.game.data.definition;

import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class DefinitionManager {

    private static final String DEFINITION_PATH = "data/definitions/";

    private static EntityDefinition[] entityDefinitions = new EntityDefinition[10000];
    private static ItemDefinition[] itemDefinitions = new ItemDefinition[10000];
    private static RecipeDefinition[] recipeDefinitions;

    private static <T> T load(String definition, Class type, Gson gson) throws FileNotFoundException {
        FileReader reader = new FileReader(DEFINITION_PATH + definition + "Definitions.json");

        return (T) gson.fromJson(reader, type);
    }

    private static void loadDefinitions() {
        Gson gson = new Gson();

        long start = System.currentTimeMillis();

        try {
            entityDefinitions = load("Entity", EntityDefinition[].class, gson);
            itemDefinitions = load("item", ItemDefinition[].class, gson);
            // recipeDefinitions = load("recipe", recipeDefinitions.getClass(), gson);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("Loaded all definitions in " + (System.currentTimeMillis() - start) + "ms");
    }

    public static EntityDefinition getEntityDefinition(int id) {
        return entityDefinitions[id];
    }

    public static ItemDefinition getItemDefinition(int id) {
        return itemDefinitions[id];
    }

    public static RecipeDefinition getRecipeDefinition(int id) {
        return recipeDefinitions[id];
    }

    public static void initialize() {
        loadDefinitions();
    }
}