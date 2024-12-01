package system;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.ObjectMapper;
import data.SaveLoad;
import gamestates.Playing;
import main.Game;
import objects.Door;
import objects.MonsterArea;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class SaveLoadSystem {
    private Playing playing;
    private DoorSystem doorSystem;
    private MonsterAreaSystem monsterAreaSystem;

//    public SaveLoadSystem(Playing playing) {
//        this.playing = playing;
//    }
//    public SaveLoadSystem(Game game) {
//        this.game = game;
//    }
    public SaveLoadSystem(Playing playing) {
        this.playing = playing;
        this.doorSystem = playing.getDoorSystem();
        this.monsterAreaSystem = playing.getMonsterAreaSystem();
    }

    public void saveGame() {
        ArrayList<Door> doors = doorSystem.doors;
        ArrayList<MonsterArea> monsterAreas = monsterAreaSystem.monsterAreas;

        GameData gameData = new GameData();
        gameData.monsterAreas = monsterAreas;
        gameData.doors = doors;

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File("save.json"), gameData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
