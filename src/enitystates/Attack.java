package enitystates;

import entities.Entity;
import entities.Sprite;
import utils.ImageManager;

import java.awt.image.BufferedImage;

public class Attack extends EntityStateMethods{

    public Attack(Sprite entity,int totalAnimationFrames, int frameDuration) {
        super(entity, totalAnimationFrames, frameDuration);
        state = "ATTACK";
    }

    public Attack(Sprite entity) {
        super(entity);
        state = "ATTACK";
    }

    @Override
    public void update(Sprite entity) {

    }
}
