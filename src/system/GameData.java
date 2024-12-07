package system;

import data.MonstersData;
import data.NPCsData;
import data.PlayerData;

public class GameData {
    public String currentLevel;
    public PlayerData player;
    public MonstersData monsters;
    public NPCsData npcsData;

    public DoorSystem doorSystem;
    public MonsterAreaSystem monsterAreaSystem;

    public CollectibleSystem collectibleSystem;
    public String currentLevel;

    public GameData() {
        player = new PlayerData();u
        monsters = new MonstersData();
        npcsData = new NPCsData();
    }

}
