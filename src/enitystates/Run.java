package enitystates;

import entities.Sprite;
import utils.ImageManager;

public class Run extends EntityStateMethods{
    public Run(Sprite entity, int totalAnimationFrames, int frameDuration) {
        super(entity, totalAnimationFrames, frameDuration);
        state = "RUN";
    }

    public Run(Sprite entity) {
        super(entity);
        state = "RUN";
    }

    @Override
    public void update(Sprite entity) {

    }
}
