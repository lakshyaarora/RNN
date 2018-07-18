import numpy as np
from keras.utils import np_utils


row = 436
col = 506
bands = 4
c_c = 6

# to load label and training data here

pixels = row * col
y_test = np.zeros([row * col], dtype=np.uint8)
values = []
path = ["Clay", "Dal" "forest", "Eufinal", "Water", "Wheat", "Grassland"]
dict1 = {"Clay": 0, "Dalforest": 0, "Eufinal": 0, "Water": 0, "Wheat": 0, "Grassland": 0}
for address in path:
    with open(address, "rb") as f:
        block = f.read()
        for ch in block:
            values.append(ch)
            dict1[address] += 1

ll = (len(values))
rex = ll // bands
print(ll, rex)
'''from here'''
f_in = np.zeros([ll // 2], dtype=np.uint8)
x = 0
for i in range(ll):
    if i % 2 == 1:
        f_in[x] = values[i]
        x += 1

sh = int(rex // bands)
y_train = np.zeros([(dict1["Clay"] + dict1["Dalforest"] + dict1["Eufinal"] + dict1["Water"] + dict1["Wheat"] + dict1[
    "Grassland"]) // 8], dtype=np.uint8)
print(
    (dict1["Clay"] + dict1["Dalforest"] + dict1["Eufinal"] + dict1["Water"] + dict1["Wheat"] + dict1["Grassland"]) // 2)
for i in range(dict1["Clay"] // 8):
    y_train[i] = 1
for i in range(dict1["Dalforest"] // 8):
    y_train[dict1["Clay"] // 8 + i] = 2
for i in range(dict1["Eufinal"] // 8):
    y_train[(dict1["Clay"] + dict1["Dalforest"]) // 8 + i] = 3
for i in range(dict1["Water"] // 8):
    y_train[(dict1["Clay"] + dict1["Dalforest"] + dict1["Eufinal"]) // 8 + i] = 4
for i in range(dict1["Wheat"] // 8):
    y_train[(dict1["Clay"] + dict1["Dalforest"] + dict1["Eufinal"] + dict1["Water"]) // 8 + i] = 5
for i in range(dict1["Grassland"] // 8):
    y_train[(dict1["Clay"] + dict1["Dalforest"] + dict1["Eufinal"] + dict1["Water"] + dict1["Wheat"]) // 8 + i] = 6
'''
till here
'''
print(y_train)
print(20 * '%')
x_train = f_in.reshape(rex // 2, bands)

# loading of data ends here

print(x_train.shape)
print(y_train.shape)
print(y_test.shape)

seed = 7
np.random.seed(seed)

# normalize inputs from 0-255 to 0-1
x_train = x_train / 255

num_pixels = bands

# one hot encode outputs

y_train = np_utils.to_categorical(y_train)
y_test = np_utils.to_categorical(y_test)
num_classes = c_c
y_test_new = np.zeros([pixels, c_c], dtype=np.uint8)

print(x_train.reshape(143, 1, 4))
print(20 * '#')
print(y_train)
print("this is predicted output")

k = x_train.reshape(143, 1, 4)
print(k.shape)

"""
img = x_test.reshape(row, col, bands)

plt.imshow(img)
plt.show()
result = Image.fromarray((img * 255).astype('uint8'))
result.save('image.tiff')

img = y_test.reshape(row, col)
plt.imshow(img)
plt.show()
result = Image.fromarray(((img * 255) / c_c).astype('uint8'))
result.save('cnn_' + datapath + '.tiff')
print("img created")
model.save(datapath + '.hdf5')
"""

