package entities.npc;

import enitystates.Idle;
import gamestates.Playing;

import java.awt.*;

import static utils.Constants.Screen.TILE_SIZE;

public class WhiteSamurai extends Npc{
    public WhiteSamurai(Playing playing, int worldX, int worldY) {
        super("White_Samurai",
                playing, worldX, worldY, 6 * TILE_SIZE, 4 * TILE_SIZE);
        solidArea = new Rectangle(5 * TILE_SIZE / 2, 2 * TILE_SIZE, TILE_SIZE, TILE_SIZE);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        idle = new Idle(this, 10, 5);

        dialogues = new String[]{
                "...",
                "Hello adventure",
                "I'm, tha best smr"
        };
    }


}
