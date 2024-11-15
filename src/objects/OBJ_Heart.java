package objects;

import components.*;
import entities.Player;

public class OBJ_Heart extends Collectible {
    public AnimationComponent animation;

    public OBJ_Heart(PositionComponent position,
                     RenderComponent render,
                     HitboxComponent hitbox,
                     AnimationComponent animation,
                     ItemComponent item) {
        super(position, render, hitbox, item);
        this.animation = animation;
    }

    public void interact(Player player) {
        player.increaseHealth(item.value);
    }

    public void update() {
        animation.updateAnimation();
    }
}
