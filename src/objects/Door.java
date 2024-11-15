package objects;

import components.AnimationComponent;
import entities.Entity;
import gamestates.Playing;

import java.awt.*;

public class Door extends Entity {
    AnimationComponent animation;

    private boolean closing;
    public Door(String name, Playing playing, int worldX, int worldY, int width, int height) {
        super(name, playing, width, height);
        this.worldX = worldX;
        this.worldY = worldY;
        closing = true;
        animation = new AnimationComponent(5, 3);
    }

    public void openDoor() {
        closing = false;
        
    }

    public void closeDoor() {
        if (!animation.checkCompleteAnimation()) animation.updateAnimation();
    }
}
