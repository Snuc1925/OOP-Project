package entities;

import gamestates.Playing;

import java.awt.*;

import static utils.Constants.Player.*;
import static utils.Constants.Screen.*;
public class Player extends Sprite {

    public Player(Playing playing) {
        super("Player", "player/Idle/Normal/down/1", playing, PLAYER_IMAGE_WIDTH, PLAYER_IMAGE_HEIGHT, 8);
        setDefaultValues();
    }

    public void setDefaultValues() {
        solidArea = new Rectangle();
        solidArea.setBounds(18* SCALE, 32* SCALE, 13 * SCALE, 12 * SCALE);

        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        worldX = TILE_SIZE * 21;
        worldY = TILE_SIZE * 20;
        speed = 4;
    }

    public void goUp() {
        direction = "up";
        worldY -= speed;
    }

    public void goDown() {
        direction = "down";
        worldY += speed;
    }

    public void goLeft() {
        direction = "left";
        worldX -= speed;
    }

    public void goRight() {
        direction = "right";
        worldX += speed;
    }

    public void goUpLeft() {
        direction = "up_left";
        worldX -= speed - 1;
        worldY -= speed - 1;
    }

    public void goUpRight() {
        direction = "up_right";
        worldX += speed - 1;
        worldY -= speed - 1;
    }

    public void goDownLeft() {
        direction = "down_left";
        worldX -= speed - 1;
        worldY += speed - 1;
    }

    public void goDownRight() {
        direction = "down_right";
        worldX += speed - 1;
        worldY += speed - 1;
    }

    @Override
    public void draw(Graphics2D g2) {
        int tempScreenX = PLAYER_SCREEN_X;
        int tempScreenY = PLAYER_SCREEN_Y;
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1f));
        g2.drawImage(image,tempScreenX,tempScreenY, null);
        g2.drawRect(tempScreenX + solidArea.x, tempScreenY + solidArea.y, solidArea.width, solidArea.height);

    }


}
