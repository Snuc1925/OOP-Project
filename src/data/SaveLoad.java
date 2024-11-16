package data;

import entities.monsters.*;
import entities.npc.Npc;
import entities.npc.WhiteSamurai;
import gamestates.Playing;

import java.io.*;
import entities.Player;

public class SaveLoad {
    Playing playing;
    public SaveLoad(Playing playing) {
        this.playing = playing;
    }
    public void saveGame()  {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("save.dat")));
            DataStorage ds = new DataStorage();

            // Store data
            ds.currentLevel = playing.currentLevel;

            Player player = playing.getPlayer();
            ds.currentHealth = player.currentHealth;
            ds.maxHealth = player.maxHealth;
            ds.currentMana = player.currentMana;
            ds.maxMana = player.maxMana;
            ds.currentArmor = player.currentArmor;
            ds.maxArmor = player.maxArmor;
            ds.direction = player.direction;
            ds.currentWeapon = player.currentWeapon;
            ds.worldX = player.worldX;
            ds.worldY = player.worldY;

            ds.monstersName = new String[playing.monsters.length];
            ds.monstersWorldX = new int[playing.monsters.length];
            ds.monstersWorldY = new int[playing.monsters.length];
            ds.monstersCurrentHealth = new int[playing.monsters.length];

            for (int i = 0; i < playing.monsters.length; i++) {
                Monster monster = playing.monsters[i];
                ds.monstersName[i] = monster.name;
                ds.monstersWorldX[i] = monster.worldX;
                ds.monstersWorldY[i] = monster.worldY;
                ds.monstersCurrentHealth[i] = monster.currentHealth;
            }

            ds.npcName = new String[playing.npcArray.length];
            ds.npcWorldX = new int[playing.npcArray.length];
            ds.npcWorldY = new int[playing.npcArray.length];

            for (int i = 0; i < playing.npcArray.length; i++) {
                ds.npcName[i] = playing.npcArray[i].name;
                ds.npcWorldX[i] = playing.npcArray[i].worldX;
                ds.npcWorldY[i] = playing.npcArray[i].worldY;
            }

            oos.writeObject(ds);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public Monster createMonster(String name,
                                 int worldX, int worldY, int currentHealth) {
        Monster monster = switch (name) {
            case "Slime" -> new Slime(playing, worldX, worldY);
            case "BringerOfDeath" -> new BringerOfDeath(playing, worldX, worldY);
            case "Demon" -> new Demon(playing, worldX, worldY);
            case "Mage" -> new Mage(playing, worldX, worldY);
            case "Morph" -> new Morph(playing, worldX, worldY);
            case "PlantMelee" -> new PlantMelee(playing, worldX, worldY);
            case "Samurai" -> new Samurai(playing, worldX, worldY);
            case "Sickle" -> new Sickle(playing, worldX, worldY);
            case "Sword_Knight" -> new SwordKnight(playing, worldX, worldY);
            default -> null;
        };
        assert monster != null;
        monster.currentHealth = currentHealth;
        return monster;
    }
    public Npc createNpc(String name, int worldX, int worldY) {
        Npc npc = null;
        if (name.equals("White_Samurai")) {
            npc = new WhiteSamurai(playing, worldX, worldY);
        }
        return npc;
    }
    public void loadGame() {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File("save.dat")));
            // Load data
            DataStorage ds = (DataStorage) ois.readObject();

            // Set data
            playing.currentLevel = ds.currentLevel;
            Player player = playing.getPlayer();
            player.currentHealth = ds.currentHealth;
            player.maxHealth = ds.maxHealth;
            player.currentMana = ds.currentMana;
            player.maxMana = ds.maxMana;
            player.currentArmor = ds.currentArmor;
            player.maxArmor = ds.maxArmor;
            player.direction = ds.direction;
            player.currentWeapon = ds.currentWeapon;
            player.worldX = ds.worldX;
            player.worldY = ds.worldY;

            playing.monsters = new Monster[ds.monstersName.length];
            for (int i = 0; i < ds.monstersName.length; i++) {
                Monster monster = createMonster(ds.monstersName[i],
                                                ds.monstersWorldX[i], ds.monstersWorldY[i], ds.monstersCurrentHealth[i]);
                playing.monsters[i] = monster;
            }

            playing.npcArray = new Npc[ds.npcName.length];
            for (int i = 0; i < ds.npcName.length; i++) {
                Npc npc = createNpc(ds.npcName[i], ds.npcWorldX[i], ds.npcWorldY[i]);
                playing.npcArray[i] = npc;
            }

            playing.setUpList();

        } catch (Exception e) {
            System.out.println("Error loading save file");
        }
    }
}
