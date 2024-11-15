package enitystates;

import entities.*;
import entities.monsters.BringerOfDeath;
import entities.monsters.Demon;
import entities.monsters.PlantMelee;
import entities.monsters.Slime;
import entities.npc.WhiteSamurai;
import entities.projectile.Projectile;
import utils.ImageLoader;
import utils.ImageManager;

import java.awt.image.BufferedImage;

public abstract class EntityStateMethods {
    protected Sprite entity;
    protected String weaponName = "normal";
    protected String state;

    protected ImageManager imageManager;
    public int totalAnimationFrames = 8;
    public int frameDuration = 5;
    public int frameCounter = -1;
    public int numAnimationFrames = 0;

    public EntityStateMethods(Sprite entity, int totalAnimationFrames, int frameDuration) {
        this.totalAnimationFrames = totalAnimationFrames;
        this.frameDuration = frameDuration;
        this.entity = entity;
        ImageLoader.initialize();

    }
    public EntityStateMethods(Sprite entity) {
        this.entity = entity;
    }

    public String getState() { return state; }
    public void setState(String state) { this.state = state; }

    public BufferedImage getImage() {
        frameCounter++;
        if (frameCounter >= frameDuration) {
            frameCounter = 0;
            numAnimationFrames = (numAnimationFrames + 1) % totalAnimationFrames;
        }

        imageManager = ImageLoader.imageManager;
        if (entity instanceof Projectile) {
            String state = (entity.currentState == EntityState.ATTACK ? "ATTACK" : "EXPLOSION");
            System.out.println(state);
            return imageManager.getProjectileImage(entity.name, state, entity.direction, numAnimationFrames + 1);
        }
        if (entity instanceof Player player) {
            if (state.equals("ATTACK") && player.currentWeapon.equals("SPEAR"))
                return imageManager.getPlayerImage(state, player.currentWeapon, "VFX_" + entity.direction, numAnimationFrames, entity.width, entity.height);
            return imageManager.getPlayerImage(state, player.currentWeapon, entity.direction, numAnimationFrames, entity.width, entity.height);
        }
        if (entity instanceof WhiteSamurai) {
            switch (entity.direction) {
                case "left", "up", "left_down", "left_up":
                    return imageManager.getNPCImage(entity.name, state, "left", numAnimationFrames, entity.width, entity.height);
                case "right", "down", "right_down", "right_up":
                    return imageManager.getNPCImage(entity.name, state, "right", numAnimationFrames, entity.width, entity.height);
            }
        }
        else if (entity instanceof PlantMelee) {
            return imageManager.getMonsterImage("PlantMelee", state, "ALL", numAnimationFrames, entity.width, entity.height);
        }
        else {
            // Add more cases for other directions if needed
            switch (entity.direction) {
                case "left", "up", "left_down", "left_up":
                    return imageManager.getMonsterImage(entity.name, state, "left", numAnimationFrames, entity.width, entity.height);
                case "right", "down", "right_down", "right_up":
                    return imageManager.getMonsterImage(entity.name, state, "right", numAnimationFrames, entity.width, entity.height);
            }
        }

        return null;
    }

    public abstract void update(Sprite entity);

}
