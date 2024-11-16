package system;

import components.HitboxComponent;
import components.PositionComponent;
import components.RenderComponent;
import entities.Player;
import gamestates.Playing;
import utils.HelpMethods;

import java.awt.*;

import static utils.Constants.Player.PLAYER_SCREEN_X;
import static utils.Constants.Player.PLAYER_SCREEN_Y;
import static utils.Constants.Screen.TILE_SIZE;

public class RenderSystem {
    Playing playing;
    public RenderSystem(Playing playing) {
        this.playing = playing;
    }

    public void draw(Graphics2D g2, PositionComponent position, RenderComponent render) {
        int width = render.width, height = render.height;
        int worldX = position.worldX, worldY = position.worldY;
        Player player = playing.getPlayer();
        int playerWorldX = player.worldX;
        int playerWorldY = player.worldY;
        int screenX = worldX - playerWorldX + PLAYER_SCREEN_X;
        int screenY = worldY - playerWorldY + PLAYER_SCREEN_Y;
        if (isOnTheScreen(width, height, worldX, worldY)) {
            g2.drawImage(render.image, screenX, screenY, render.width, render.height, null);
        }
    }

    public void draw(Graphics2D g2, PositionComponent position, RenderComponent render, HitboxComponent hitbox) {
        int width = render.width, height = render.height;
        int worldX = position.worldX, worldY = position.worldY;
        Player player = playing.getPlayer();
        int playerWorldX = player.worldX;
        int playerWorldY = player.worldY;
        int screenX = worldX - playerWorldX + PLAYER_SCREEN_X;
        int screenY = worldY - playerWorldY + PLAYER_SCREEN_Y;
        if (isOnTheScreen(width, height, worldX, worldY)) {
            g2.drawImage(render.image, screenX, screenY, width, height, null);
            g2.setColor(Color.WHITE);
            g2.setStroke(new BasicStroke(3));
            g2.drawRect(screenX , screenY, hitbox.area.width, hitbox.area.height);
        }
    }



    public int getWorldY(int worldY, int height) {
        return worldY + height / 2;
    }
    public int getWorldX(int worldX, int width) {
        return worldX + width / 2;
    }

    public boolean isOnTheScreen(int worldX, int worldY, int width, int height) {
        Player player = playing.getPlayer();
        int playerWorldX = player.worldX;
        int playerWorldY = player.worldY;
        return getWorldX(worldX, width) > (playerWorldX + TILE_SIZE) - (PLAYER_SCREEN_X + TILE_SIZE) - TILE_SIZE * 3
                && getWorldX(worldX, width) < (playerWorldX + TILE_SIZE) + (PLAYER_SCREEN_X + TILE_SIZE) + TILE_SIZE * 3
                && getWorldY(worldY, height) > (playerWorldY + TILE_SIZE) - (PLAYER_SCREEN_Y + TILE_SIZE) - TILE_SIZE * 3
                && getWorldY(worldY, height) < (playerWorldY + TILE_SIZE) + (PLAYER_SCREEN_Y + TILE_SIZE) + TILE_SIZE * 3;
    }
}
