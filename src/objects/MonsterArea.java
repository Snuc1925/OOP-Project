package objects;

import entities.monsters.Monster;

import java.util.ArrayList;

import static enitystates.EntityState.DEATH;

public class MonsterArea {
    private String name;
    public ArrayList<Monster> monsters;
    public ArrayList<Door> doors;
    private boolean isLocked = false;

    public MonsterArea(String name, ArrayList<Monster> monsters, ArrayList<Door> doors) {
        this.name = name;
        this.monsters = monsters;
        this.doors = doors;
    }
    public MonsterArea(String name) {
        this.name = name;
        monsters = new ArrayList<>();
        doors = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void addMonster(Monster monster) {
        monsters.add(monster);
    }

    public void addDoor(Door door) {
        doors.add(door);
    }

    public void update() {
        if (isLocked) {
            monsters.removeIf(monster -> monster.currentState == DEATH);
            if (monsters.isEmpty()) {
                unlockArea();
            }
        } else {
            if (!monsters.isEmpty()) {
                for (Door door : doors) {
                    if (door.playerPassed) {
                        lockArea();
                        return;
                    }
                }
            }
        }
    }

    public void lockArea() {
        isLocked = true;
        for (Door door : doors) {
            door.isLocked = true;
        }
    }
    public void unlockArea() {
        isLocked = false;
        for (Door door : doors) {
            door.isLocked = false;
        }
    }
}
