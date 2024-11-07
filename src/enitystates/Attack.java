package enitystates;

import entities.monsters.*;
import entities.Sprite;
import inputs.KeyboardInputs;
import entities.Player;


public class Attack extends EntityStateMethods{
    public EntityState lastState;

    public Attack(Sprite entity,int totalAnimationFrames, int frameDuration) {
        super(entity, totalAnimationFrames, frameDuration);
        state = "ATTACK";
    }

    // This constructor used for multiple attack types
    public Attack(Sprite entity, int totalAnimationFrames, int frameDuration, String customAttackState) {
        super(entity, totalAnimationFrames, frameDuration);
        state = customAttackState;
    }

    public Attack(Sprite entity) {
        super(entity);
        state = "ATTACK";
    }

    int frameCounter = 0;
    @Override
    public void update(Sprite entity) {
        if (entity instanceof Slime slime) {
            slime.attack();
        }

        if (entity instanceof PlantMelee plantMelee) {
            plantMelee.attack();
        }

        if (entity instanceof Demon demon) {
            demon.attack();
        }

    }

    public void update(Player player, KeyboardInputs keyboardInputs) {
        // focus at the position of the mouse
        player.lockOn();
        frameCounter++;
        if (frameCounter >= totalAnimationFrames * frameDuration) {
            for (Monster monster : player.getPlaying().monsters) {
                if (monster != null) {
                    if (monster.isBeingLockOn) {
                        if (player.currentWeapon.equals("SPEAR"))
                            monster.currentHealth -= player.attackPointSpear;
                        else if (player.currentMana - player.manaCostPerShot < 0) {
                            player.currentState = lastState;
                            return;
                        }
                        else if (player.currentWeapon.equals("GUN")) {
                            monster.currentHealth -= player.attackPointGun;
                            player.currentMana -= player.manaCostPerShot;
                        }
                        if (monster.currentHealth <= 0) {
                            monster.currentState = EntityState.DEATH;
                        }
                    }
                }
            }
            if (!keyboardInputs.attackPressed) {
                player.currentState = lastState;
            }
            frameCounter = 0;
        }
    }
}
