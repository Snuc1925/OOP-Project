package gamestates;

import main.Game;
import java.awt.*;
import java.awt.image.BufferedImage;

import entities.Player;
import tile.TileManager;
import utils.HelpMethods;
import utils.ImageLoader;
import utils.ImageManager;

public class Playing extends State implements Statemethods {

    private final Player player;
    private final TileManager tileManager;

    public Playing(Game game) {
        super(game);
        player = new Player(this);
        tileManager = new TileManager(player);

    }

    public Game getGame() {
        return game;
    }
    public Player getPlayer() {
        return player;
    }

    public TileManager getTileManager() {
        return tileManager;
    }

    @Override
    public void update() {

        player.update();

    }

    @Override
    public void draw(Graphics2D g2) {
        tileManager.draw(g2);
        player.draw(g2);

        // Draw player status GUI
        ImageLoader.initialize();
        ImageManager imageManager = ImageLoader.imageManager;
        BufferedImage ui_bar_decor = imageManager.getGuiImage("UI_BAR_DECORATION");
        ui_bar_decor = HelpMethods.scaleImage(ui_bar_decor, 0.25);
        g2.drawImage(ui_bar_decor, 0, 0, null);

        BufferedImage player_health_bar = imageManager.getGuiImage("HEALTH_BAR");
        player_health_bar = HelpMethods.scaleImage(player_health_bar, 0.25);
        player_health_bar = HelpMethods.getBarImage(player_health_bar, 1.0*player.currentHealth/player.maxHealth);
        g2.drawImage(player_health_bar, 49, 10, null);

        BufferedImage player_armor_bar = imageManager.getGuiImage("ARMOR_BAR");
        player_armor_bar = HelpMethods.scaleImage(player_armor_bar, 0.25);
        player_armor_bar = HelpMethods.getBarImage(player_armor_bar, 1.0*player.currentArmor/player.maxArmor);
        g2.drawImage(player_armor_bar, 49, 43, null);

        BufferedImage player_mana_bar = imageManager.getGuiImage("MANA_BAR");
        player_mana_bar = HelpMethods.scaleImage(player_mana_bar, 0.25);
        player_mana_bar = HelpMethods.getBarImage(player_mana_bar, 1.0*player.currentMana/player.maxMana);
        g2.drawImage(player_mana_bar, 49, 75, null);

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
