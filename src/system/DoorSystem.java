package system;
import components.PositionComponent;
import entities.Player;
import gamestates.Playing;
import objects.Door;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.awt.*;

import static utils.HelpMethods.scaleImage;

public class DoorSystem {
    public ArrayList<Door> doors;
    public Playing playing;

    private Door enteredDoor; // The door that player has just gone in
    private int enteredDirection;
    private int exitDirection;

    public DoorSystem(Playing playing) {
        this.playing = playing;
        doors = new ArrayList<>();
        enteredDoor = null;
        enteredDirection = exitDirection = 0;
        InitSystem.initDoors(doors);
    }

    public void update() {
        for (Door door : doors) {
            if (door.isOpen) {
                door.animation.playAnAnimation();
            } else {
                door.animation.playAnAnimationReverse();
            }

            String key = door.name;
            int numAnimationFrame = door.animation.numAnimationFrame;
            int totalAnimationFrame = door.animation.totalAnimationFrame;
            BufferedImage image = playing.getImageManager().getObjectImage(key, numAnimationFrame - 1, totalAnimationFrame);
            door.render.image = scaleImage(image, door.render.width, door.render.height);
        }
    }

    public void checkPlayerNextMove(Player player) {
        PositionComponent playerCurrent = new PositionComponent(player.worldX + player.width / 2, player.worldY + player.height / 2);
        player.goAlongDirection();
        PositionComponent playerNext = new PositionComponent(player.worldX + player.width / 2, player.worldY + player.height / 2);
        player.goOppositeDirection();

        if (enteredDoor != null) {
            exitDirection = getExitDirection(playerCurrent, playerNext, enteredDoor);
            System.out.println("EnteredDiretion: " + enteredDirection + ", ExitDirection" + exitDirection);

            if (exitDirection != 0) {
                enteredDoor.isOpen = false;
                if (exitDirection + enteredDirection == 0 && enteredDirection == enteredDoor.direction) {
                    playing.getMonsterAreaSystem().playerEnteredDoor(enteredDoor);
                }
                exitDirection = enteredDirection = 0;
                enteredDoor = null;
            }
        } else {
            for (Door door : doors) {
                enteredDirection = getEnterDirection(playerCurrent, playerNext, door);
                if (enteredDirection == 0) continue;
                if (door.isLocked) {
                    player.collisionOn = true;
                    return;
                }

                enteredDoor = door;
                enteredDoor.isOpen = true;
                break;
            }
        }
    }

    private int getEnterDirection(PositionComponent current, PositionComponent next, Door door) {
        int left = door.position.worldX - door.hitbox.area.width / 2;
        int right = door.position.worldX + door.hitbox.area.width / 2;
        int up = door.position.worldY - door.hitbox.area.height / 2;
        int down = door.position.worldY + door.hitbox.area.height / 2;
        if (left > next.worldX || next.worldX > right || up > next.worldY || next.worldY > down) return 0;
        if (current.worldX < left && left <= next.worldX) return -2;
        if (current.worldY < up && up <= next.worldY) return -1;
        if (current.worldX > right && right >= next.worldX) return 2;
        if (current.worldY > down && down >= next.worldY) return 1;
        return 0;
    }

    private int getExitDirection(PositionComponent current, PositionComponent next, Door door) {
        int left = door.position.worldX - door.hitbox.area.width / 2;
        int right = door.position.worldX + door.hitbox.area.width / 2;
        int up = door.position.worldY - door.hitbox.area.height / 2;
        int down = door.position.worldY + door.hitbox.area.height / 2;
        if (left <= next.worldX && next.worldX <= right && up <= next.worldY && next.worldY <= down) return 0;
        if (current.worldX >= left && left >= next.worldX) return -2;
        if (current.worldY >= up && up >= next.worldY) return -1;
        if (current.worldX <= right && right <= next.worldX) return 2;
        if (current.worldY <= down && down <= next.worldY) return 1;
        return 0;
    }

    public void draw(Graphics2D g2) {
        for (Door door : doors) {
            playing.getRenderSystem().draw(g2, door.position, door.render, door.hitbox);
//            playing.getRenderSystem().draw(g2, door.position, door.render);
        }
    }
}
