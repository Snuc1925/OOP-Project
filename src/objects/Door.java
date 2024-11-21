package objects;

import components.*;

import static utils.Constants.Screen.TILE_SIZE;

public class Door {
    public String name;
    public AnimationComponent animation;
    public PositionComponent position;
    public RenderComponent render;
    public HitboxComponent hitbox;
    public boolean isOpen;
    public Door(String name, int worldX, int worldY,
                int renderWidth, int renderHeight,
                int hitboxWidth, int hitboxHeight) {
        this.name = "object/door/" + name;
        position = new PositionComponent(worldX, worldY);
        render = new RenderComponent(renderWidth, renderHeight);
        hitbox = new HitboxComponent(hitboxWidth, hitboxHeight);
        animation = new AnimationComponent(8, 10);
        isOpen = false;
    }

}
