from __future__ import absolute_import
from __future__ import division
from __future__ import print_function
import numpy as np
import matplotlib.pyplot as plt
from osgeo import gdal
from keras.layers import Dense
from keras import Sequential
from keras.utils import np_utils
from PIL import Image
import filereader
import tempreader

row = 436
col = 506
bands = 4
c_c = 6


def ReadBilFile(bil,bands,pixels):
    extract_band = 1
    image = np.zeros([pixels, bands], dtype=np.uint8)
    gdal.GetDriverByName('EHdr').Register()
    img = gdal.Open(bil)
    while bands >= extract_band:
        bandx = img.GetRasterBand(extract_band)
        datax = bandx.ReadAsArray()
        temp = datax
        store = temp.reshape(pixels)
        for i in range(pixels):
            image[i][extract_band - 1] = store[i]
        extract_band = extract_band + 1
    return image


pixels = row * col
y_test = np.zeros([row * col], dtype=np.uint8)
x_test = ReadBilFile("subfebformosat2", 4, pixels)
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
x_train = f_in.reshape(rex // 2, bands)

# loading of data ends here

print(x_test.shape)
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


model = Sequential()
model.add(Dense(num_pixels, input_dim=num_pixels, kernel_initializer='normal', activation='relu'))
model.add(Dense(num_classes, kernel_initializer='normal', activation='softmax'))
model.compile(loss='categorical_crossentropy', optimizer='adam', metrics=['accuracy'])

model.fit(x_train, y_train, batch_size=32, epochs=100, verbose=2)
y_test_new = model.predict(x_test, batch_size=32)
y_test = np.argmax(y_test_new, axis=1)

print("this is predicted output")
# print("Baseline Error: %.2f%%" % (100-scores[1]*100))

img = x_test.reshape(row, col, bands)

plt.imshow(img)
plt.show()
result = Image.fromarray((img * 255).astype('uint8'))
result.save('image.tiff')

img = y_test.reshape(row, col)
plt.imshow(img)
plt.show()
result = Image.fromarray(((img * 255) / c_c).astype('uint8'))
result.save('Classified_image.tiff')
print("img created")
model.save('Classfication_model.hdf5')
