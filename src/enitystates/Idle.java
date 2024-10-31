package enitystates;

import entities.Entity;
import entities.Slime;
import entities.Sprite;
import gamestates.Playing;
import inputs.KeyboardInputs;
import utils.ImageManager;
import entities.Player;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static java.lang.Math.abs;
import static utils.Constants.Screen.TILE_SIZE;

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
        Player player = entity.getPlaying().getPlayer();
        if (entity.name.equals("Slime")) {
            if (abs(player.getWorldX() - entity.getWorldX()) < TILE_SIZE && abs(player.getWorldY() - entity.getWorldY()) < TILE_SIZE) {
                entity.currentState = EntityState.ATTACK;
                return;
            }
            Slime slime = (Slime) entity;
            slime.stateChanger();
        }
    }

    public void update(Player player, KeyboardInputs keyboardInputs) {
        player.isIdling = false;
        if (keyboardInputs.upPressed) {
            if (keyboardInputs.leftPressed)
                player.direction = "left_up";
            else if (keyboardInputs.rightPressed)
                player.direction = "right_up";
            else player.direction = "up";
        } else if (keyboardInputs.downPressed) {
            if (keyboardInputs.leftPressed)
                player.direction = "left_down";
            else if (keyboardInputs.rightPressed)
                player.direction = "right_down";
            else player.direction = "down";
        } else if (keyboardInputs.leftPressed)
            player.direction = "left";
        else if (keyboardInputs.rightPressed)
            player.direction = "right";
        else player.isIdling = true;

        if (!keyboardInputs.mousePressed || player.currentWeapon.equals("NORMAL")) {
            if (!player.isIdling) {
                if (keyboardInputs.shiftPressed) player.currentState = EntityState.RUN;
                else player.currentState = EntityState.WALK;
            }
        } else {
            player.attack.lastState = EntityState.IDLE;
            player.currentState = EntityState.ATTACK;
        }

    }
}
