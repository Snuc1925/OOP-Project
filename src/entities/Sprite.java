package entities;

import gamestates.Playing;
import utils.Constants;
import utils.HelpMethods;

import java.awt.*;

public class Sprite extends Entity{
//    public EntityState currentState;
//    public String direction;
//    int numAnimationFrames;
//    public IdleState idleState;
//    public WalkState walkState;
//    public AttackState attackState;
//    public DeathState deathState;
//    public Sprite(String name, String image_path, Playing playing, int width, int height, int numAnimationFrames) {
//        super(name, image_path, playing, width, height);
//        this.numAnimationFrames = numAnimationFrames;
//
//    }
    public Sprite(String name, String image_path, Playing playing, int width, int height, int numAnimationFrames) {
        super(name, image_path, playing, width, height);
    }
}
