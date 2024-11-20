package effect;

import entities.Player;
import entities.monsters.Monster;
import entities.monsters.bosses.SkeletonReaper;

import java.awt.*;

import static utils.Constants.Screen.SCALE;

public class ElectricBurst extends EffectMethod{
    SkeletonReaper entity;
    public ElectricBurst(Monster entity, int worldX, int worldY, int index) {
        super("ElectricBurst", 27, entity,
                entity.getPlaying().getPlayer(),
                worldX,
                worldY, 72 * SCALE, 72 * SCALE, index);

        effectRect.height *= 2;
        effectRect.width *= 2;

        // Make the center of effectRect has position (worldX, worldY)
        this.worldX -= effectRect.x + effectRect.width / 2;
        this.worldY -= effectRect.y + effectRect.height / 2;
        effectRect.x += this.worldX;
        effectRect.y += this.worldY;
    }

    public void draw(Graphics2D g2) {
        super.draw(g2, 2);
    }

    public void update() {
//        super.update(14, )
    }

    @Override
    public void removeEffect(int index) {

    }
}
