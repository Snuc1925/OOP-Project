package objects;

import components.*;
import entities.Player;

public class Collectible {
    public String name;
    public RenderComponent render;
    public PositionComponent position;
    public HitboxComponent hitbox;
    public ItemComponent item;
    public AnimationComponent animation;

    public Collectible() {
        this.name = "OBJECT";
    }

    public void update() {}
    public void draw() {}

    public void interact(Player player) {

    }

}
