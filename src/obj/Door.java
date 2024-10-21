package obj;

import entity.Entity;
import main.GamePanel;

import java.awt.image.BufferedImage;

public class Door extends Entity {

    GamePanel gp;
    public Door(GamePanel gp) {
        super(gp);
        name = "Door";
        image = getObjectImage("door", gp.tileSize, gp.tileSize);
        collision = true;

        idle_down = new BufferedImage[1];
        idle_down[0] = image;
        numAnimationFrames = 1;

        solidArea.x = 0;
        solidArea.y = 16;
        solidArea.width = 48;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }

}
