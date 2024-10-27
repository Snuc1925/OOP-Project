package entities;

import enitystates.Attack;
import enitystates.EntityState;
import gamestates.Playing;

import static enitystates.EntityState.*;

public class Sprite extends Entity{
    public EntityState currentState = IDLE;
    public int speed;
    public String direction = "down";
    public boolean collisionOn;
    public boolean isIdling = true;

    public Sprite(String name, String image_path, Playing playing, int width, int height, int worldX, int worldY) {
        super(name, image_path, playing, width, height, worldX, worldY);
    }

    public void move() {
        playing.getGame().getCollisionChecker().checkTile(this);
        playing.getGame().getCollisionChecker().checkEntity(this, getPlaying().allSprites);
        if (collisionOn) return;
        if (direction.equals("down")) {
            worldY += speed;
        }
        if (direction.equals("up")) {
            worldY -= speed;
        }
        if (direction.equals("left")) {
            worldX -= speed;
        }
        if (direction.equals("right")) {
            worldX += speed;
        }
        if (direction.equals("down_right")) {
            worldX += speed - 1;
            worldY += speed - 1;
        }
        if (direction.equals("up_left")) {
            worldX -= speed - 1;
            worldY -= speed - 1;
        }
        if (direction.equals("down_left")) {
            worldX -= speed - 1;
            worldY += speed - 1;
        }
        if (direction.equals("up_right")) {
            worldX += speed - 1;
            worldY -= speed - 1;
        }
    }

}
