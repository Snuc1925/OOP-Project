package system;

import objects.Collectible;
import objects.Door;
import objects.OBJ_Heart;
import objects.OBJ_Mana;

import java.util.ArrayList;

import static utils.Constants.Screen.TILE_SIZE;

public class InitSystem {
    public static void initDoors(ArrayList<Door> doors) {
        //----------------- SideDoor -------------------------------------------------------------
        int renderWidth = TILE_SIZE, renderHeight = TILE_SIZE * 4 + 20;
        int hitboxWidth = TILE_SIZE + 20, hitboxHeight = TILE_SIZE * 4;

        int worldX = 18 * TILE_SIZE + 9, worldY = 12 * TILE_SIZE + TILE_SIZE / 2;
        doors.add(new Door("side_wall", worldX, worldY, renderWidth, renderHeight, hitboxWidth, hitboxHeight));

        worldX = 32 * TILE_SIZE + TILE_SIZE / 2; worldY = 12 * TILE_SIZE + TILE_SIZE / 2;
        doors.add(new Door("side_wall", worldX, worldY, renderWidth, renderHeight, hitboxWidth, hitboxHeight));

        worldX = 32 * TILE_SIZE + TILE_SIZE / 2; worldY = 39 * TILE_SIZE + TILE_SIZE / 2;
        doors.add(new Door("side_wall", worldX, worldY, renderWidth, renderHeight, hitboxWidth, hitboxHeight));

        worldX = 18 * TILE_SIZE + 9; worldY = 39 * TILE_SIZE + TILE_SIZE / 2;
        doors.add(new Door("side_wall", worldX, worldY, renderWidth, renderHeight, hitboxWidth, hitboxHeight));
        //----------------- FrontDoor --------------------------------------------------------
        renderWidth = TILE_SIZE * 3; renderHeight = TILE_SIZE * 2;
        hitboxWidth = TILE_SIZE * 3; hitboxHeight = TILE_SIZE * 2;

        worldX = 12 * TILE_SIZE + TILE_SIZE / 2; worldY = 17 * TILE_SIZE + TILE_SIZE / 2;
        doors.add(new Door("front_wall", worldX, worldY, renderWidth, renderHeight, hitboxWidth, hitboxHeight));

        worldX = 12 * TILE_SIZE + TILE_SIZE / 2; worldY = 34 * TILE_SIZE + 16;
        doors.add(new Door("front_wall", worldX, worldY, renderWidth, renderHeight, hitboxWidth, hitboxHeight));

        worldX = 37 * TILE_SIZE + TILE_SIZE / 2; worldY = 17 * TILE_SIZE + TILE_SIZE / 2;
        doors.add(new Door("front_wall", worldX, worldY, renderWidth, renderHeight, hitboxWidth, hitboxHeight));

        worldX = 37 * TILE_SIZE + TILE_SIZE / 2; worldY = 34 * TILE_SIZE + 16;
        doors.add(new Door("front_wall", worldX, worldY, renderWidth, renderHeight, hitboxWidth, hitboxHeight));
    }

    public static void initCollectibleObjects(ArrayList<Collectible> collectibles) {
        OBJ_Heart objHeart1 = new OBJ_Heart("heart", 9 * TILE_SIZE, 10 * TILE_SIZE, 2);
        OBJ_Heart objHeart2 = new OBJ_Heart("heart", 6 * TILE_SIZE, 15 * TILE_SIZE, 2);
        OBJ_Mana objMana1 = new OBJ_Mana("mana", 8 * TILE_SIZE, 20 * TILE_SIZE, 2);
        OBJ_Mana objMana2 = new OBJ_Mana("mana", 15 * TILE_SIZE, 10 * TILE_SIZE, 2);
        OBJ_Mana objMana3 = new OBJ_Mana("mana", 12 * TILE_SIZE, 12 * TILE_SIZE, 2);

        collectibles.add(objHeart1);
        collectibles.add(objHeart2);
        collectibles.add(objMana1);
        collectibles.add(objMana2);
        collectibles.add(objMana3);
    }
}
