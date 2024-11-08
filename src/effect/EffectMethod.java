package effect;

import entities.Player;
import entities.Sprite;
import entities.monsters.BringerOfDeath;
import entities.monsters.Monster;
import utils.HelpMethods;
import utils.ImageLoader;
import utils.ImageManager;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class EffectMethod {
    String name;
    int totalAnimationFrames;
    int numAnimationFrames = 0;
    int frameDuration = 5;
    int frameCounter = 0;
    int worldX, worldY, width, height;
    Player player;
    Rectangle effectRect;
    Monster entity;
    int index;

    ImageManager imageManager;
    public EffectMethod(String name, int totalAnimationFrames, Monster entity,
                        Player player,
                        int worldX, int worldY,
                        int index) {
        this.totalAnimationFrames = totalAnimationFrames;
        this.name = name;
        this.index = index;
        this.worldX = worldX;
        this.worldY = worldY;
        this.player = player;
        this.entity = entity;

        ImageLoader.initialize();
        imageManager = ImageLoader.imageManager;
        width = imageManager.getEffectImage(name, 1).getWidth();
        height = imageManager.getEffectImage(name, 1).getHeight();
        effectRect = new Rectangle(worldX - width / 2, worldY - height / 2, width, height);
    }

    public BufferedImage getImage(int scaleFactor) {
        frameCounter++;
        if (frameCounter % frameDuration == 0) {
            numAnimationFrames = (numAnimationFrames + 1) % totalAnimationFrames;
        }
        ImageLoader.initialize();
        ImageManager imageManager = ImageLoader.imageManager;
        BufferedImage image = imageManager.getEffectImage(name, numAnimationFrames + 1);
        image = HelpMethods.scaleImage(image, scaleFactor);
        return image;
    }
    public void draw(Graphics2D g2, int scaleFactor) {
        int screenX = HelpMethods.getScreenX(worldX, player);
        int screenY = HelpMethods.getScreenY(worldY, player);
        BufferedImage image = getImage(scaleFactor);
        g2.drawImage(image, screenX, screenY, null);

        // Draw effect rect
        g2.setColor(Color.WHITE);
        g2.drawRect(effectRect.x - worldX + screenX, effectRect.y - worldY + screenY, effectRect.width, effectRect.height);
    }

    // Decrease player's health when keyFrame is on the screen
    public void update(int keyFrame, boolean needScreenShake) {
        if (frameCounter == keyFrame * frameDuration) {
            if (needScreenShake) player.getPlaying().cameraShake.startShake();

            player.solidArea.x += player.worldX;
            player.solidArea.y += player.worldY;
            if (player.solidArea.intersects(effectRect)) {
                player.getHurt(entity.attackPoints);
            }
            player.solidArea.x = player.solidAreaDefaultX;
            player.solidArea.y = player.solidAreaDefaultY;
        }
        if (frameCounter > frameDuration * totalAnimationFrames) {
            removeEffect(index);
        }
    }

    public abstract void removeEffect(int index);

}
