package enitystates;

import entities.Player;
import entities.Slime;
import entities.Sprite;
import inputs.KeyboardInputs;

import static java.lang.Math.abs;
import static utils.Constants.Screen.TILE_SIZE;

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
        Player player = entity.getPlaying().getPlayer();
        entity.move();

        if (entity.name.equals("Slime")) {
            System.out.println("Player: " + player.worldX + " " + player.worldY);
            System.out.println("Slime: " + entity.worldX + " " + entity.worldY);
            System.out.println();
            if (abs(player.worldX - entity.worldX) < TILE_SIZE || abs(player.worldY - entity.worldY) < TILE_SIZE) {
                entity.currentState = EntityState.ATTACK;
                return;
            }
            Slime slime = (Slime) entity;
            slime.stateChanger();
        }
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
            player.move();
        }
    }
    public void stateChanger(Player player, KeyboardInputs keyboardInputs) {
        if (player.isIdling) player.currentState = EntityState.WALK;
        if (!player.isIdling && keyboardInputs.shiftPressed) player.currentState = EntityState.RUN;
    }
}
