package obj;

import entity.Entity;
import main.GamePanel;

import java.awt.image.BufferedImage;

public class Chest extends Entity {

    GamePanel gp;

    public Chest(GamePanel gp) {
        super(gp);
        name = "Chest";
        image = getObjectImage("chest", gp.tileSize, gp.tileSize);

        idle_down = new BufferedImage[1];
        idle_down[0] = image;
        numAnimationFrames = 1;
    }
}
