package system;

import data.DataStorage;
import gamestates.Playing;
import objects.Door;
import objects.MonsterArea;

import java.util.ArrayList;

public class MonsterAreaSystem {
    Playing playing;
    public ArrayList<MonsterArea> monsterAreas;

    public MonsterAreaSystem(Playing playing) {
        this.playing = playing;
        monsterAreas = new ArrayList<>();
        init();
    }

//    public void saveMonsterAreas(DataStorage ds) {
//        ds.monsterAreas = monsterAreas;
//    }
//
//    public void loadMonsterAreas(DataStorage ds) {
//        monsterAreas = ds.monsterAreas;
//        System.out.println("MonsterAreas size: " + monsterAreas.size());
//    }

    private void init() {
        MonsterArea area1 = new MonsterArea("area1");
        for (int i = 0; i < 6; i++) addMonster(area1, i);
        addDoor(area1, 1); addDoor(area1, 7);

        MonsterArea area2 = new MonsterArea("area2");
        for (int i = 6; i < 11; i++) addMonster(area2, i);
        addDoor(area2, 3); addDoor(area2, 6);

        MonsterArea area3 = new MonsterArea("area3");
        for (int i = 11; i < 14; i++) addMonster(area3, i);
        addDoor(area3, 2); addDoor(area3, 5);

        MonsterArea area4 = new MonsterArea("area4");
        addMonster(area4, 14);
        addDoor(area4, 0); addDoor(area4, 4);

        monsterAreas.add(area1);
        monsterAreas.add(area2);
        monsterAreas.add(area3);
        monsterAreas.add(area4);
    }

    private void addDoor(MonsterArea ma, int doorId) {
        ma.addDoor(playing.getDoorSystem().doors.get(doorId));
    }

    private void addMonster(MonsterArea ma, int monsterId) {
        ma.addMonster(playing.monsters[monsterId]);
    }

    public void playerEnteredDoor(Door door) {
        for (MonsterArea monsterArea : monsterAreas) {
            if(!monsterArea.monsters.isEmpty() && monsterArea.doors.contains(door)) {
                monsterArea.lockArea();
                System.out.println("Lock " + monsterArea.getName());
                return;
            }
        }
    }

    public void update() {
        for (MonsterArea monsterArea : monsterAreas) {
            monsterArea.update();
        }
    }
}
