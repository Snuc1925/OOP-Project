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

public class DeathHand {
    int totalAnimationFrames = 15;
    int numAnimationFrames = 0;
    int frameDuration = 5;
    int frameCounter = 0;
    int worldX, worldY;
    Player player;
    Rectangle effectRect;
    BringerOfDeath bringerOfDeath;
    int index;

    public DeathHand(BringerOfDeath bringerOfDeath, Player player, int worldX, int worldY, int index) {
        this.index = index;
        this.worldX = worldX;
        this.worldY = worldY;
        this.player = player;
        this.bringerOfDeath = bringerOfDeath;
        effectRect = new Rectangle(worldX + 5 * TILE_SIZE / 2, worldY + 5 * TILE_SIZE, 3 * TILE_SIZE, 3 * TILE_SIZE);
    }
    public BufferedImage getImage() {
        frameCounter++;
        if (frameCounter % frameDuration == 0) {
            numAnimationFrames = (numAnimationFrames + 1) % totalAnimationFrames;
        }
        ImageLoader.initialize();
        ImageManager eimageManager = ImageLoader.imageManager;
        return eimageManager.getEffectImage("DeathHand", numAnimationFrames + 1);

    }
    public void draw(Graphics2D g2) {
        int screenX = HelpMethods.getScreenX(worldX, player);
        int screenY = HelpMethods.getScreenY(worldY, player);
        BufferedImage image = HelpMethods.scaleImage(getImage(), 2);
        g2.drawImage(image, screenX, screenY, null);

        // Draw effect rect
        g2.setColor(Color.WHITE);
        g2.drawRect(effectRect.x + screenX, effectRect.y + screenY, effectRect.width, effectRect.height);
    }


    public void update() {
        if (frameCounter == 8 * frameDuration) {
            player.solidArea.x += player.worldX;
            player.solidArea.y += player.worldY;
            if (player.solidArea.intersects(effectRect)) {
                player.getHurt(bringerOfDeath.attackPoints);
            }
            player.solidArea.x = player.solidAreaDefaultX;
            player.solidArea.y = player.solidAreaDefaultY;
        }
        if (frameCounter > frameDuration * totalAnimationFrames) {
            bringerOfDeath.removeEffect(index);
        }
    }
}
