package utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class ImageManager {

    private static ImageManager instance;
    Map<String, BufferedImage> guiImages;
    Map<String, BufferedImage> monsterImages;
    Map<String, BufferedImage> effectImages;
    Map<String, BufferedImage> npcImages;
    Map<String, BufferedImage> projectileImages;
    Map<String, BufferedImage> objectImages;

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
        guiImages = loadAllImages("GUI");
        monsterImages = loadAllImages("MONSTER");
        effectImages = loadAllImages("EFFECT");
        npcImages = loadAllImages("NPC");
        projectileImages = loadAllImages("PROJECTILE");
        npcImages = loadAllImages("NPC");
        objectImages = loadAllImages("OBJECT");
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
//                    System.out.println(key);
                } catch (IOException e) {
                    System.err.println("Failed to load image: " + file.getPath());
                    e.printStackTrace();
                }
            }
        }
    }

//    public BufferedImage getPlayerImage(String state, String weapon, String direction, int numAnimationFrame, int width, int height) {
//        String key = "PLAYER_" + state + "_";
//        if (!state.equals("RELOADING")) {
//            key += weapon + "_";
//        }
//        key += direction.toUpperCase() + "_1-SHEET";
//        return playerImages.get(key).getSubimage(width * numAnimationFrame, 0, width, height);  // Trả về ảnh từ bộ nhớ
//    }

    public static BufferedImage loadBufferedImage(String resourcePath) {
        try {
            return ImageIO.read(Objects.requireNonNull(
                    ImageManager.class.getResource(resourcePath)
            ));
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
            return null;
        }
    }

    public BufferedImage getPlayerImage(String state, String weapon, String direction, int numAnimationFrame, int width, int height) {
        String path = "/Player";
        if (state.equals("ATTACK")) path += "/Attack";
        else if (state.equals("RUN")) path += "/Run";
        else if (state.equals("WALK")) path += "/Walk";
        else if (state.equals("IDLE")) path += "/Idle";
        else path += "/Death";
//        if (!state.equals("RELOADING")) {
//            path += weapon + "/";
//        }
        if (weapon.equals("NORMAL")) path += "/Normal";
        else if (weapon.equals("SPEAR")) path += "/Spear/vfx";
        else path += "/Gun";

        path += "/" + direction + "/1-sheet.png";

        BufferedImage image = loadBufferedImage(path);
        System.out.println(path);

        image = HelpMethods.scaleImage(image, Constants.Screen.SCALE);

        if (image == null) return null;
        return image.getSubimage(width * numAnimationFrame, 0, width, height);  // Trả về ảnh từ bộ nhớ

    }


    public BufferedImage getGuiImage(String name) {
        String key = "GUI_" + name;
        return guiImages.get(key);
    }

    public BufferedImage getMonsterImage(String name, String state, String direction, int numAnimationFrame, int width, int height) {
        String key = "MONSTER_" + name.toUpperCase() + "_" + state.toUpperCase() + "_" + direction.toUpperCase() + "_1-SHEET";
        return monsterImages.get(key).getSubimage(width * numAnimationFrame, 0, width, height);
    }

    public BufferedImage getEffectImage(String name, int numAnimationFrame, int width, int height) {
        String key = "EFFECT_" + name.toUpperCase()  + "_1-SHEET";
        return effectImages.get(key).getSubimage(width * numAnimationFrame, 0, width, height);
    }
    public BufferedImage getNPCImage(String name, String state, String direction, int numAnimationFrame, int width, int height) {
        String key = "NPC_" + name.toUpperCase() + "_" + state.toUpperCase() + "_" + direction.toUpperCase() + "_1-SHEET";
        return npcImages.get(key).getSubimage(width * numAnimationFrame, 0, width, height);
    }

    public BufferedImage getProjectileImage(String name, String state, String direction, int numAnimationFrame) {
        String key = "PROJECTILE_" + name.toUpperCase() + "_" + state + "_" + numAnimationFrame + "_" + direction.toUpperCase();
        System.out.println(key);
        BufferedImage img = projectileImages.get(key);
        return img;
    }

    public BufferedImage getObjectImage(String key, int numAnimationFrame, int width, int height) {
        key = key.toUpperCase();
        System.out.println(key);
        return objectImages.get(key).getSubimage(width * numAnimationFrame, 0, width, height);
    }

    public BufferedImage getObjectImage(String key, int numAnimationFrame, int totalAnimationFrame) {
        key = key.toUpperCase();
        System.out.println(key);
        BufferedImage image = objectImages.get(key);
        int width = image.getWidth() / totalAnimationFrame, height = image.getHeight();
        return objectImages.get(key).getSubimage(width * numAnimationFrame, 0, width, height);
    }
}
