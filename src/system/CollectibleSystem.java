package system;

import gamestates.Playing;
import objects.Collectible;
import objects.OBJ_Heart;
import objects.OBJ_Mana;

import java.awt.*;
import java.util.ArrayList;
import static utils.Constants.Screen.*;

public class CollectibleSystem {
    public ArrayList<Collectible> collectibleList;
    public Playing playing;
    public CollectibleSystem(Playing playing) {
        this.playing = playing;
        collectibleList = new ArrayList<>();
        initCollectibleObjects();
    }

    public void initCollectibleObjects() {
        OBJ_Heart objHeart1 = new OBJ_Heart("heart", 9 * TILE_SIZE, 10 * TILE_SIZE, 2);
        OBJ_Heart objHeart2 = new OBJ_Heart("heart", 6 * TILE_SIZE, 15 * TILE_SIZE, 2);
        OBJ_Mana objMana1 = new OBJ_Mana("mana", 8 * TILE_SIZE, 20 * TILE_SIZE, 2);
        OBJ_Mana objMana2 = new OBJ_Mana("mana", 15 * TILE_SIZE, 10 * TILE_SIZE, 2);
        OBJ_Mana objMana3 = new OBJ_Mana("mana", 12 * TILE_SIZE, 12 * TILE_SIZE, 2);

        collectibleList.add(objHeart1);
        collectibleList.add(objHeart2);
        collectibleList.add(objMana1);
        collectibleList.add(objMana2);
        collectibleList.add(objMana3);
    }


    public void update() {
        for (Collectible collectible : collectibleList) {
            if (playing.getGame().getCollisionChecker().checkPlayer(collectible.hitbox, collectible.position)) {
                collectible.interact(playing.getPlayer());
                collectible.item.isCollected = true;
            } else {
                collectible.animation.updateAnimation();
                String key = collectible.name;
                int numAnimationFrame = collectible.animation.numAnimationFrame;
                int width = collectible.render.width;
                int height = collectible.render.height;
                collectible.render.image = playing.getImageManager().getObjectImage(key, numAnimationFrame - 1, width, height);
            }
        }
        collectibleList.removeIf(collectible -> collectible.item.isCollected);
    }

    public void draw(Graphics2D g2) {
        for (Collectible collectible : collectibleList) {
            playing.getRenderSystem().draw(g2, collectible.position, collectible.render, collectible.hitbox);
        }
    }
}
