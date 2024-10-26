package entities;

import enitystates.Attack;
import enitystates.EntityState;
import gamestates.Playing;

import static enitystates.EntityState.*;

public class Sprite extends Entity{
    public EntityState currentState = IDLE;
    public int worldX, worldY;
    public int speed;
    public String direction = "down";
    public boolean collisionOn;
    public boolean isIdling = true;

    public Sprite(String name, String image_path, Playing playing, int width, int height, int numAnimationFrames) {
        super(name, image_path, playing, width, height);
    }



}
