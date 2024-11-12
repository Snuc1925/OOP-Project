package tile;

import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;
import java.util.Objects;

import static utils.Constants.Screen.*;
import static utils.Constants.Player.*;
import entities.Player;
import utils.Constants;

public class TileManager {

    public Boolean[][] tile;

    public TileManager(Player player) {
        tile = new Boolean[MAX_WORLD_ROW][MAX_WORLD_COL];
        for (int i = 0; i < MAX_WORLD_ROW; i++)
            for (int j = 0; j < MAX_WORLD_COL; j++)
                tile[i][j] = false;
    }

    public boolean isWall(int row, int col) {
        return tile[row][col];
    }

}
