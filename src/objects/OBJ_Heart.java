package objects;

import components.*;
import entities.Player;
import static utils.Constants.Screen.TILE_SIZE;

public class OBJ_Heart extends Collectible {

    public OBJ_Heart(String name, int worldX, int worldY, int itemValue) {
        this.name = super.name + "_" + name;
        position = new PositionComponent(worldX, worldY);
        render = new RenderComponent(TILE_SIZE, TILE_SIZE * 2);
        hitbox = new HitboxComponent(TILE_SIZE / 2, TILE_SIZE / 2);
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
