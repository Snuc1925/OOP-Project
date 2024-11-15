package objects;

import components.*;
import entities.Player;

public class Collectible {
    public RenderComponent render;
    public PositionComponent position;
    public HitboxComponent hitbox;
    public ItemComponent item;

    public Collectible(PositionComponent position,
                       RenderComponent render,
                       HitboxComponent hitbox,
                       ItemComponent item) {
        this.position = position;
        this.render = render;
        this.hitbox = hitbox;
        this.item = item;
    }

    public void update() {}
    public void draw() {}

    public void interact(Player player) {

    }

}
