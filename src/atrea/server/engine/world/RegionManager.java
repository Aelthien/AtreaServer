package atrea.server.game.world;

import atrea.server.game.ai.pathfinding.Tile;
import atrea.server.engine.utilities.Position;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Getter;

import java.io.*;

public class RegionManager {

    private static RegionManager instance = new RegionManager();

    private RegionManager() {

    }

    public static synchronized RegionManager getInstance() {
        return instance;
    }

    private static final int WORLD_DIMENSION = 256;
    private static final int REGION_DIMENSION = 64;
    private static final String REGION_PATH = "data/regions/";
    private static final String REGION_INDEX_PATH = REGION_PATH + "regions.index";

    private static @Getter Region[][] regions = new Region[WORLD_DIMENSION][WORLD_DIMENSION];

    public static Region getRegion(Position position) {
        return getRegion(position.getX(), position.getY());
    }

    public static Region getRegion(int x, int y) {
        Region region = regions[x][y];

        if (region == null)
            System.out.println("Null");

        if (!region.isLoaded())
            loadRegion(region);

        return region;
    }

    public static void initialise() {
        //generateTestRegion();
        System.out.println("Loading region indices.");
        long start = System.currentTimeMillis();

        try {
            File file = new File(REGION_INDEX_PATH);
            InputStream stream = new FileInputStream(file);

            ByteBuf buffer = Unpooled.buffer();
            buffer.writeBytes(stream.readAllBytes());

            int id;
            int mapId;
            int loadedRegions = 0;

            while (buffer.isReadable()) {
                id = buffer.readUnsignedShort();
                mapId = buffer.readUnsignedShort();

                int x = id / WORLD_DIMENSION;
                int y = id % WORLD_DIMENSION;

                regions[x][y] = new Region(id, mapId, x, y);

                loadedRegions++;
            }

            System.out.println("Loaded " + loadedRegions + " region indices in: " + (System.currentTimeMillis() - start) + "ms");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void generateTestRegion() {
        File file = new File(REGION_PATH + 0 + ".data");

        try {
            file.createNewFile();
            OutputStream stream = new FileOutputStream(file);
            ByteBuf buffer = Unpooled.buffer();

            for (int i = 0; i < REGION_DIMENSION * REGION_DIMENSION; i++) {
                buffer.writeByte(0);
                buffer.writeByte(1);
            }

            byte[] bytes = new byte[buffer.readableBytes()];
            buffer.readBytes(bytes);

            stream.write(bytes);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Region loadRegion(Region region) {

        try {
            File file = new File(REGION_PATH + region.getMapId() + ".data");
            InputStream stream = new FileInputStream(file);

            ByteBuf buffer = Unpooled.buffer();
            buffer.writeBytes(stream.readAllBytes());

            Tile[][][] tiles = new Tile[1][REGION_DIMENSION][REGION_DIMENSION];

            int level = 0;

            int xTile = 0;
            int yTile = 0;

            while (buffer.isReadable()) {
                int type = buffer.readByte();
                boolean walkable = buffer.readByte() == 1 ? true : false;

                tiles[level][xTile][yTile] = new Tile(xTile, yTile, 0, type, walkable);

                if (xTile == REGION_DIMENSION - 1) {
                    xTile = 0;
                    yTile++;
                }
                else
                    xTile++;
            }

            region.setTiles(tiles);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return region;
    }
}