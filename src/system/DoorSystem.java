package system;
import gamestates.Playing;
import objects.Door;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.awt.*;

import static utils.HelpMethods.scaleImage;

public class DoorSystem {
    public ArrayList<Door> doors;
    public Playing playing;

    public DoorSystem(Playing playing) {
        this.playing = playing;
        doors = new ArrayList<>();
        InitSystem.initDoors(doors);
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
            int totalAnimationFrame = door.animation.totalAnimationFrame;
            BufferedImage image = playing.getImageManager().getObjectImage(key, numAnimationFrame - 1, totalAnimationFrame);
            door.render.image = scaleImage(image, door.render.width, door.render.height);
        }
    }

    public void draw(Graphics2D g2) {
        for (Door door : doors) {
            playing.getRenderSystem().draw(g2, door.position, door.render, door.hitbox);
//            playing.getRenderSystem().draw(g2, door.position, door.render);
        }
    }
}
