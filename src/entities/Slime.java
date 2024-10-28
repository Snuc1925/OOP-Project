package entities;

import enitystates.Attack;
import enitystates.EntityState;
import enitystates.Idle;
import enitystates.Walk;
import gamestates.Playing;

import java.awt.*;
import java.util.Random;

import static utils.Constants.Screen.TILE_SIZE;

public class Slime extends Monster {

    public Slime(Playing playing, int worldX, int worldY) {
        super("Slime", "Monster/Slime/Idle/Down/1", playing, TILE_SIZE, TILE_SIZE * 2);
        currentState = EntityState.IDLE;
        attack = new Attack(this, 6, 10);
        idle = new Idle(this, 5, 10);
        walk = new Walk(this, 5, 10);
        this.worldX = worldX;
        this.worldY = worldY;
        speed = 2;

        solidArea = new Rectangle(0, TILE_SIZE*3/2, TILE_SIZE, TILE_SIZE/2);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }

}
