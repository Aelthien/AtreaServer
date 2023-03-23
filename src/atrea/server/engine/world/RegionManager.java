package atrea.server.engine.world;

import atrea.server.engine.main.GameManager;
import atrea.server.engine.utilities.SpatialHashGrid;
import atrea.server.game.ai.pathfinding.Tile;
import atrea.server.engine.utilities.Position;
import atrea.server.game.entities.EEntityType;
import atrea.server.game.entities.ecs.Entity;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.Unpooled;
import lombok.Getter;

import java.io.*;

public class RegionManager {

    private static RegionManager instance = new RegionManager();

    private String entityDataExtension = ".entitydata";
    private String mapDataExtension = ".data";

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

    private static @Getter SpatialHashGrid entityGrid = new SpatialHashGrid(REGION_DIMENSION, WORLD_DIMENSION, WORLD_DIMENSION);

    public static Region getRegion(Position position) {
        return getRegion(position.getX(), position.getY());
    }

    public static Region getRegion(int x, int y) {
        Region region = regions[x][y];

        if (region == null) {
            System.out.println(String.format("Region: x: %d y: %d is null", x, y));
            return null;
        }

        return region;
    }

    public static void initialise() {
        //generateTestRegion();
        loadRegionIndices();

    }

    private static void loadRegionIndices() {
        System.out.println("Loading region indices.");
        long start = System.currentTimeMillis();

        try {
            File file = new File(REGION_INDEX_PATH);
            InputStream stream = new FileInputStream(file);

            ByteBuf buffer = PooledByteBufAllocator.DEFAULT.buffer();
            buffer.writeBytes(stream.readAllBytes());

            int id;
            int mapId;
            int loadedRegions = 0;

            while (buffer.isReadable()) {
                id = buffer.readUnsignedShort();
                mapId = buffer.readUnsignedShort();

                loadRegion(id, mapId);

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

    private static void loadRegion(int id, int mapId) {
        try {
            File tileDataFile = new File(REGION_PATH + mapId + ".tiledata");
            File entityDataFile = new File(REGION_PATH + mapId + getInstance().entityDataExtension);

            InputStream tileDataStream = new FileInputStream(tileDataFile);
            InputStream entityDataStream = new FileInputStream(entityDataFile);

            ByteBuf tileDataBuffer = Unpooled.buffer().writeBytes(tileDataStream.readAllBytes());
            ByteBuf entityDataBuffer = Unpooled.buffer().writeBytes(entityDataStream.readAllBytes());

            Tile[][][] tiles = new Tile[1][REGION_DIMENSION][REGION_DIMENSION];

            for (int i = 0; i < REGION_DIMENSION * REGION_DIMENSION; i++) {
                int level = 0;
                int x = i % REGION_DIMENSION;
                int y = i / REGION_DIMENSION;

                int type = tileDataBuffer.readByte();
                boolean walkable = tileDataBuffer.readByte() == 1 ? true : false;

                tiles[level][x][y] = new Tile(x, y, level, type, walkable);
            }

            int regionX = id % WORLD_DIMENSION;
            int regionY = id / WORLD_DIMENSION;

            Region region = new Region(id, mapId, regionX, regionY, tiles, new EntitySpawnData[1]);

            regions[regionX][regionY] = region;

            while (entityDataBuffer.readableBytes() > 0) {
                int entityId = entityDataBuffer.readUnsignedShort();
                EEntityType entityType = EEntityType.integerToEnum(entityDataBuffer.readByte());
                int x = entityDataBuffer.readByte();
                int y = entityDataBuffer.readByte();
/*
                Entity entity = GameManager.getEntityManager().createEntity(entityType, entityId);

                if (entityType != EEntityType.Player) {
                    entity.setNeverSpawn(true);
                }*/

//                entityGrid.insert(new Position(x, y), entityId);

//                GameManager.getSystemManager().getTransformSystem().setTransform(entityId, new Position(x, y, 0), true, false, false, false);
            }
/*
            int objects = buffer.readInt();

            for (int i = 0; i < objects; i++) {
                int entityId = buffer.readInt();
                int definitionId = buffer.readInt();
                int type = buffer.readByte();
                int entityX = buffer.readByte();
                int entityY = buffer.readByte();
                int level = buffer.readByte();

                GameManager.getEntityManager().createEntity(entityId, definitionId, EEntityType.integerToEnum(type), entityX, entityY, level);
            }

 */

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}