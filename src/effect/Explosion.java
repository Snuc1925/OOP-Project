package effect;

import entities.Player;
import entities.monsters.BringerOfDeath;
import entities.monsters.Demon;
import utils.HelpMethods;
import utils.ImageLoader;
import utils.ImageManager;

import java.awt.*;
import java.awt.image.BufferedImage;

import static utils.Constants.Screen.TILE_SIZE;

public class Explosion extends EffectMethod{
    Demon demon;
    public Explosion(Demon demon, int worldX, int worldY, int index) {
        super("Explosion", 12, demon, demon.getPlaying().getPlayer(), worldX, worldY,
                8 * TILE_SIZE, 8 * TILE_SIZE, index);
        this.demon = demon;
        effectRect = new Rectangle(2 * TILE_SIZE, 4 * TILE_SIZE, 4 * TILE_SIZE, 4 * TILE_SIZE);

        // Make the center of effectRect has position (worldX, worldY)
        this.worldX -= effectRect.x + effectRect.width / 2;
        this.worldY -= effectRect.y + effectRect.height / 2;
        effectRect.x += this.worldX;
        effectRect.y += this.worldY;
    }


    public void draw(Graphics2D g2) {
        super.draw(g2, 1);
    }

    public void update() {
        super.update(6, true);
    }

    @Override
    public void removeEffect(int index) {
        demon.removeEffect(index);
    }
}
