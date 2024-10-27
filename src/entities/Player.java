package entities;

import enitystates.*;
import gamestates.Playing;
import java.awt.*;
import static utils.Constants.Player.*;
import static utils.Constants.Screen.*;
public class Player extends Sprite {
    // Player's states
    Attack attack;
    Idle idle;
    Run run;
    Walk walk;

    // Player's attributes
    public int maxArmor = 10, maxHealth = 12, maxMana = 200;
    public int currentArmor = 8, currentHealth = 5, currentMana = 102;
    public int attackPointSpear = 3, attackPointGun = 2;


    public Player(Playing playing) {
        super("Player", "player/Idle/Normal/down/1", playing, PLAYER_IMAGE_WIDTH, PLAYER_IMAGE_HEIGHT, TILE_SIZE * 5, TILE_SIZE * 5);
        setDefaultValues();
        attack = new Attack(this);
        idle = new Idle(this);
        run = new Run(this);
        walk = new Walk(this);
    }

    public void setDefaultValues() {
        solidArea = new Rectangle();
        solidArea.setBounds(18* SCALE, 32* SCALE, 13 * SCALE, 12 * SCALE);

        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        worldX = TILE_SIZE * 5;
        worldY = TILE_SIZE * 5;
        speed = 4;
    }


    @Override
    public void draw(Graphics2D g2) {
        int tempScreenX = PLAYER_SCREEN_X;
        int tempScreenY = PLAYER_SCREEN_Y;
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1f));
        g2.drawImage(image,tempScreenX,tempScreenY, null);
        g2.drawRect(tempScreenX + solidArea.x, tempScreenY + solidArea.y, solidArea.width, solidArea.height);

    }

    @Override
    public void update() {
        switch (currentState) {
            case IDLE:
                idle.update(this, playing.getGame().getKeyboardInputs());
                image = idle.getImage();
                break;
            case RUN:
                run.update(this, playing.getGame().getKeyboardInputs());
                image = run.getImage();
                break;
            case WALK:
                walk.update(this, playing.getGame().getKeyboardInputs());
                image = walk.getImage();
                break;
        }
    }
}
