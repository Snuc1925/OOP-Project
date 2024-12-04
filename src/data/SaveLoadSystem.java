package data;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import gamestates.Playing;
import main.Game;
import system.DoorSystem;
import system.GameData;
import system.MonsterAreaSystem;

import java.io.*;

public class SaveLoadSystem {
    private Playing playing;
    private Game game;
    private DoorSystem doorSystem;
    private MonsterAreaSystem monsterAreaSystem;

    ObjectMapper objectMapper;

    public SaveLoadSystem(Game game) {
        this.game = game;
    }

    public SaveLoadSystem(Playing playing) {
        this.playing = playing;
        this.doorSystem = playing.getDoorSystem();
        this.monsterAreaSystem = playing.getMonsterAreaSystem();
        objectMapper = new ObjectMapper();
        objectMapper.configure(MapperFeature.AUTO_DETECT_GETTERS, false);
    }

    public void saveGame() {
        GameData gameData = new GameData();
        gameData.currentLevel = playing.currentLevel;
        gameData.player.saveData(playing.getPlayer());
        gameData.monsters.saveData(playing.monsters);
        gameData.npcsData.saveData(playing.npcArray);

        gameData.monsterAreaSystem = monsterAreaSystem;
        gameData.doorSystem = doorSystem;

        gameData.currentLevel = playing.currentLevel;

        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File("save.json"), gameData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadGame() {
        loadGame("save");
    }

    public void loadGame(String filePath) {

        try {
            GameData gameData = objectMapper.readValue(new File(filePath + ".json"), GameData.class);

            playing.currentLevel = gameData.currentLevel;

            gameData.player.loadData(playing.getPlayer());
            gameData.monsters.loadData(playing);
            gameData.npcsData.loadData(playing);

            doorSystem = gameData.doorSystem;
            if (doorSystem != null) {
                doorSystem.playing = playing;
            }
            playing.doorSystem = doorSystem;

            monsterAreaSystem = gameData.monsterAreaSystem;
            if (monsterAreaSystem != null) {
                monsterAreaSystem.playing = playing;
            }
            playing.monsterAreaSystem = monsterAreaSystem;
            if (gameData.currentLevel != null)
                playing.currentLevel = gameData.currentLevel;

            playing.setUpList();
            playing.loadMap();
            playing.setLevelTheme();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
}
