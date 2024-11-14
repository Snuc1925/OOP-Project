package inputs;

import java.awt.event.*;

import gamestates.Gamestate;
import main.GamePanel;

public class KeyboardInputs extends KeyAdapter implements KeyListener  {

    private final GamePanel gamePanel;
    public boolean upPressed, downPressed, leftPressed, rightPressed;
    public boolean enterPressed;
    public boolean shiftPressed;
    public boolean spacePressed;
    public boolean mousePressed;
    public boolean skillActivePressed; // K pressed
    public boolean attackPressed; // J pressed
    public boolean changWeaponPressed; // L pressed

    public KeyboardInputs(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        gamePanel.addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                mousePressed = true;
//                System.out.println("Mouse pressed at: (" + e.getX() + ", " + e.getY() + ")");
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                mousePressed = false;
//                System.out.println("Mouse released at: (" + e.getX() + ", " + e.getY() + ")");
            }
        });
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_W) {
            upPressed = false;
        }
        if (code == KeyEvent.VK_A) {
            leftPressed = false;
        }
        if (code == KeyEvent.VK_S) {
            downPressed = false;
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = false;
        }
        if (code == KeyEvent.VK_ENTER) {
            enterPressed = false;
        }
        if (code == KeyEvent.VK_SHIFT) {
            shiftPressed = false;
        }
        if (code == KeyEvent.VK_SPACE) {
            spacePressed = false;
        }
        if (code == KeyEvent.VK_K) {
            skillActivePressed = false;
        }
        if (code == KeyEvent.VK_J) {
            attackPressed = false;
        }
        if (code == KeyEvent.VK_L) {
            changWeaponPressed = false;
        }

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_W) {
            upPressed = true;
        }
        if (code == KeyEvent.VK_A) {
            leftPressed = true;
        }
        if (code == KeyEvent.VK_S) {
            downPressed = true;
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = true;
        }
        if (code == KeyEvent.VK_ENTER) {
            enterPressed = true;
        }
        if (code == KeyEvent.VK_SHIFT) {
            shiftPressed = true;
        }
        if (code == KeyEvent.VK_SPACE) {
            spacePressed = true;
        }
        if (code == KeyEvent.VK_K) {
            skillActivePressed = true;
        }
        if (code == KeyEvent.VK_J) {
            attackPressed = true;
        }
        if (code == KeyEvent.VK_L) {
            changWeaponPressed = true;
        }
    }
    public int getMouseX() {
        return gamePanel.getMouseX();
    }
    public int getMouseY() {
        return gamePanel.getMouseY();
    }

    public static boolean previousResult = false;
    public static boolean isPressedValid(boolean keyPressed) {
        if (keyPressed) {
            if (previousResult) return false;
            previousResult = true;
            return true;
        }
        previousResult = false;
        return false;
    }

}
