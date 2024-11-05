package entities;

import enitystates.Attack;
import enitystates.EntityState;
import gamestates.Playing;

import static enitystates.EntityState.*;

public class Sprite extends Entity{
    public int speed;
    public String direction = "down";
    public boolean isIdling = true;

    public Sprite(String name, String image_path, Playing playing, int width, int height) {
        super(name, image_path, playing, width, height);
    }

    public void move() {
        playing.getGame().getCollisionChecker().checkTile(this);
        playing.getGame().getCollisionChecker().checkEntity(this, getPlaying().entityList);
        if (collisionOn) return;
//        if (name.equals("Demon"))
//            System.out.println(direction);

        if (direction.equals("down")) {
            worldY += speed;
        }
        if (direction.equals("up")) {
            worldY -= speed;
        }
        if (direction.equals("left")) {
            worldX -= speed;
        }
        if (direction.equals("right")) {
            worldX += speed;
        }
        if (direction.equals("right_down")) {
            worldX += speed - 1;
            worldY += speed - 1;
        }
        if (direction.equals("left_up")) {
            worldX -= speed - 1;
            worldY -= speed - 1;
        }
        if (direction.equals("left_down")) {
            worldX -= speed - 1;
            worldY += speed - 1;
        }
        if (direction.equals("right_up")) {
            worldX += speed - 1;
            worldY -= speed - 1;
        }
    }

    public String getDirectionForAttacking(Entity target) {
        int dx = target.getWorldX() - worldX;
        int dy = target.getWorldY() - worldY;
        double angle = (Math.atan2(dy, dx) * 180 / Math.PI);
        if (angle >= -22 && angle < 22)  return "right";
        else if (angle >= 22 && angle < 67) return "right_down";
        else if (angle >= 67 && angle < 112) return "down";
        else if (angle >= 112 && angle < 157) return "left_down";
        else if (angle >= 157 || angle < -157) return "left";
        else if (angle >= -157 && angle < -112) return "left_up";
        else if (angle >= -112 && angle < -67) return "up";
        else return "right_up";
    }
    public void knock_back(int speed, String direction) {
        String temp = this.direction;
        int tempSpeed = this.speed;
        this.direction = direction;
        this.speed = speed;
        move();
        this.direction = temp;
        this.speed = tempSpeed;
    }

}
