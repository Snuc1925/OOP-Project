import os
from PIL import Image

# Hàm tính bộ nhớ của một ảnh
def calculate_image_memory(image_path):
    with Image.open(image_path) as img:
        width, height = img.size
        bit_depth = 32  # Giả sử ảnh có 32 bits (4 bytes) mỗi pixel (ARGB hoặc RGB)
        memory_usage = width * height * (bit_depth // 8)  # Bytes
        return memory_usage

# Hàm duyệt qua tất cả các thư mục và tệp trong thư mục res
def calculate_total_memory(res_dir):
    total_memory = 0
    for root, dirs, files in os.walk(res_dir):  # Duyệt đệ quy qua các thư mục con
        for file in files:
            # Kiểm tra tệp có phải là ảnh không
            if file.lower().endswith(('.png', '.jpg', '.jpeg', '.bmp', '.gif', '.tiff')):
                image_path = os.path.join(root, file)
                total_memory += calculate_image_memory(image_path)
    return total_memory

# Thư mục res
res_dir = 'res'

# Tính tổng bộ nhớ cho tất cả các ảnh trong thư mục res
total_memory = calculate_total_memory(res_dir)

# In kết quả
print(f'Total memory usage for images in RAM: {total_memory / (1024 * 1024):.2f} MB')
