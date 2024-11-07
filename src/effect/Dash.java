package effect;

import java.awt.*;
import java.awt.image.BufferedImage;
import entities.Player;
import utils.HelpMethods;

import static utils.Constants.Player.*;

public class Dash {
    BufferedImage[] dashImages;
    int[] dashImageX, dashImageY;
    int[] dashAlpha;
    Player player;
    int dashImagesSize;
    int duration;
    public boolean isSpeeding = false;

    public Dash(Player player, int duration) {
        this.player = player;
        this.duration = duration;
        dashImagesSize = duration/player.run.frameDuration;
        alphaDiff = 255 / (duration / dashImagesSize);
        dashImages = new BufferedImage[dashImagesSize];
        dashImageX = new int[dashImagesSize];
        dashImageY = new int[dashImagesSize];
        dashAlpha = new int[dashImagesSize];

        for (int i = 0; i < dashImagesSize; i++)
            dashAlpha[i] = 255;
    }

    int alphaDiff;
    int frameCounter = 0;
    public void update() {
        isSpeeding = false;
        for (int i = 0; i < dashImages.length; i++) {
            if (frameCounter == i * duration / dashImagesSize) {
                isSpeeding = true;
                dashImages[i] = HelpMethods.makeWhiteExceptTransparent(player.image);
                dashImageX[i] = player.worldX;
                dashImageY[i] = player.worldY;
            }
        }
        frameCounter++;
        if (frameCounter % (duration / dashImagesSize) == 0) {
            for (int i = 0; i < dashImages.length; i++) {
                if (dashImages[i] != null && dashAlpha[i] > 0) {
                    dashAlpha[i] -= alphaDiff;
                    dashImages[i] = HelpMethods.makeMoreTransparent(dashImages[i], dashAlpha[i]);
                }
            }
        }

        if (dashAlpha[dashImagesSize - 1] < alphaDiff) {
            player.dash = null;
        }

    }

    public void draw(Graphics2D g2) {
        for (int i = 0; i < dashImages.length; i++) {
            if (dashImages[i] != null) {
                int screenX = dashImageX[i] - player.worldX + PLAYER_SCREEN_X;
                int screenY = dashImageY[i] - player.worldY + PLAYER_SCREEN_Y;
                g2.drawImage(dashImages[i], screenX, screenY, null);
            }
        }
    }
}
