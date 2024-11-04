package tile;

import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;
import java.util.Objects;

import static utils.Constants.Screen.*;
import static utils.Constants.Player.*;
import entities.Player;
import utils.Constants;

public class TileManager {

    private Player player;
    public Tile[] tile;
    public int tileNum[][];

    public TileManager(Player player) {
        this.player = player;
        tile = new Tile[200];
        tileNum = new int[MAX_WORLD_ROW][MAX_WORLD_COL];
        getTileImage();
        loadMap("res/maps/dungeonMap");
//        temp = new int[MAX_WORLD_ROW][MAX_WORLD_COL];
    }

//    public int temp[][];
    public boolean isWall(int row, int col) {
//        temp[row][col] = 1;
        return tileNum[row][col] >= 0 && tile[tileNum[row][col]].collision;
    }
    public void getTileImage() {
        String path = "res/dungeontiles/tileData";
        try {
            File file = new File(path);
            FileInputStream ft = new FileInputStream(file);

            DataInputStream in = new DataInputStream(ft);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strline;

            String imagePath = null;
            boolean isSolid;
            int cnt = 0;
            while ((strline = br.readLine()) != null){
                if (imagePath == null) imagePath = strline;
                else {
                    isSolid = Boolean.parseBoolean(strline);
                    String indexStr = imagePath.substring(0, imagePath.indexOf("."));
                    setUp(cnt, indexStr, isSolid);
                    cnt++;
                    imagePath = null;
                }
            }


            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void setUp(int index, String imagePath, boolean collision) {
        UtilityTool utilityTool = new UtilityTool();
        try {
            tile[index] = new Tile();
            tile[index].image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(
                    "dungeontiles/" + imagePath + ".png")));
            tile[index].image = utilityTool.scaleImage(tile[index].image, TILE_SIZE, TILE_SIZE);
            tile[index].collision = collision;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadMap(String path) {
        try {
            File file = new File(path);
            FileInputStream ft = new FileInputStream(file);

            DataInputStream in = new DataInputStream(ft);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strline;

            int row = 0;
            while ((strline = br.readLine()) != null){
                String[] numbers = strline.split(" ");
                for (int i = 0; i < MAX_WORLD_COL; i++) {
                    tileNum[row][i] = Integer.parseInt(numbers[i]);
                }
                row++;
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {
        int worldCol = 0;
        int worldRow = 0;
        
        int playerWorldX = player.worldX;
        int playerWorldY = player.worldY;

        while (worldCol < MAX_WORLD_COL && worldRow < MAX_WORLD_ROW) {
            int tileId = tileNum[worldRow][worldCol];
//            if (temp[worldRow][worldCol] == 1)
//                tileId = 99;
            if (tileId >= 0) {
                int worldX = worldCol*TILE_SIZE;
                int worldY = worldRow*TILE_SIZE;

                int screenX = worldX - (playerWorldX + TILE_SIZE) + (PLAYER_SCREEN_X + TILE_SIZE);
                int screenY = worldY - (playerWorldY + TILE_SIZE) + (PLAYER_SCREEN_Y + TILE_SIZE);

                if (worldX > (playerWorldX + TILE_SIZE) - (PLAYER_SCREEN_X + TILE_SIZE) - TILE_SIZE
                        && worldX < (playerWorldX + TILE_SIZE) + (PLAYER_SCREEN_X + TILE_SIZE) + TILE_SIZE
                        && worldY > (playerWorldY + TILE_SIZE) - (PLAYER_SCREEN_Y + TILE_SIZE) - TILE_SIZE
                        && worldY < (playerWorldY + TILE_SIZE) + (PLAYER_SCREEN_Y + TILE_SIZE) + TILE_SIZE)
                    g2.drawImage(tile[tileId].image, screenX, screenY, TILE_SIZE, TILE_SIZE, null);
            }
            worldCol++;

            if (worldCol == MAX_WORLD_COL) {
                worldCol = 0;
                worldRow++;
            }
        }

//        for (int i = 0; i < MAX_WORLD_ROW; i++)
//            for (int j = 0; j < MAX_WORLD_COL; j++)
//                temp[i][j] = 0;
    }
}
