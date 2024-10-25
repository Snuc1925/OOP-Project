package gamestates;

import inputs.KeyboardInputs;
import main.Game;

import java.awt.*;
import java.awt.event.KeyEvent;

import entities.Player;
import tile.Tile;
import tile.TileManager;

public class Playing extends State implements Statemethods {

    private Player player;
    private TileManager tileManager;

    public Playing(Game game) {
        super(game);
        player = new Player(this);
        tileManager = new TileManager(player);

    }


    public Game getGame() {
        return game;
    }
    public Player getPlayer() {
        return player;
    }

    public TileManager getTileManager() {
        return tileManager;
    }

    @Override
    public void update() {
        KeyboardInputs keyboardInputs = game.getKeyboardInputs();
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
        game.getCollisionChecker().checkTile(player);

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
        player.update();

    }


    @Override
    public void draw(Graphics2D g2) {
        tileManager.draw(g2);
        player.draw(g2);
    }


}
