package enitystates;

import entities.Sprite;
import inputs.KeyboardInputs;
import utils.ImageManager;

import java.awt.image.BufferedImage;

public class Idle extends EntityStateMethods{

    public Idle(Sprite entity, ImageManager imageManager, int totalAnimationFrames, int frameDuration) {
        super(entity, imageManager, totalAnimationFrames, frameDuration);
        state = "IDLE";
    }

    public Idle(Sprite entity, ImageManager imageManager) {
        super(entity, imageManager);
        state = "IDLE";
    }

    @Override
    public void update(Sprite entity) {

    }

    public void update(Sprite entity, KeyboardInputs keyboardInputs) {

    }
}
