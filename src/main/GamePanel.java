package main;

import inputs.KeyboardInputs;

import java.awt.*;
import javax.swing.JPanel;

import static java.awt.Color.BLACK;
import static utils.Constants.Screen.SCREEN_WIDTH;
import static utils.Constants.Screen.SCREEN_HEIGHT;
//import inputs.KeyboardInputs;

public class GamePanel extends JPanel{
    private Game game;

    public GamePanel(Game game) {
        this.game = game;
        setPanelSize();
    }

    private void setPanelSize() {
        setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        setBackground(BLACK);
        setDoubleBuffered(true); // Make game render better
        addKeyListener(new KeyboardInputs(this));
        setFocusable(true);
//        this.eventHandler = new EventHandler(this);
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        game.render((Graphics2D)g);
    }

    public Game getGame() {
        return game;
    }
}
