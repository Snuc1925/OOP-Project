package components;

public class AnimationComponent {
    private int totalAnimationFrame;
    private int numAnimationFrame;
    private int frameDuration;
    private int frameCounter;

    private boolean completeAnimation;


    public AnimationComponent(int totalAnimationFrame, int frameDuration) {
        this.totalAnimationFrame = totalAnimationFrame;
        this.frameDuration = frameDuration;
        this.numAnimationFrame = 1;
        this.frameCounter = 0;
        this.completeAnimation = false;
    }

    public void updateAnimation() {
        frameCounter++;
        if (frameCounter >= frameDuration) {
            frameCounter = 0;
            numAnimationFrame += 1;
            if (numAnimationFrame == totalAnimationFrame) {
                completeAnimation = true;
            }
            if (numAnimationFrame > totalAnimationFrame) {
                numAnimationFrame -= totalAnimationFrame;
                completeAnimation = false;
            }
        }
    }
    public void reverseAnimation() {
        frameCounter++;
        if (frameCounter >= frameDuration) {
            frameCounter = 0;
            numAnimationFrame -= 1;
            if (numAnimationFrame == 1) {
                completeAnimation = true;
            }
            if (numAnimationFrame == 0) {
                numAnimationFrame = totalAnimationFrame;
                completeAnimation = false;
            }
        }
    }

    public boolean checkCompleteAnimation() {
        return completeAnimation;
    }
    public int getCurrentFrame() {
        return numAnimationFrame;
    }

}
