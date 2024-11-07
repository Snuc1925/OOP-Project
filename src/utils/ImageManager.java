package utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.io.File;

public class ImageManager {

    private static ImageManager instance;
    Map<String, BufferedImage> playerImages;
    Map<String, BufferedImage> guiImages;
    Map<String, BufferedImage> monsterImages;
    Map<String, BufferedImage> effectImages;
    Map<String, BufferedImage> projectileImages;

    public static ImageManager getInstance() {
        if (instance == null) {
            synchronized (ImageManager.class) { // Optional: Thread-safe initialization
                if (instance == null) {
                    instance = new ImageManager(); // Create the instance lazily
                }
            }
        }
        return instance;
    }


    private ImageManager() {
        playerImages = loadAllImages("PLAYER");
        guiImages = loadAllImages("GUI");
        monsterImages = loadAllImages("MONSTER");
        effectImages = loadAllImages("EFFECT");
        projectileImages = loadAllImages("PROJECTILE");
    }

    private static Map<String, BufferedImage> loadAllImages(String imagePath) {
        Map<String, BufferedImage> images = new HashMap<>();

        // Use class loader to get the base directory for Player/Attack
        URL baseURL = ImageManager.class.getResource("/" + imagePath);

        if (baseURL != null) {
            File baseDir = new File(baseURL.getPath());
            // Recursively load images from the directory
            loadImagesFromDirectory(baseDir, images, imagePath);
        } else {
            System.err.println("Base directory does not exist: " + imagePath);
        }

        return images;
    }

    // Load toan bo anh tu directory
    private static void loadImagesFromDirectory(File directory, Map<String, BufferedImage> images, String keyPrefix) {
        File[] files = directory.listFiles();
        if (files == null) return;

        for (File file : files) {
            if (file.isDirectory()) {
                String newKeyPrefix = keyPrefix + "_" + file.getName().toUpperCase();
                loadImagesFromDirectory(file, images, newKeyPrefix);  // Recursive call for subdirectories
            } else if (file.isFile() && file.getName().endsWith(".png")) {
                // Extract the image index from the file name (e.g., 1 from "1.png")
                String fileName = file.getName().substring(0, file.getName().lastIndexOf('.'));
                String key = keyPrefix + "_" + fileName.toUpperCase();

                try {
                    // Load the image and put it into the map
                    BufferedImage image = ImageIO.read(file);
                    image = HelpMethods.scaleImage(image, Constants.Screen.SCALE);
                    images.put(key, image);
                    System.out.println(key);
                } catch (IOException e) {
                    System.err.println("Failed to load image: " + file.getPath());
                    e.printStackTrace();
                }
            }
        }
    }

    public BufferedImage getPlayerImage(String state, String weapon, String direction, int numAnimationFrame) {
        String key = "PLAYER_" + state + "_";
        if (!state.equals("RELOADING")) {
            key += weapon + "_";
        }
        key += direction.toUpperCase() + "_";
        key += numAnimationFrame;
        if (state.equals("DEATH"))
            System.out.println(key);
        return playerImages.get(key);  // Trả về ảnh từ bộ nhớ
    }

    public BufferedImage getGuiImage(String name) {
        String key = "GUI_" + name;
        return guiImages.get(key);
    }

    public BufferedImage getMonsterImage(String name, String state, String direction, int numAnimationFrame) {
        String key = "MONSTER_" + name.toUpperCase() + "_" + state.toUpperCase() + "_" + direction.toUpperCase() + "_" + numAnimationFrame;
        System.out.println(key);
        return monsterImages.get(key);
    }

    public BufferedImage getEffectImage(String name, int numAnimationFrame) {
        String key = "EFFECT_" + name.toUpperCase() + "_" + numAnimationFrame;
        return effectImages.get(key);
    }

    public BufferedImage getProjectileImage(String name, int numAnimationFrame, String direction) {
        String key = "PROJECTILE_" + name + "_" + numAnimationFrame + "_" + direction;
        return projectileImages.get(key);
    }
}
