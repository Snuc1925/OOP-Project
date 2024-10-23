package entities;

import gamestates.Playing;

import static utils.Constants.Player.*;
import static utils.Constants.Screen.*;
public class Player extends Entity {
    public int worldX, worldY;

    public Player(Playing playing) {
        super("Player", "player/down1", playing, PLAYER_IMAGE_WIDTH, PLAYER_IMAGE_HEIGHT);
        setDefaultValues();
    }

    public void setDefaultValues() {
        worldX = TILE_SIZE * 23;
        worldY = TILE_SIZE * 21;


    }


}
