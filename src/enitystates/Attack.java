package enitystates;

import entities.monsters.Monster;
import entities.monsters.PlantMelee;
import entities.monsters.Slime;
import entities.Sprite;
import inputs.KeyboardInputs;
import entities.Player;


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
        if (entity instanceof Slime) {
            ((Slime) entity).attack();
        }

        if (entity instanceof PlantMelee) {
            ((PlantMelee) entity).attack();
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
            if (!keyboardInputs.spacePressed) {
                player.currentState = lastState;
            }
            frameCounter = 0;
        }
    }
}
