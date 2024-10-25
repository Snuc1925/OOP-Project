package gamestates;

import inputs.KeyboardInputs;
import main.Game;

import java.awt.*;
import java.awt.event.KeyEvent;

public interface Statemethods {
    public void update(Game game);

    public void draw(Graphics2D g);


}
