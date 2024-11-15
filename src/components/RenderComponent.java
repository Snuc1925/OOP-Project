package components;

import java.awt.image.BufferedImage;

public class RenderComponent {
    public BufferedImage image;
    public int width, height;

    public RenderComponent(BufferedImage image, int width, int height) {
        this.image = image;
        this.width = width;
        this.height = height;
    }
}
