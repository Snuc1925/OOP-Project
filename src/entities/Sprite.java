package entities;

import entities.state.*;
import gamestates.Playing;

public class Sprite extends Entity{
    EntityState currentState;
    int numAnimationFrames;
    IdleState idleState;
    WalkState walkState;
    AttackState attackState;
    DeathState deathState;
    public Sprite(String name, String image_path, Playing playing, int width, int height, int numAnimationFrames) {
        super(name, image_path, playing, width, height);
        this.numAnimationFrames = numAnimationFrames;
        idleState = new IdleState(name, numAnimationFrames, 1);
        walkState = new WalkState(name, numAnimationFrames, 1);
        attackState = new AttackState(name, numAnimationFrames, 1);
        deathState = new DeathState(name, numAnimationFrames, 1);
    }
}
