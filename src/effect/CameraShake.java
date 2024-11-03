package effect;

public class CameraShake {
    private float amplitude;
    private float duration;
    private float timer;

    public CameraShake(float amplitude, float duration) {
        this.amplitude = amplitude;
        this.duration = duration;
        this.timer = duration;
    }

    public void update(float deltaTime) {
        timer -= deltaTime;
    }

    public float getOffsetX() {
        if (timer <= 0) return 0;
        return (float) (Math.random() * 2 - 1) * amplitude;
    }

    public float getOffsetY() {
        if (timer <= 0) return 0;
        return (float) (Math.random() * 2 - 1) * amplitude;
    }

    public boolean isShaking() {
        return timer > 0;
    }
}