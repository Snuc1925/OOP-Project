package enitystates;

import entities.Sprite;
import inputs.KeyboardInputs;
import entities.Player;

public class Run extends EntityStateMethods{
    public Run(Sprite entity, int totalAnimationFrames, int frameDuration) {
        super(entity, totalAnimationFrames, frameDuration);
        state = "RUN";
    }

    public Run(Sprite entity) {
        super(entity);
        state = "RUN";
    }

    @Override
    public void update(Sprite entity) {

    }
    public void update(Player player, KeyboardInputs keyboardInputs) {
        player.speed = 5;
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

        // Condition to change player state
        stateChanger(player, keyboardInputs);

        if (!player.collisionOn && !player.isIdling) {
//            switch (player.direction) {
//                case "up":
//                    player.goUp();
//                    break;
//                case "down":
//                    player.goDown();
//                    break;
//                case "left":
//                    player.goLeft();
//                    break;
//                case "right":
//                    player.goRight();
//                    break;
//                case "up_left":
//                    player.goUpLeft();
//                    break;
//                case "up_right":
//                    player.goUpRight();
//                    break;
//                case "down_left":
//                    player.goDownLeft();
//                    break;
//                case "down_right":
//                    player.goDownRight();
//                    break;
//            }
            player.move();
        }

    }

    public void stateChanger(Player player, KeyboardInputs keyboardInputs) {
        if (player.isIdling) {
            player.currentState = EntityState.IDLE;
        }
        if (!keyboardInputs.shiftPressed && !player.isIdling) {
            player.currentState = EntityState.WALK;
        }
    }
}
