package enitystates;

import entities.Player;
import entities.Sprite;
import inputs.KeyboardInputs;

public class Walk extends EntityStateMethods{
    public Walk(Sprite entity, int totalAnimationFrames, int frameDuration) {
        super(entity, totalAnimationFrames, frameDuration);
        state = "WALK";
    }

    public Walk(Sprite entity) {
        super(entity);
        state = "WALK";
    }

    @Override
    public void update(Sprite entity) {

    }

    public void update(Player player, KeyboardInputs keyboardInputs) {
        player.speed = 4;
        player.isIdling = false;
        if (keyboardInputs.upPressed) {
            if (keyboardInputs.leftPressed)
                player.direction = "up_left";
            else if (keyboardInputs.rightPressed)
                player.direction = "up_right";
            else player.direction = "up";
        } else if (keyboardInputs.downPressed) {
            if (keyboardInputs.leftPressed)
                player.direction = "down_left";
            else if (keyboardInputs.rightPressed)
                player.direction = "down_right";
            else player.direction = "down";
        } else if (keyboardInputs.leftPressed)
            player.direction = "left";
        else if (keyboardInputs.rightPressed)
            player.direction = "right";
        else player.isIdling = true;

        player.getPlaying().getGame().getCollisionChecker().checkTile(player);

        // Condition for changing player state
        stateChanger(player, keyboardInputs);

        if (player.isIdling) player.currentState = EntityState.IDLE;
        if (!player.collisionOn && !player.isIdling) {
            switch (player.direction) {
                case "up":
                    player.goUp();
                    break;
                case "down":
                    player.goDown();
                    break;
                case "left":
                    player.goLeft();
                    break;
                case "right":
                    player.goRight();
                    break;
                case "up_left":
                    player.goUpLeft();
                    break;
                case "up_right":
                    player.goUpRight();
                    break;
                case "down_left":
                    player.goDownLeft();
                    break;
                case "down_right":
                    player.goDownRight();
                    break;
            }
        }
    }
    public void stateChanger(Player player, KeyboardInputs keyboardInputs) {
        if (player.isIdling) player.currentState = EntityState.WALK;
        if (!player.isIdling && keyboardInputs.shiftPressed) player.currentState = EntityState.RUN;
    }
}
