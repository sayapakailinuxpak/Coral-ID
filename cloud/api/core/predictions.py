from keras.preprocessing import image
import matplotlib.pyplot as plt
import numpy as np


def load_image_from_path(img_path, show=False):

    img = image.load_img(img_path, target_size=(416, 416))
    # (height, width, channels)
    img_tensor = image.img_to_array(img)
    # (1, height, width, channels), add a dimension because the model expects this shape: (batch_size, height, width, channels)
    img_tensor = np.expand_dims(img_tensor, axis=0)
    # imshow expects values in the range [0, 1]
    img_tensor /= 255.

    if show:
        plt.imshow(img_tensor[0])
        plt.axis('off')
        plt.show()

    return img_tensor
