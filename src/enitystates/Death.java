package enitystates;

import entities.Monster;
import entities.Slime;
import entities.Sprite;
import utils.ImageLoader;
import entities.Player;

import java.awt.image.BufferedImage;

public class Death extends EntityStateMethods{
    public Death(Sprite entity, int totalAnimationFrames, int frameDuration) {
        super(entity, totalAnimationFrames, frameDuration);
        state = "DEATH";
    }

    public Death(Sprite entity) {
        super(entity);
        state = "DEATH";
    }

    @Override
    public void update(Sprite entity) {
    }


    int frameCounter = 0;
    int animationIndex = 0;
    @Override
    public BufferedImage getImage() {
        ImageLoader.initialize();
        imageManager = ImageLoader.imageManager;
        frameCounter++;
        if (frameCounter >= frameDuration) {
            animationIndex++;
            if (animationIndex + 1 > totalAnimationFrames)
                animationIndex = totalAnimationFrames - 1;
            frameCounter = 0;
        }
        if (entity instanceof Slime) {
             return imageManager.getMonsterImage(entity.name, state, entity.direction, animationIndex + 1);
        }

        if (entity instanceof Player player) {

            return imageManager.getPlayerImage(state, player.currentWeapon, player.direction, animationIndex + 1);
        }

        return null;
    }
}
