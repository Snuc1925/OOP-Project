package data;

import entities.Entity;
import entities.Sprite;
import entities.monsters.Monster;
import entities.npc.Npc;

import java.io.Serializable;
import java.util.ArrayList;

public class DataStorage implements Serializable {
    // Player stats
    int worldX, worldY, currentHealth, maxHealth, currentMana, maxMana, currentArmor, maxArmor;
    String direction;
    String currentWeapon;

    // Playing variables
    String currentLevel;

    int[] monstersWorldX;
    int[] monstersWorldY;
    String[] monstersName;
    int[] monstersCurrentHealth;

    String[] npcName;
    int[] npcWorldX;
    int[] npcWorldY;

}
