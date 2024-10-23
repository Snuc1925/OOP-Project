package utils;

import java.awt.*;
import static utils.Constants.Screen.*;
import java.io.IOException;
import java.io.InputStream;


public class HelpMethods {
    public static int getXForCenterText(String text, Graphics2D g2) {
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return SCREEN_WIDTH/2 - length/2;
    }
    public static int getYForCenterText(String text, Graphics2D g2) {
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getHeight();
        return SCREEN_HEIGHT/2 - length/2;
    }

    public static Font loadFontMaruMonica() {
        Font maruMonica = null;
        try {
            InputStream is = HelpMethods.class.getClassLoader().getResourceAsStream("font/x12y16pxMaruMonica.ttf");
            if (is == null) {
                System.err.println("Không tìm thấy tệp font. Kiểm tra lại đường dẫn và đảm bảo tệp tồn tại.");
                return null;
            }
            maruMonica = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (FontFormatException e) {
            System.err.println("Lỗi định dạng font: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Lỗi đọc file font: " + e.getMessage());
        }
        return maruMonica; // Trả về font hoặc null nếu có lỗi
    }
}
