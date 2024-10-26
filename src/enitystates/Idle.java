package enitystates;

import entities.Sprite;
import inputs.KeyboardInputs;
import utils.ImageManager;

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

    public void update(Sprite entity, KeyboardInputs keyboardInputs) {

    }
}
