import os
from PIL import Image

def check_bit_depth(file_path):
    """Kiểm tra độ sâu màu của file PNG."""
    try:
        with Image.open(file_path) as img:
            if img.mode == "RGB":  # 32 bit
                return True
    except Exception as e:
        print(f"Không thể mở file {file_path}: {e}")
    return False

def find_png_with_bit_depth(folder):
    """Duyệt qua thư mục và in các file PNG có bit depth = 32."""
    for root, dirs, files in os.walk(folder):
        for file in files:
            if file.lower().endswith(".png"):
                file_path = os.path.join(root, file)
                if check_bit_depth(file_path):
                    print(f"File PNG với bit depth 32: {file_path}")

# Thay 'your_folder_path' bằng đường dẫn tới thư mục cần duyệt.
find_png_with_bit_depth("res")
