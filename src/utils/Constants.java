package utils;

public class Constants {
    public static class Screen {
        public static final int ORIGINAL_TILE_SIZE = 16; // 16x16 pixels
        public static final int SCALE = 3;
        public static final int TILE_SIZE = ORIGINAL_TILE_SIZE * SCALE; // 48x48 pixels
        public static final int MAX_SCREEN_COL = 16; // 16 tiles
        public static final int MAX_SCREEN_ROW = 12;
        public static final int SCREEN_WIDTH = TILE_SIZE * MAX_SCREEN_COL; // 768 pixels
        public static final int SCREEN_HEIGHT = TILE_SIZE * MAX_SCREEN_ROW; // 576 pixels

        public static final int FPS_SET = 120;
        public static final int UPS_SET = 200;

        public static final int MAX_WORLD_COL = 50;
        public static final int MAX_WORLD_ROW = 50;
        public static final int WORLD_WIDTH = TILE_SIZE * MAX_WORLD_COL;
        public static final int WORLD_HEIGHT = TILE_SIZE * MAX_WORLD_ROW;

        public static final int PLAYER_SCREEN_X = SCREEN_WIDTH/2 - TILE_SIZE/2 - 16 * SCALE;
        public static final int PLAYER_SCREEN_Y = SCREEN_HEIGHT/2 - TILE_SIZE/2 - 16 * SCALE;
    }
}
