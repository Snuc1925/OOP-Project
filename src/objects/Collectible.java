package objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
        this.name = "object";
    }

    public void update() {}

    public void interact(Player player) {

    }

}
