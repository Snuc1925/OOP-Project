package components;

import java.awt.*;

public class HitboxComponent {
    public Rectangle area;

    public HitboxComponent(int width, int height) {
        area = new Rectangle(0, 0, width, height);
    }
}
