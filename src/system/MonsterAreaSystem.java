package system;

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

    private void init() {
        MonsterArea area1 = new MonsterArea("area1");
        area1.addMonster(playing.monsters[14]);
        System.out.print(playing.monsters[14].name);
        area1.addDoor(playing.getDoorSystem().doors.get(0));
        monsterAreas.add(area1);
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
