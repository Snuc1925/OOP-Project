package entities.projectile;

import static enitystates.EntityState.*;
import entities.Entity;
import gamestates.Playing;
import utils.Constants;
import utils.HelpMethods;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;

public class ProjectileManager {
    ArrayList<Projectile> projectileList;
    Entity entity;

    BufferedImage image;
    String image_path;
    Playing playing;

    String direction;
    int speed;

    public ProjectileManager(Entity entity, String image_path, Playing playing) {
        this.entity = entity;
        this.playing = playing;
        image = HelpMethods.setUp(image_path, Constants.Projectile.WIDTH, Constants.Projectile.HEIGHT);
    }

    public void changeProjectile(String image_path) {

    }

    public void projectileAttack(String direction) {
        Projectile projectile = new Projectile(playing,image_path, entity.worldX, entity.worldY, direction, speed);
    }

    public void update() {
        Iterator<Projectile> iterator = projectileList.iterator();

        while (iterator.hasNext()) {
            Projectile projectile = iterator.next();
            if (projectile.collisionOn == true) {
                iterator.remove();
            } else {
                projectile.update();
            }
        }
    }

    public void draw(Graphics2D g2) {
        for (Projectile projectile : projectileList) {
            projectile.draw(g2);
        }
    }
}
