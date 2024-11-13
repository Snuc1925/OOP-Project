package effect;

import entities.Player;
import entities.monsters.Mage;
import entities.monsters.Monster;

import java.awt.*;

import static utils.Constants.Screen.SCALE;

public class MagicCircle extends EffectMethod{
    Mage mage;
    public MagicCircle(Mage mage, int worldX, int worldY, int index) {
        super("MagicCircle", 10, mage, mage.getPlaying().getPlayer(), worldX, worldY, index);
        this.mage = mage;

        effectRect = new Rectangle(26 * SCALE * 2, 22 * SCALE * 2, 74 * SCALE * 2, 27 * SCALE * 2);
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
        super.update(5, false);
    }

    @Override
    public void removeEffect(int index) {
        mage.removeEffect(index);
    }
}
