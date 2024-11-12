package map;

import tile.TileLayer;
import tile.TileManager;
import tile.TileSet;

import java.awt.*;
import java.util.ArrayList;

import entities.Player;

import static utils.Constants.Screen.*;

public class GameMap {

    private final int mapWidth;
    private final int mapHeight;

    ArrayList<TileSet> tileSetList;
    ArrayList<TileLayer> map;

    public GameMap(int mapWidth , int mapHeight)
    {
        map = new ArrayList<>();
        tileSetList = new ArrayList<>();

        this.mapWidth = mapWidth * SCALE;
        this.mapHeight = mapHeight * SCALE;
    }

    public void render(Graphics2D g2, Player player)
    {
        for (int i = 0; i < map.size(); i++) {
            TileLayer layer = map.get(i);
            layer.render(g2, player);

        }
    }

    public void buildTileManager(TileManager tileManager) {
        TileLayer layer = map.get(1);
        for (int i = 0; i < MAX_WORLD_ROW; i++)
            for (int j = 0; j < MAX_WORLD_COL; j++) {
                if (layer.getTileData(i, j) != 0) {
                    tileManager.tile[i][j] = true;
                }
            }
    }


    public int getMapWidth() {
        return mapWidth;
    }

    public int getMapHeight() {
        return mapHeight;
    }
}