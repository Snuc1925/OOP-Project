package entities.state;

import entities.Sprite;

import java.awt.*;

public class DeathState extends EntityState{
    public DeathState(String imageFolderPath, int numAnimationsFrames, int numType) {
        super(imageFolderPath, numAnimationsFrames, numType);
    }

    @Override
    public void handleInput(Sprite sprite) {

    }

    @Override
    public void render(Graphics2D g2) {

    }
}
