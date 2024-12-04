package system;

import data.MonstersData;
import data.NPCsData;
import data.PlayerData;

public class GameData {
    public PlayerData player;
    public MonstersData monsters;
    public NPCsData npcsData;
    public String currentLevel;


    public DoorSystem doorSystem;
    public MonsterAreaSystem monsterAreaSystem;

    public GameData() {
        player = new PlayerData();
        monsters = new MonstersData();
        npcsData = new NPCsData();
    }

}
