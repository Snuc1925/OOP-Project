from PIL import Image

# Mở ảnh gốc (đạn kích thước 46x9)
original_image = Image.open("projectile.png")

# Mở rộng chiều cao ảnh thành 46x46 bằng cách thêm khoảng trống ở bên trên và bên dưới
new_height = 46
expanded_image = Image.new("RGBA", (46, new_height), (0, 0, 0, 0))  # Màu nền trong suốt
expanded_image.paste(original_image, (0, (new_height - original_image.height) // 2))

# Lưu ảnh mở rộng ban đầu với hướng trái (left)
expanded_image.save("left.png")

# Tạo các ảnh xoay theo các hướng khác nhau
# Right (xoay 180 độ từ left)
right_image = expanded_image.rotate(180)
right_image.save("right.png")

# down (xoay 90 độ từ left)
down_image = expanded_image.rotate(90)
down_image.save("down.png")

# up (xoay 270 độ từ left)
up_image = expanded_image.rotate(270)
up_image.save("up.png")

# Diagonal directions
# Left_down (giữ nguyên ảnh mở rộng)
left_down_image = expanded_image.rotate(45)
left_down_image.save("left_down.png")

# Left_up
left_up_image = expanded_image.rotate(-45)
left_up_image.save("left_up.png")

# Right_down
right_down_image = expanded_image.rotate(135)
right_down_image.save("right_down.png")

# Right_up
right_up_image = expanded_image.rotate(225)
right_up_image.save("right_up.png")

# print("Đã tạo các ảnh với hướng khác nhau thành công!")
