package objects;

import components.*;
import entities.Player;
import utils.Constants;
import utils.ImageManager;

import java.awt.image.BufferedImage;

public class OBJ_Heart extends Collectible {

    public OBJ_Heart(String name, int worldX, int worldY, int itemValue) {
        this.name = super.name + "_" + name;
        position = new PositionComponent(worldX, worldY);
        render = new RenderComponent(Constants.Object.ObjHeart.WIDTH, Constants.Object.ObjHeart.HEIGHT);
        hitbox = new HitboxComponent(Constants.Screen.TILE_SIZE, Constants.Screen.TILE_SIZE * 2 - 30);
        item = new ItemComponent("heart", itemValue);
        animation = new AnimationComponent(12, 5);
    }

    public void interact(Player player) {
        player.increaseHealth(item.value);
    }
    public void update() {
        animation.updateAnimation();
    }
}
