package entities;

import static utils.Constants.Screen.*;
public class Player {
    public int worldX, worldY;

    public Player() {
        setDefaultValues();
    }

    public void setDefaultValues() {
        worldX = TILE_SIZE * 23;
        worldY = TILE_SIZE * 21;
    }
}
