package data;

import entities.monsters.*;
import entities.monsters.bosses.BringerOfDeath;
import entities.monsters.bosses.Demon;
import entities.monsters.bosses.Samurai;
import entities.monsters.bosses.SkeletonReaper;
import entities.npc.Npc;
import entities.npc.WhiteSamurai;
import gamestates.Playing;

import java.io.*;
import entities.Player;
import main.Game;

import static java.lang.Math.min;
import static utils.Constants.Screen.TILE_SIZE;
import main.Sound;

public class SaveLoad {
    Playing playing;
    Game game;

    public SaveLoad(Playing playing) {
        this.playing = playing;
    }
    public SaveLoad(Game game) {
        this.game = game;
    }
    public void saveSettings() {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("settings.dat")));

            // Store data
            Settings settings = new Settings();
            settings.volume = game.getPause().currentVolume;
            settings.isSoundtrackOn = game.getPause().isSoundtrackOn;
            settings.isSoundEffectOn = game.getPause().isSoundEffectOn;

            oos.writeObject(settings);
        } catch (IOException e) {
            System.out.println("Failed to save settings!");
        }
    }
    public void loadSettings() {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File("settings.dat")));

            // Load data
            Settings settings = (Settings) ois.readObject();

            game.getPause().currentVolume = settings.volume;
            game.getPause().isSoundtrackOn = settings.isSoundtrackOn;
            game.getPause().isSoundEffectOn = settings.isSoundEffectOn;

            game.getPlaying().soundtrack.setVolume(settings.volume / 100f);

           if (!settings.isSoundEffectOn) game.getPlaying().soundtrack.toggleEffectMute();
           if (!settings.isSoundtrackOn) game.getPlaying().soundtrack.toggleSongMute();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
//    public void saveGame()  {
//        try {
//            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("save.dat")));
//            DataStorage ds = new DataStorage();
//
//            // Store data
//            ds.currentLevel = playing.currentLevel;
//
//            Player player = playing.getPlayer();
//            ds.currentHealth = player.currentHealth;
//            ds.maxHealth = player.maxHealth;
//            ds.currentMana = player.currentMana;
//            ds.maxMana = player.maxMana;
//            ds.currentArmor = player.currentArmor;
//            ds.maxArmor = player.maxArmor;
//            ds.direction = player.direction;
//            ds.currentWeapon = player.currentWeapon;
//            ds.worldX = player.worldX;
//            ds.worldY = player.worldY;
//
//            ds.currentLevel = playing.currentLevel;
//
//            ds.monstersName = new String[playing.monsters.length];
//            ds.monstersWorldX = new int[playing.monsters.length];
//            ds.monstersWorldY = new int[playing.monsters.length];
//            ds.monstersCurrentHealth = new int[playing.monsters.length];
//
//            for (int i = 0; i < playing.monsters.length; i++) {
//                Monster monster = playing.monsters[i];
//                if (monster == null) continue;
//                ds.monstersName[i] = monster.name;
//                ds.monstersWorldX[i] = monster.worldX;
//                ds.monstersWorldY[i] = monster.worldY;
//                ds.monstersCurrentHealth[i] = monster.currentHealth;
//            }
//
//            ds.npcName = new String[playing.npcArray.length];
//            ds.npcWorldX = new int[playing.npcArray.length];
//            ds.npcWorldY = new int[playing.npcArray.length];
//
//            for (int i = 0; i < playing.npcArray.length; i++) {
//                ds.npcName[i] = playing.npcArray[i].name;
//                ds.npcWorldX[i] = playing.npcArray[i].worldX;
//                ds.npcWorldY[i] = playing.npcArray[i].worldY;
//            }
//
////            playing.getDoorSystem().saveDoors(ds);
////            playing.getMonsterAreaSystem().saveMonsterAreas(ds);
//
//            oos.writeObject(ds);
//            playing.getSaveLoadSystem().saveGame();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//    public Monster createMonster(String name,
//                                 int worldX, int worldY, int currentHealth) {
//        if (name == null) return null;
//        Monster monster = switch (name) {
//            case "Slime" -> new Slime(playing, worldX, worldY);
//            case "BringerOfDeath" -> new BringerOfDeath(playing, worldX, worldY);
//            case "Demon" -> new Demon(playing, worldX, worldY);
//            case "Mage" -> new Mage(playing, worldX, worldY);
//            case "Morph" -> new Morph(playing, worldX, worldY);
//            case "PlantMelee" -> new PlantMelee(playing, worldX, worldY);
//            case "Samurai" -> new Samurai(playing, worldX, worldY);
//            case "Sickle" -> new Sickle(playing, worldX, worldY);
//            case "Sword_Knight" -> new SwordKnight(playing, worldX, worldY);
//            case "SkeletonReaper" -> new SkeletonReaper(playing, worldX, worldY);
//            default -> null;
//        };
//        assert monster != null;
//        monster.currentHealth = min(currentHealth, monster.maxHealth);
//        return monster;
//    }
//    public Npc createNpc(String name, int worldX, int worldY) {
//        Npc npc = null;
//        if (name.equals("White_Samurai")) {
//            npc = new WhiteSamurai(playing, worldX, worldY);
//        }
//        return npc;
//    }

//    public void loadGame() {
//        loadGame("save");
//    }
//    public void loadGame(String levelName) {
//        try {
//            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File(levelName + ".dat")));
//            // Load data
//            DataStorage ds = (DataStorage) ois.readObject();
//
//            // Set data
//            playing.currentLevel = ds.currentLevel;
//            Player player = playing.getPlayer();
//            player.currentHealth = ds.currentHealth;
//            player.maxHealth = ds.maxHealth;
//            player.currentMana = ds.currentMana;
//            player.maxMana = ds.maxMana;
//            player.currentArmor = ds.currentArmor;
//            player.maxArmor = ds.maxArmor;
//            player.direction = ds.direction;
//            player.currentWeapon = ds.currentWeapon;
//            player.worldX = ds.worldX;
//            player.worldY = ds.worldY;
//
//
//            playing.monsters = new Monster[ds.monstersName.length];
//            for (int i = 0; i < ds.monstersName.length; i++) {
//                Monster monster = createMonster(ds.monstersName[i],
//                                                ds.monstersWorldX[i], ds.monstersWorldY[i], ds.monstersCurrentHealth[i]);
//                System.out.println(ds.monstersName[i] + " " + ds.monstersWorldX[i] + " " + ds.monstersWorldY[i]);
//                playing.monsters[i] = monster;
//            }
//
//
//
//            playing.npcArray = new Npc[ds.npcName.length];
//            for (int i = 0; i < ds.npcName.length; i++) {
//                Npc npc = createNpc(ds.npcName[i], ds.npcWorldX[i], ds.npcWorldY[i]);
//                playing.npcArray[i] = npc;
//            }
//
//            playing.currentLevel = ds.currentLevel;
//
//            playing.setUpList();
//            playing.loadMap();
//            playing.setLevelTheme();
//            // playing.nextLevel = null;
//
////            playing.getDoorSystem().loadDoors(ds);
////            playing.getMonsterAreaSystem().loadMonsterAreas(ds);
////            playing.getSaveLoadSystem().loadGame("save");
//        } catch (Exception e) {
//            System.out.println("Error loading save file");
//            e.printStackTrace();
//        }
//    }
}
