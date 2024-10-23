package gamestates;

import main.Game;

import java.awt.*;
import java.awt.event.KeyEvent;
import entities.Player;

public class Playing extends State implements Statemethods {

    private Player player;
    public Playing(Game game) {
        super(game);
    }


    public Player getPlayer() { return player; }

    @Override
    public void update() {

    }


    @Override
    public void draw(Graphics2D g) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
