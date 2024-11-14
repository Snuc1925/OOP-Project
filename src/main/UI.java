package main;


import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import gamestates.Playing;
import utils.HelpMethods;
import utils.ImageLoader;
import utils.ImageManager;
import entities.Player;

import static utils.Constants.Screen.*;

public class UI {
    Playing playing;
    Player player;
    Font arial_40, arial_80B;
    Font maruMonica, purisaBold;

    public UI(Playing playing) {
        this.playing = playing;
        player = playing.getPlayer();

        try {
            InputStream is = getClass().getResourceAsStream("/font/MaruMonica.ttf");
            assert is != null;
            maruMonica = Font.createFont(Font.TRUETYPE_FONT, is);

            is = getClass().getResourceAsStream("/font/PurisaBold.ttf");
            assert is != null;
            purisaBold = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (FontFormatException | IOException e) {
            throw new RuntimeException(e);
        }
    }


    int frameCounter = 0;
    String previousText = null;
    int lineCounter = 0;
    public void drawDialogueScreen(String currentDialogue, Graphics2D g2) {
        // Window
        int x = TILE_SIZE * 2, y = TILE_SIZE / 2;
        int width = SCREEN_WIDTH - TILE_SIZE * 4, height = TILE_SIZE * 4;

        drawSubWindow(x, y, width, height, g2);

        x += TILE_SIZE;
        y += TILE_SIZE;
        g2.setFont(maruMonica);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 32F));
        if (currentDialogue == null) {
            playing.npcTalking = null;
            return;
        }
        if (!currentDialogue.equals(previousText)) {
            lineCounter = 0;
            previousText = currentDialogue;
            frameCounter = 0;
        }

        frameCounter++;
        String[] lines = currentDialogue.split(("\n"));

        for (int i = 0; i < lineCounter; i++) {
            g2.drawString(lines[i], x, y + 40 * i);
        }

        for (int j = lines[lineCounter].length(); j >= 0; j--) {
            if (j * 1.75 < frameCounter) {
                g2.drawString(lines[lineCounter].substring(0, j), x, y + 40 * lineCounter);
                if (j == lines[lineCounter].length() && lineCounter != lines.length - 1) {
                    lineCounter++;
                    frameCounter = 0;
                }
                break;
            }
        }


    }

    private void drawSubWindow(int x, int y, int width, int height, Graphics2D g2) {
        Color c = new Color(0, 0, 0, 200);
        g2.setColor(c);
        g2.fillRoundRect(x, y, width, height, 35, 35);

        c = new Color(255, 255, 255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 25, 25);
    }


    public void drawPlayerUI(Graphics2D g2) {
        // Draw player status GUI
        ImageLoader.initialize();
        ImageManager imageManager = ImageLoader.imageManager;
        BufferedImage ui_bar_decor = imageManager.getGuiImage("UI_BAR_DECORATION");
        ui_bar_decor = HelpMethods.scaleImage(ui_bar_decor, 0.25);
        g2.drawImage(ui_bar_decor, 0, 0, null);

        if (player.currentHealth > 0) {
            BufferedImage player_health_bar = imageManager.getGuiImage("HEALTH_BAR");
            player_health_bar = HelpMethods.scaleImage(player_health_bar, 0.25);
            player_health_bar = HelpMethods.getBarImage(player_health_bar, 1.0 * player.currentHealth / player.maxHealth);
            g2.drawImage(player_health_bar, 49, 10, null);
        }

        if (player.currentArmor > 0) {
            BufferedImage player_armor_bar = imageManager.getGuiImage("ARMOR_BAR");
            player_armor_bar = HelpMethods.scaleImage(player_armor_bar, 0.25);
            player_armor_bar = HelpMethods.getBarImage(player_armor_bar, 1.0 * player.currentArmor / player.maxArmor);
            g2.drawImage(player_armor_bar, 49, 43, null);
        }

        if (player.currentMana > 0) {
            BufferedImage player_mana_bar = imageManager.getGuiImage("MANA_BAR");
            player_mana_bar = HelpMethods.scaleImage(player_mana_bar, 0.25);
            player_mana_bar = HelpMethods.getBarImage(player_mana_bar, 1.0 * player.currentMana / player.maxMana);
            g2.drawImage(player_mana_bar, 49, 75, null);
        }

        Font pixelFont = HelpMethods.loadFont("PixelFont");
        String text = player.currentHealth + "/" + player.maxHealth;
        g2.setFont(pixelFont);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 20F));
        g2.setColor(Color.WHITE);
        g2.drawString(text, 107, 27);

        text = player.currentArmor + "/" + player.maxArmor;
        g2.drawString(text, 107, 60);

        text = player.currentMana + "/" + player.maxMana;
        g2.drawString(text, 90, 93);
    }

}
