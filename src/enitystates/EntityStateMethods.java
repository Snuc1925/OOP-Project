package enitystates;

import entities.Entity;
import entities.Sprite;
import utils.ImageManager;

import java.awt.image.BufferedImage;

public abstract class EntityStateMethods {
    protected Sprite entity;
    protected String weaponName = "normal";
    protected String state;

    protected ImageManager imageManager;
    protected int totalAnimationFrames = 8;
    protected int frameDuration = 5;
    protected int frameCounter = 0;
    protected int numAnimationFrames = 0;

    public EntityStateMethods(Sprite entity, ImageManager imageManager, int totalAnimationFrames, int frameDuration) {
        this.imageManager = imageManager;
        this.totalAnimationFrames = totalAnimationFrames;
        this.frameDuration = frameDuration;
        this.entity = entity;
    }
    public EntityStateMethods(Sprite entity, ImageManager imageManager) {
        this.imageManager = imageManager;
        this.entity = entity;
    }

    int cnt = 0;
    public BufferedImage getImage() {
        frameCounter++;
        if (frameCounter >= frameDuration) {
            frameCounter = 0;
            numAnimationFrames = (numAnimationFrames + 1) % totalAnimationFrames;
        }
        if (entity.name.equals("Player")) {
            return imageManager.getPlayerImage(state, weaponName.toUpperCase(), entity.direction.toUpperCase(), numAnimationFrames);
        }
        return null;
    }

    public abstract void update(Sprite entity);

}
