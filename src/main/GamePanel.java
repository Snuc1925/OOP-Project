package main;

import java.awt.*;
import javax.swing.JPanel;

import static java.awt.Color.BLACK;
import static utils.Constants.Screen.screenWidth;
import static utils.Constants.Screen.screenHeight;
//import inputs.KeyboardInputs;

public class GamePanel extends JPanel{
    private Game game;

    public GamePanel(Game game) {
        this.game = game;
        setPanelSize();
    }

    private void setPanelSize() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(BLACK);
        this.setDoubleBuffered(true); // Make game render better
//        this.addKeyListener(keyHandler);
        this.setFocusable(true);
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
