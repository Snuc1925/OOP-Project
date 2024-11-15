from PIL import Image
import numpy as np

# Load the PNG image
image = Image.open("up.png")

# Resize to 32x32 if it's not already (optional)
image = image.resize((32, 32))

# Convert to RGB or RGBA (to make sure it has 3 or 4 channels)
image = image.convert("RGBA")

# Convert the image to a NumPy array
pixel_array = np.array(image)

# Print pixel matrix
# Each entry is in the form (R, G, B, A)
print(pixel_array)
