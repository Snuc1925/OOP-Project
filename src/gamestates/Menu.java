package gamestates;

import inputs.KeyboardInputs;
import main.Game;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.InputStream;

import static utils.Constants.Screen.*;
import static utils.HelpMethods.*;

public class Menu extends State implements Statemethods {
    public Menu(Game game) {
        super(game);
    }
    public void update(Game game) {

    }

    public void draw(Graphics2D g2) {
        Font maruMonica = loadFontMaruMonica();
        g2.setFont(maruMonica);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 80F));
        g2.setColor(Color.WHITE);
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);

        // Create shadow for
        g2.setColor(Color.GRAY);
        String gameTitle = "Dungeon Game";
        int x = getXForCenterText(gameTitle, g2);
        int y = getYForCenterText(gameTitle, g2) - TILE_SIZE * 3;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 80F));
        g2.drawString(gameTitle, x + 5, y + 3);

        // Title name
        g2.setColor(Color.WHITE);
        g2.drawString(gameTitle, x, y);

        System.out.println(x + " " + y);

        // Display main character
//        x = SCREEN_WIDTH / 2 - TILE_SIZE;
//        y += TILE_SIZE / 2;
//        String direction;
//        frameCounter++;
//        if (frameCounter >= 60) {
//            directionIndex = (directionIndex + 1) % 8;
//            frameCounter = 0;
//        }
//        direction = directionArray[directionIndex];
//        BufferedImage image = switch (direction) {
//            case "up" -> gp.player.getImage("up");
//            case "down" -> gp.player.getImage("down");
//            case "left" -> gp.player.getImage("left");
//            case "right" -> gp.player.getImage("right");
//            case "up_left" -> gp.player.getImage("up_left");
//            case "up_right" -> gp.player.getImage("up_right");
//            case "down_left" -> gp.player.getImage("down_left");
//            case "down_right" -> gp.player.getImage("down_right");
//            default -> null;
//        };
//        g2.drawImage(image, x - 16 * 4, y - 16 * 4, 48 * 5, 64 * 5, null);
//
//        // Menu
//        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 32F));
//        String text = "New game";
//        x = getXForCenterText(text);
//        y = getYForCenterText(text) + gp.TILE_SIZE * 3 / 2;
//        g2.drawString(text, x, y);
//        if (commandNumber == 0) {
//            g2.drawString("->", x - gp.TILE_SIZE, y);
//        }
//
//        text = "Load game";
//        x = getXForCenterText(text);
//        y += gp.TILE_SIZE;
//        g2.drawString(text, x, y);
//        if (commandNumber == 1) {
//            g2.drawString("->", x - gp.TILE_SIZE, y);
//        }
//
//        text = "Quit";
//        x = getXForCenterText(text);
//        y += gp.TILE_SIZE;
//        g2.drawString(text, x, y);
//        if (commandNumber == 2) {
//            g2.drawString("->", x - gp.TILE_SIZE, y);
//        }
    }


}
