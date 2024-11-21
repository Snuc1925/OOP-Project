import os
from PIL import Image

def create_spritesheet(source_folder, output_folder):
    directions = ["left", "right", "up", "down", "left_up", "left_down", "right_up", "right_down"]

    # Tạo thư mục output nếu chưa tồn tại
    os.makedirs(output_folder, exist_ok=True)

    for direction in directions:
        frames = []

        # Duyệt qua các subfolder (1->5)
        for animation_frame in range(1, 4):  # AnimationFrame từ 1 -> 5
            frame_path = os.path.join(source_folder, str(animation_frame), f"{direction}.png")
            if os.path.exists(frame_path):
                frames.append(Image.open(frame_path))
            else:
                print(f"Không tìm thấy frame: {frame_path}")

        if frames:
            # Kích thước spritesheet: (width * số frame, height)
            width, height = frames[0].size
            spritesheet_width = width * len(frames)
            spritesheet_height = height

            # Tạo canvas cho spritesheet
            spritesheet = Image.new("RGBA", (spritesheet_width, spritesheet_height))

            # Vẽ từng frame vào spritesheet
            for index, frame in enumerate(frames):
                spritesheet.paste(frame, (index * width, 0))

            # Lưu spritesheet
            output_path = os.path.join(output_folder, f"{direction}.png")
            spritesheet.save(output_path)
            print(f"Đã tạo spritesheet: {output_path}")

# Sử dụng hàm
source_folder = "explosion"  # Đường dẫn tới thư mục chứa các AnimationFrame (1->5)
output_folder = "explosion1"  # Đường dẫn lưu spritesheet
create_spritesheet(source_folder, output_folder)
