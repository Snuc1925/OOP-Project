package enitystates;

import entities.Entity;
import entities.Sprite;
import utils.ImageManager;

import java.awt.image.BufferedImage;

public class Attack extends EntityStateMethods{

    public Attack(Sprite entity, ImageManager imageManager, int totalAnimationFrames, int frameDuration) {
        super(entity, imageManager, totalAnimationFrames, frameDuration);
        state = "ATTACK";
    }

    public Attack(Sprite entity, ImageManager imageManager) {
        super(entity, imageManager);
        state = "ATTACK";
    }

    @Override
    public void update(Sprite entity) {

    }
}
