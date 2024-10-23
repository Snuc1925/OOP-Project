package entities;

import gamestates.Playing;

import java.awt.*;

import static utils.Constants.Player.*;
import static utils.Constants.Screen.*;
public class Player extends Entity {
    public int worldX, worldY;
    public int speed;

    public Player(Playing playing) {
        super("Player", "player/down1", playing, PLAYER_IMAGE_WIDTH, PLAYER_IMAGE_HEIGHT);
        setDefaultValues();
    }

    public void setDefaultValues() {
        worldX = TILE_SIZE * 23;
        worldY = TILE_SIZE * 21;
        speed = 6;
    }

    public void goUp() {
        worldY -= speed;
    }

    public void goDown() {
        worldY += speed;
    }

    public void goLeft() {
        worldX -= speed;
    }

    public void goRight() {
        worldX += speed;
    }

//    @Override
//    public void draw(Graphics2D g2) {
//        int tempScreenX = SCREEN_X;
//        int tempScreenY = SCREEN_Y;
//        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1f));
//        g2.drawImage(image,tempScreenX,tempScreenY, null);
//    }


}
