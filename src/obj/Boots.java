package obj;

import entity.Entity;
import main.GamePanel;

import java.awt.image.BufferedImage;

public class Boots extends Entity {
    GamePanel gp;
    public Boots(GamePanel gp) {
        super(gp);
        name = "Boots";
        image = getObjectImage("boots", gp.tileSize, gp.tileSize);
        idle_down = new BufferedImage[1];
        idle_down[0] = image;
        numAnimationFrames = 1;
    }
}
