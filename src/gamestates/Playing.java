package gamestates;

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


    public Player getPlayer() { return player; }

    @Override
    public void update() {

    }


    @Override
    public void draw(Graphics2D g2) {
        tileManager.draw(g2);
        player.draw(g2);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch(e.getKeyCode()) {
            case KeyEvent.VK_A:
                player.goLeft();
                break;
            case KeyEvent.VK_D:
                player.goRight();
                break;
            case KeyEvent.VK_S:
                player.goDown();
                break;
            case KeyEvent.VK_W:
                player.goUp();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
