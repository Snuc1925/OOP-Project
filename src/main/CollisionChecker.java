package main;

import entities.Entity;
import entities.Sprite;
import entities.Player;
import tile.TileManager;

import java.util.ArrayList;

import static utils.Constants.Screen.*;

public class CollisionChecker {

    Game game;
    TileManager tileManager;
    public CollisionChecker(Game game) {
        this.game = game;
    }

    public void checkTile(Sprite entity) {
        tileManager = entity.getPlaying().getTileManager();
        int entityLeftWorldX = entity.worldX + entity.solidArea.x;
        int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.worldY + entity.solidArea.y;
        int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;


        int entityLeftCol = entityLeftWorldX/TILE_SIZE;
        int entityRightCol = entityRightWorldX/TILE_SIZE;
        int entityTopRow = entityTopWorldY/TILE_SIZE;
        int entityBottomRow = entityBottomWorldY/TILE_SIZE;

        int tileNum1 = 0, tileNum2 = 0;

        switch (entity.direction) {
            case "up":
                entityTopRow = (entityTopWorldY - entity.speed)/TILE_SIZE;
                tileNum1 = tileManager.tileNum[entityTopRow][entityLeftCol];
                tileNum2 = tileManager.tileNum[entityTopRow][entityRightCol];
                break;
            case "down":
                entityBottomRow = (entityBottomWorldY + entity.speed)/TILE_SIZE;
                tileNum1 = tileManager.tileNum[entityBottomRow][entityLeftCol];
                tileNum2 = tileManager.tileNum[entityBottomRow][entityRightCol];
                break;
            case "left":
                entityLeftCol = (entityLeftWorldX - entity.speed)/TILE_SIZE;
                tileNum1 = tileManager.tileNum[entityTopRow][entityLeftCol];
                tileNum2 = tileManager.tileNum[entityBottomRow][entityLeftCol];
                break;
            case "right":
                entityRightCol = (entityRightWorldX + entity.speed)/TILE_SIZE;
                tileNum1 = tileManager.tileNum[entityTopRow][entityRightCol];
                tileNum2 = tileManager.tileNum[entityBottomRow][entityRightCol];
                break;
            case "left_up":
                entityLeftCol = (entityLeftWorldX - (entity.speed))/TILE_SIZE;
                entityTopRow = (entityTopWorldY - entity.speed)/TILE_SIZE;
                tileNum1 = tileManager.tileNum[entityTopRow][entityLeftCol];
                tileNum2 = tileManager.tileNum[entityTopRow][entityRightCol];
                break;
            case "right_up":
                entityRightCol = (entityRightWorldX + entity.speed)/TILE_SIZE;
                entityTopRow = (entityTopWorldY - entity.speed)/TILE_SIZE;
                tileNum1 = tileManager.tileNum[entityTopRow][entityLeftCol];
                tileNum2 = tileManager.tileNum[entityTopRow][entityRightCol];
                break;
            case "left_down":
                entityLeftCol = (entityLeftWorldX - entity.speed)/TILE_SIZE;
                entityBottomRow = (entityBottomWorldY + entity.speed)/TILE_SIZE;
                tileNum1 = tileManager.tileNum[entityBottomRow][entityLeftCol];
                tileNum2 = tileManager.tileNum[entityBottomRow][entityRightCol];
                break;
            case "right_down":
                entityRightCol = (entityRightWorldX + entity.speed)/TILE_SIZE;
                entityBottomRow = (entityBottomWorldY + entity.speed)/TILE_SIZE;
                tileNum1 = tileManager.tileNum[entityBottomRow][entityLeftCol];
                tileNum2 = tileManager.tileNum[entityBottomRow][entityRightCol];
                break;
        }
        entity.collisionOn = tileManager.tile[tileNum1].collision || tileManager.tile[tileNum2].collision;

    }
//    public int checkObject(Sprite entity, boolean player) {
//        int index = 999;
//        for (int i = 0; i < gp.superObject.length; i++) {
//            if (gp.superObject[i] != null) {
//
//                // Get entity solid area position
//                entity.solidArea.x = entity.worldX + entity.solidArea.x;
//                entity.solidArea.y = entity.worldY + entity.solidArea.y;
//                // Get the object solid area position
//                gp.superObject[i].solidArea.x = gp.superObject[i].solidArea.x + gp.superObject[i].worldX;
//                gp.superObject[i].solidArea.y = gp.superObject[i].solidArea.y + gp.superObject[i].worldY;
//
//                switch (entity.direction) {
//                    case "up":
//                        entity.solidArea.y -= entity.speed;
//                        break;
//                    case "down":
//                        entity.solidArea.y += entity.speed;
//
//                        break;
//                    case "left":
//                        entity.solidArea.x -= entity.speed;
//
//                        break;
//                    case "right":
//                        entity.solidArea.x += entity.speed;
//                        break;
//                    case "left_up":
//                        entity.solidArea.x -= entity.speed;
//                        entity.solidArea.y -= entity.speed;
//                        break;
//                    case "right_up":
//                        entity.solidArea.x += entity.speed;
//                        entity.solidArea.y -= entity.speed;
//                        break;
//                    case "left_down":
//                        entity.solidArea.x -= entity.speed;
//                        entity.solidArea.y += entity.speed;
//                        break;
//                    case "right_down":
//                        entity.solidArea.x += entity.speed;
//                        entity.solidArea.y += entity.speed;
//                        break;
//                }
//                if (entity.solidArea.intersects(gp.superObject[i].solidArea)){
//                    if (gp.superObject[i].collision)
//                        entity.collisionOn = true;
//                    if (player) {
//                        index = i;
//                    }
//                }
//
//                gp.superObject[i].solidArea.x = gp.superObject[i].solidAreaDefaultX;
//                gp.superObject[i].solidArea.y = gp.superObject[i].solidAreaDefaultY;
//            }
//            entity.solidArea.x = entity.solidAreaDefaultX;
//            entity.solidArea.y = entity.solidAreaDefaultY;
//        }
//
//        return index;
//    }

    // NPC and monster collision
    public int checkEntity(Sprite entity, ArrayList<Entity> entityArrayList) {
        int index = -1;
        for (int i = 0; i < entityArrayList.size(); i++) {
            Entity target = entityArrayList.get(i);
            if (target != null) {
                // Get entity solid area position
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;

                // Get the target solid area position
                target.solidArea.x = target.solidArea.x + target.worldX;
                target.solidArea.y = target.solidArea.y + target.worldY;

                switch (entity.direction) {
                    case "up":
                        entity.solidArea.y -= entity.speed;
                        break;
                    case "down":
                        entity.solidArea.y += entity.speed;
                        break;
                    case "left":
                        entity.solidArea.x -= entity.speed;
                        break;
                    case "right":
                        entity.solidArea.x += entity.speed;
                        break;
                    case "left_up":
                        entity.solidArea.x -= entity.speed;
                        entity.solidArea.y -= entity.speed;
                        break;
                    case "right_up":
                        entity.solidArea.x += entity.speed;
                        entity.solidArea.y -= entity.speed;
                        break;
                    case "left_down":
                        entity.solidArea.x -= entity.speed;
                        entity.solidArea.y += entity.speed;
                        break;
                    case "right_down":
                        entity.solidArea.x += entity.speed;
                        entity.solidArea.y += entity.speed;
                        break;
                }
                if (entity.solidArea.intersects(target.solidArea) && target != entity) {
                    entity.collisionOn = true;
                    index = i;
                }

                target.solidArea.x = target.solidAreaDefaultX;
                target.solidArea.y = target.solidAreaDefaultY;
            }
            entity.solidArea.x = entity.solidAreaDefaultX;
            entity.solidArea.y = entity.solidAreaDefaultY;
        }

        return index;
    }

    public boolean checkPlayer(Sprite entity) {
        Player player = game.getPlaying().getPlayer();
        
        tileManager = entity.getPlaying().getTileManager();
        boolean contactPlayer = false;

        // Get entity solid area position
        entity.solidArea.x = entity.worldX + entity.solidArea.x;
        entity.solidArea.y = entity.worldY + entity.solidArea.y;

        // Get the target solid area position
        player.solidArea.x = player.solidArea.x + player.worldX;
        player.solidArea.y = player.solidArea.y + player.worldY;

        switch (entity.direction) {
            case "up":
                entity.solidArea.y -= entity.speed;
                break;
            case "down":
                entity.solidArea.y += entity.speed;
                break;
            case "left":
                entity.solidArea.x -= entity.speed;
                break;
            case "right":
                entity.solidArea.x += entity.speed;
                break;
            case "left_up":
                entity.solidArea.x -= entity.speed;
                entity.solidArea.y -= entity.speed;
                break;
            case "right_up":
                entity.solidArea.x += entity.speed;
                entity.solidArea.y -= entity.speed;
                break;
            case "left_down":
                entity.solidArea.x -= entity.speed;
                entity.solidArea.y += entity.speed;
                break;
            case "right_down":
                entity.solidArea.x += entity.speed;
                entity.solidArea.y += entity.speed;
                break;
        }
        if (entity.solidArea.intersects(player.solidArea)){
            entity.collisionOn = true;

            contactPlayer = true;
        }

        player.solidArea.x = player.solidAreaDefaultX;
        player.solidArea.y = player.solidAreaDefaultY;
        entity.solidArea.x = entity.solidAreaDefaultX;
        entity.solidArea.y = entity.solidAreaDefaultY;

        return contactPlayer;
    }
}
