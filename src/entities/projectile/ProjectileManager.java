package entities.projectile;

import static enitystates.EntityState.*;

import enitystates.EntityState;
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
    Playing playing;

    public ProjectileManager(Playing playing) {
        projectileList = new ArrayList<>();
        this.playing = playing;
    }

    public void update() {
        Iterator<Projectile> iterator = projectileList.iterator();

        while (iterator.hasNext()) {
            Projectile projectile = iterator.next();
            playing.getGame().getCollisionChecker().checkPlayer(projectile);
            if (projectile.collisionOn == true) {
                playing.getPlayer().currentHealth -= projectile.attackPoints;
                if (playing.getPlayer().currentHealth <= 0) {
                    playing.getPlayer().currentState = EntityState.DEATH;
                }
            }
            playing.getGame().getCollisionChecker().checkTile(projectile);
            if (projectile.collisionOn == true) {
                iterator.remove();
            } else {
                projectile.update();
            }
        }
    }

    public void addProjectile(Projectile projectile) {
        projectileList.add(projectile);
    }

    public void draw(Graphics2D g2) {
        for (Projectile projectile : projectileList) {
            projectile.draw(g2);
        }
    }
}
