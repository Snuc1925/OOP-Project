package enitystates;

import entities.Sprite;
import utils.ImageManager;

public class Run extends EntityStateMethods{
    public Run(Sprite entity, ImageManager imageManager, int totalAnimationFrames, int frameDuration) {
        super(entity, imageManager, totalAnimationFrames, frameDuration);
        state = "RUN";
    }

    public Run(Sprite entity, ImageManager imageManager) {
        super(entity, imageManager);
        state = "RUN";
    }

    @Override
    public void update(Sprite entity) {

    }
}
