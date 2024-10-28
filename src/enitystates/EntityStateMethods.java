package enitystates;

import entities.*;
import utils.ImageLoader;
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

    public EntityStateMethods(Sprite entity, int totalAnimationFrames, int frameDuration) {
        this.totalAnimationFrames = totalAnimationFrames;
        this.frameDuration = frameDuration;
        this.entity = entity;
    }
    public EntityStateMethods(Sprite entity) {
        this.entity = entity;
    }

    public BufferedImage getImage() {
        frameCounter++;
        if (frameCounter >= frameDuration) {
            frameCounter = 0;
            numAnimationFrames = (numAnimationFrames + 1) % totalAnimationFrames;
        }
        ImageLoader.initialize();
        imageManager = ImageLoader.imageManager;
        if (entity.name.equals("Player")) {
            Player player = (Player) entity;
            return imageManager.getPlayerImage(state, player.currentWeapon, entity.direction.toUpperCase(), numAnimationFrames + 1);
        }
        if (entity.name.equals("Slime")) {
            return imageManager.getMonsterImage(entity.name, state, entity.direction.toUpperCase(), numAnimationFrames + 1);
        }
        return null;
    }

    public abstract void update(Sprite entity);

}
