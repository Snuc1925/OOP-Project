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
    public Door(String name, int worldX, int worldY, int width, int height) {
        this.name = "Object_Door_" + name;
        position = new PositionComponent(worldX, worldY);
        render = new RenderComponent(width, height);
        hitbox = new HitboxComponent(width, height);
        animation = new AnimationComponent(8, 10);
        isOpen = false;
//        animation.numAnimationFrame = animation.totalAnimationFrame;
    }

}
