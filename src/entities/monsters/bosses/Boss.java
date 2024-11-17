package entities.monsters.bosses;

import entities.monsters.Monster;
import gamestates.Playing;
import utils.HelpMethods;
import utils.ImageLoader;

import java.awt.*;
import java.awt.image.BufferedImage;

import static utils.Constants.Screen.*;
import static utils.Constants.Screen.SCREEN_WIDTH;

public abstract class Boss extends Monster {
    public Boss(String name, Playing playing, int width, int height) {
        super(name, playing, width, height);
    }

    // Boss intro attributes
    public boolean isBossIntroDrew = true;
    public boolean isFirstTime = true;
    int frameCnt = 0;
    float textSize = 100f;
    int rectangleHeight = 150;
    int bossNameX = 0, bossNameY = 0, imageX = 0, imageY = 0;
    String bossName;
    BufferedImage bossImage; // 1296 x 720

    public abstract void drawBossIntro(Graphics2D g2);

    public void initializeBossIntro(Graphics2D g2, String bossName, BufferedImage bossImage) {
        g2.setFont(playing.getGame().getUI().maruMonica);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, textSize));
        this.bossName = bossName;
        this.bossImage = bossImage;
        if (bossNameX == 0) bossNameX = TILE_SIZE;
        if (bossNameY == 0) bossNameY = HelpMethods.getYForCenterText(bossName, g2) + 3 * TILE_SIZE / 2;
        if (imageX == 0) imageX = SCREEN_WIDTH - bossImage.getWidth() + TILE_SIZE * 9;
        if (imageY == 0) imageY = SCREEN_HEIGHT / 2 - bossImage.getHeight() / 2 - TILE_SIZE  * 2;
    }
    public void bossIntro(Graphics2D g2, String bossName, BufferedImage bossImage) {
        if (isFirstTime) {
            if (canSeePlayer()) {
                isBossIntroDrew = false;
                isFirstTime = false;
            }
        }

        if (isBossIntroDrew) return;

        initializeBossIntro(g2, bossName, bossImage);
        frameCnt++;
        if (frameCnt <= 30) {
            fillScreen(30, g2);
        }
        else if (frameCnt <= 60) {
            rectangleMoveIn(30, g2);

        } else if (frameCnt <= 90) {
            drawBackground(g2, imageX, imageY, false);
            if (frameCnt <= 85)
                textAnimationMoveLeft( g2, 25, 0, bossNameY, imageX - TILE_SIZE, imageY,
                        SCREEN_WIDTH, SCREEN_WIDTH);
            else textAnimationMoveRight( g2, 5, 0, bossNameY, imageX - TILE_SIZE, imageY,
                     bossNameX, imageX);

            g2.setColor(Color.BLACK);
            g2.fillRect(0, SCREEN_HEIGHT - rectangleHeight,
                    SCREEN_WIDTH, rectangleHeight);

        } else if (frameCnt <= 170) {
            drawBackground(g2, imageX, imageY, true);

            g2.setColor(Color.BLACK);
            g2.fillRect(0, SCREEN_HEIGHT - rectangleHeight,
                    SCREEN_WIDTH, rectangleHeight);

        } else if (frameCnt <= 210){
            drawBackground(g2,imageX, imageY, false);

            if (frameCnt <= 180)
                textAnimationMoveLeft(g2, 10, 0, bossNameY,
                        imageX - TILE_SIZE, imageY, bossNameX, imageX);
            else
                textAnimationMoveRight( g2, 30, 0, bossNameY, imageX - TILE_SIZE, imageY, SCREEN_WIDTH, SCREEN_WIDTH);

            g2.setColor(Color.BLACK);
            g2.fillRect(0, SCREEN_HEIGHT - rectangleHeight,
                    SCREEN_WIDTH, rectangleHeight);
        } else if (frameCnt <= 240) {
            rectangleMoveOut(30, g2);
        }
        else {
            isBossIntroDrew = true;
            frameCnt = 0;
        }
    }

    public void drawBackground(Graphics2D g2, int imageX, int imageY, boolean drawImage) {
        Color c = new Color(147, 114, 15, 180);
        g2.setColor(c);
        g2.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, SCREEN_WIDTH, rectangleHeight);

        if (drawImage) {
            g2.setFont(playing.getGame().getUI().maruMonica);
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, textSize));

            g2.setColor(Color.GRAY);
            g2.drawString(bossName, bossNameX + 5, bossNameY + 3);
            g2.setColor(Color.WHITE);
            g2.drawString(bossName, bossNameX, bossNameY);

            g2.drawImage(bossImage, imageX, imageY, null);
        }
    }

    int cnt3 = 0;
    public void fillScreen(int duration, Graphics2D g2) {
        cnt3++;
        Color c = new Color(147, 114, 15, cnt3 * (180 / duration));
        g2.setColor(c);
        g2.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
        if (cnt3 >= duration) cnt3 = 0;
    }

    int cnt4 = 0;
    public void unFillScreen(int duration, Graphics2D g2) {
        cnt4++;
        Color c = new Color(147, 114, 15, 180 - cnt4 * (180 / duration));
        g2.setColor(c);
        g2.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
        if (cnt4 >= duration) cnt4 = 0;
    }

    int cnt5 = 0;
    public void rectangleMoveIn(int duration, Graphics2D g2) {
        cnt5++;
        Color c = new Color(147, 114, 15, 180);
        g2.setColor(c);
        g2.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);

        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, SCREEN_WIDTH, cnt5 * (rectangleHeight / duration));

        g2.fillRect(0, SCREEN_HEIGHT - cnt5 * (rectangleHeight / duration),
                SCREEN_WIDTH, cnt5 * (rectangleHeight / duration));
        if (cnt5 >= duration) cnt5 = 0;
    }

    int cnt6 = 0;
    public void rectangleMoveOut(int duration, Graphics2D g2) {
        cnt6++;
        unFillScreen(duration, g2);

        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, SCREEN_WIDTH, duration * 6 - cnt6 * 6);

        g2.fillRect(0, SCREEN_HEIGHT - rectangleHeight + cnt6 * (rectangleHeight / duration),
                SCREEN_WIDTH, rectangleHeight - cnt6 * (rectangleHeight / duration));

        if (cnt6 >= duration) cnt6 = 0;
    }

    int cnt1 = 0;
    public void textAnimationMoveLeft(Graphics2D g2, int duration, int x, int y, int imageX, int imageY,
                                      int xStartText, int xStartImage) {
        String text = bossName;
        cnt1++;
        int distance = xStartText - x;
        int diff = distance / duration;
        g2.setFont(playing.getGame().getUI().maruMonica);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, textSize));
        g2.setColor(Color.GRAY);
        g2.drawString(text, xStartText - diff * cnt1 + 5, y + 3);
        g2.setColor(Color.WHITE);
        g2.drawString(text, xStartText - diff * cnt1, y);

        distance = xStartImage - imageX;
        diff = distance / duration;
        g2.drawImage(bossImage, xStartImage - cnt1 * diff, imageY, null);

        if (cnt1 >= duration) cnt1 = 0;
    }

    int cnt2 = 0;
    public void textAnimationMoveRight(Graphics2D g2, int duration, int x, int y, int imageX, int imageY,
                                       int xEndText, int xEndImage) {
        String text = bossName;
        cnt2++;
        int distance = xEndText - x;
        int diff = distance / duration;
        g2.setFont(playing.getGame().getUI().maruMonica);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, textSize));
        g2.setColor(Color.GRAY);
        g2.drawString(text, x + diff * cnt2 + 5, y + 3);
        g2.setColor(Color.WHITE);
        g2.drawString(text, x + diff * cnt2, y);

        distance = xEndImage - imageX;
        diff = distance / duration;
        g2.drawImage(bossImage, imageX + cnt2 * diff, imageY, null);

        if (cnt2 >= duration) cnt2 = 0;
    }
}
