package system;
import components.PositionComponent;
import gamestates.Playing;
import objects.Door;

import java.util.ArrayList;
import java.awt.*;

import static utils.Constants.Screen.TILE_SIZE;

public class DoorSystem {
    public ArrayList<Door> doors;
    public Playing playing;

    public DoorSystem(Playing playing) {
        this.playing = playing;
        doors = new ArrayList<>();
        initDoors();
    }

    public void initDoors() {
        doors.add(new Door("side", 18 * TILE_SIZE, 11 * TILE_SIZE, TILE_SIZE * 3, TILE_SIZE * 3));
    }

    public void update() {
        for (Door door : doors) {
            boolean collisionPlayer = playing.getGame().getCollisionChecker().checkPlayer(door.hitbox, door.position);
            if (!door.isOpen) {
                if (collisionPlayer) door.isOpen = true;
            } else {
                if (!collisionPlayer) door.isOpen = false;
            }

            if (door.isOpen) {
                door.animation.playAnAnimation();
            } else {
                door.animation.playAnAnimationReverse();
            }

            String key = door.name;
            int numAnimationFrame = door.animation.numAnimationFrame;
//            int width = door.render.width;
//            int height = door.render.height;
            door.render.image = playing.getImageManager().getObjectImage(key, numAnimationFrame - 1, TILE_SIZE * 3, TILE_SIZE * 8);
        }
    }

    public void draw(Graphics2D g2) {
        for (Door door : doors) {
            playing.getRenderSystem().draw(g2, door.position, door.render, door.hitbox);
        }
    }
}
