package enitystates;

import entities.Sprite;
import inputs.KeyboardInputs;
import utils.ImageManager;
import entities.Player;

import java.awt.image.BufferedImage;

public class Idle extends EntityStateMethods{

    public Idle(Sprite entity, int totalAnimationFrames, int frameDuration) {
        super(entity, totalAnimationFrames, frameDuration);
        state = "IDLE";
    }

    public Idle(Sprite entity) {
        super(entity);
        state = "IDLE";
    }

    @Override
    public void update(Sprite entity) {

    }

    public void update(Sprite player, KeyboardInputs keyboardInputs) {
        player.isIdling = false;
        if (keyboardInputs.upPressed) {
            if (keyboardInputs.leftPressed)
                player.direction = "up_left";
            else if (keyboardInputs.rightPressed)
                player.direction = "up_right";
            else player.direction = "up";
        } else if (keyboardInputs.downPressed) {
            if (keyboardInputs.leftPressed)
                player.direction = "down_left";
            else if (keyboardInputs.rightPressed)
                player.direction = "down_right";
            else player.direction = "down";
        } else if (keyboardInputs.leftPressed)
            player.direction = "left";
        else if (keyboardInputs.rightPressed)
            player.direction = "right";
        else player.isIdling = true;

        if (!player.isIdling) {
            if (keyboardInputs.shiftPressed) player.currentState = EntityState.RUN;
            else player.currentState = EntityState.WALK;
        }

    }
}
