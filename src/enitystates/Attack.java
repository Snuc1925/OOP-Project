package enitystates;

import entities.Entity;
import entities.Slime;
import entities.Sprite;
import inputs.KeyboardInputs;
import entities.Player;
import utils.ImageManager;

import java.awt.image.BufferedImage;

public class Attack extends EntityStateMethods{
    public EntityState lastState;

    public Attack(Sprite entity,int totalAnimationFrames, int frameDuration) {
        super(entity, totalAnimationFrames, frameDuration);
        state = "ATTACK";
    }

    public Attack(Sprite entity) {
        super(entity);
        state = "ATTACK";
    }

    int frameCounter = 0;
    @Override
    public void update(Sprite entity) {
        if (entity.name.equals("Slime")) {
            entity.speed = 4;
            entity.move();
            frameCounter++;
            if (frameCounter > totalAnimationFrames) {
                Slime slime = (Slime) entity;
                slime.stateChanger();
                frameCounter = 0;
            }
            entity.speed = 2;
        }
    }

    public void update(Player player, KeyboardInputs keyboardInputs) {
        // focus at the position of the mouse
        player.lockOn();
        frameCounter++;
        if (frameCounter >= totalAnimationFrames * frameDuration) {
            if (!keyboardInputs.mousePressed) {
                System.out.println(frameCounter);
                player.currentState = lastState;
            }
            frameCounter = 0;
        }
    }
}
