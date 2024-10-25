package main;

import java.awt.*;

import gamestates.Gamestate;
import gamestates.Menu;
import gamestates.Playing;
import inputs.KeyboardInputs;
import utils.ImageManager;

import static utils.Constants.Screen.*;

public class Game implements Runnable {

    private Thread gameThread;
    private ImageManager imageManager;
    private GamePanel gamePanel;
    private GameWindow gameWindow;
    private Playing playing;
    private Menu menu;
    private final CollisionChecker collisionChecker;


    public CollisionChecker getCollisionChecker() {
        return collisionChecker;
    }

    public Playing getPlaying() { return playing; }
    public Menu getMenu() { return menu; }
    public KeyboardInputs getKeyboardInputs() { return gamePanel.getKeyboardInputs(); }

    public Game() {
        initClasses();

        imageManager = ImageManager.getInstance();
        gamePanel = new GamePanel(this);
        gameWindow = new GameWindow(gamePanel);
        collisionChecker = new CollisionChecker(this);

        startGameLoop();

    }

    private void initClasses() {
        menu = new Menu(this);
        playing = new Playing(this);
    }

    private void startGameLoop() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double timePerFrame = 1000000000.0 / FPS_SET;
        double timePerUpdate = 1000000000.0 / UPS_SET;

        long previousTime = System.nanoTime();

        int frames = 0;
        int updates = 0;
        long lastCheck = System.currentTimeMillis();

        double deltaU = 0;
        double deltaF = 0;

        while(true) {
            long currentTime = System.nanoTime();

            deltaU += (currentTime - previousTime) / timePerUpdate;
            deltaF += (currentTime - previousTime) / timePerFrame;
            previousTime = currentTime;

            if (deltaU >= 1) {
                update();
                updates++;
                deltaU--;
            }

            if (deltaF >= 1) {
                gamePanel.repaint();
                frames++;
                deltaF--;
            }

            if (System.currentTimeMillis() - lastCheck >= 1000) {
                lastCheck = System.currentTimeMillis();
                System.out.println("FPS: " + frames + " | UPS: " + updates);
                frames = 0;
                updates = 0;
            }

        }

    }

    public void update() {
        switch(Gamestate.state) {
            case MENU:
                menu.update(this);
                break;
            case PLAYING:
                playing.update(this);
                break;
            default:
                System.exit(0);
                break;
        }
    }

    public void render(Graphics2D g) {
        switch(Gamestate.state) {
            case MENU:
                menu.draw(g);
                break;
            case PLAYING:
                playing.draw(g);
                break;
            default:
                break;
        }
    }

}
