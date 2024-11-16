package objects;

import components.*;
import entities.Player;
import utils.Constants;
import utils.ImageManager;

import java.awt.image.BufferedImage;

public class OBJ_Mana extends Collectible {

    public OBJ_Mana(String name, int worldX, int worldY, int itemValue) {
        this.name = super.name + "_" + name;
        position = new PositionComponent(worldX, worldY);
        render = new RenderComponent(Constants.Object.ObjMana.WIDTH, Constants.Object.ObjMana.HEIGHT);
        hitbox = new HitboxComponent(Constants.Screen.TILE_SIZE, Constants.Screen.TILE_SIZE * 2 - 30);
        item = new ItemComponent("mana", itemValue);
        animation = new AnimationComponent(12, 5);
    }

    public void interact(Player player) {
        player.increaseMana(item.value);
    }
    public void update() {
        animation.updateAnimation();
    }
}
