package gamestates;

import java.awt.*;
import java.awt.event.KeyEvent;

public interface Statemethods {
    public void update();

    public void draw(Graphics2D g);

    public void keyPressed(KeyEvent e);

    public void keyReleased(KeyEvent e);

}
