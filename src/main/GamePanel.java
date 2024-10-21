package main;

import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;
//import inputs.KeyboardInputs;

public class GamePanel extends JPanel{
    private Game game;

    public GamePanel(Game game) {
        this.game = game;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
//        game.render(g);
    }

    public Game getGame() {
        return game;
    }
}
