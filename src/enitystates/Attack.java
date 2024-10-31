package enitystates;

import entities.Slime;
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
    }

    public void update(Player player, KeyboardInputs keyboardInputs) {
        // focus at the position of the mouse
        player.lockOn();
        frameCounter++;
        if (frameCounter >= totalAnimationFrames * frameDuration) {
            if (!keyboardInputs.mousePressed) {
                player.currentState = lastState;
            }
            frameCounter = 0;
        }
    }
}
