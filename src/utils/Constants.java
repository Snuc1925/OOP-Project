package utils;

public class Constants {
    public static class Screen {
        public static final int originalTileSize = 16; // 16x16 pixels
        public static final int scale = 3;
        public static final int tileSize = originalTileSize * scale; // 48x48 pixels
        public static final int maxScreenCol = 16; // 16 tiles
        public static final int maxScreenRow = 12;
        public static final int screenWidth = tileSize * maxScreenCol; // 768 pixels
        public static final int screenHeight = tileSize * maxScreenRow; // 576 pixels

        public static final int FPS_SET = 120;
        public static final int UPS_SET = 200;
    }
}
