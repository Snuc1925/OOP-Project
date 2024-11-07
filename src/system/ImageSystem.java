package system;

import entities.projectile.Projectile;
import entities.projectile.ProjectileManager;
import utils.ImageLoader;
import utils.ImageManager;
import java.awt.image.BufferedImage;


public class ImageSystem {
    private ImageManager imageManager;
    private ProjectileManager projectileManager;
    public ImageSystem() {
        ImageLoader.initialize();
        imageManager = ImageLoader.imageManager;
    }
    public void updateImage(Projectile projectile) {
        projectile.numAnimationFrame = (projectile.numAnimationFrame + 1) % projectile.totalAnimationFrame;
        BufferedImage image = imageManager.getProjectileImage(
                "MONSTER_SLIME",
                projectile.numAnimationFrame + 1,
                projectile.direction);
        projectile.image = image;
    }
}
