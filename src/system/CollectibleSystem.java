package system;

import components.PositionComponent;
import components.RenderComponent;
import gamestates.Playing;
import main.CollisionChecker;
import objects.Collectible;
import objects.OBJ_Heart;

import java.awt.*;
import java.util.ArrayList;

public class CollectibleSystem {
    public ArrayList<Collectible> collectibleList;
    public Playing playing;
    public CollectibleSystem(Playing playing) {
        this.playing = playing;
        collectibleList = new ArrayList<>();
        initCollectibleObjects();
    }

    public void initCollectibleObjects() {
//        OBJ_Heart objHeart = new OBJ_Heart(new PositionComponent(20, 30),
//                                           new RenderComponent))
    }

    public void update() {
        for (Collectible collectible : collectibleList) {
            if (collectible.hitbox.area.intersects(playing.getPlayer().solidArea)) {
                collectible.interact(playing.getPlayer());
                collectible.item.isCollected = true;
            } else {
                collectible.update();
            }
        }
        collectibleList.removeIf(collectible -> collectible.item.isCollected);
    }

    public void draw(Graphics2D g2) {
        for (Collectible collectible : collectibleList) {
            playing.getRenderSystem().draw(g2, collectible.position, collectible.render);
        }
    }
}
