package effect;

import entities.Player;
import entities.monsters.BringerOfDeath;
import entities.monsters.Demon;
import entities.monsters.PlantMelee;
import entities.monsters.Slime;
import gamestates.Playing;
import utils.HelpMethods;
import utils.ImageLoader;
import utils.ImageManager;

import java.awt.*;
import java.awt.image.BufferedImage;

import static utils.Constants.Screen.TILE_SIZE;

public class DeathHand extends EffectMethod {
    BringerOfDeath bringerOfDeath;
    public DeathHand(BringerOfDeath bringerOfDeath, int worldX, int worldY, int index) {
        super("DeathHand",15,  bringerOfDeath, bringerOfDeath.getPlaying().getPlayer(), worldX, worldY, index);

        frameDuration = 5;
        effectRect = new Rectangle(worldX + 5 * TILE_SIZE / 2, worldY + 5 * TILE_SIZE, 4 * TILE_SIZE, 4 * TILE_SIZE);
        this.bringerOfDeath = bringerOfDeath;
    }

    public void draw(Graphics2D g2) {
        super.draw(g2, 2);
    }
    public void update() {
        super.update(8, true);
    }

    @Override
    public void removeEffect(int index) {
        bringerOfDeath.removeEffect(index);
    }
}
