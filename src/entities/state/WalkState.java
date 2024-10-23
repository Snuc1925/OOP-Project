package entities.state;

import entities.Sprite;

import java.awt.*;

public class WalkState extends EntityState{
    public WalkState(String imageFolderPath, int numAnimationsFrames, int numType) {
        super(imageFolderPath, numAnimationsFrames, numType);
    }

    @Override
    public void handleInput(Sprite sprite) {

    }

    @Override
    public void render(Graphics2D g2) {

    }
}
