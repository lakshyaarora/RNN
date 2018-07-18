



# Valueable code

from __future__ import absolute_import
from __future__ import division
from __future__ import print_function
import numpy as np
import matplotlib.pyplot as plt
from osgeo import gdal
#from keras.layers import Dense, Conv1D, Input, MaxPooling1D, Flatten
from keras import Sequential
from keras.utils import np_utils
from PIL import Image
import filereader
import tempreader
import sys
from tensorflow.examples.tutorials.mnist import input_data
from keras.models import Sequential
from keras.layers import LSTM,Dense
from keras.models import load_model
import numpy as np

row = 436
col = 506
bands = 4
c_c = 7


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
x_test = x_test.reshape(row*col, 1, 4)
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
#range=6

#for itr in range:
#value_array=np.zeros(7)
#value_array[itr]=1 
y_train = np.zeros([(dict1["Clay"] + dict1["Dalforest"] + dict1["Eufinal"] + dict1["Water"] + dict1["Wheat"] + dict1[
    "Grassland"]) // 8], dtype=np.uint8)
print(
    (dict1["Clay"] + dict1["Dalforest"] + dict1["Eufinal"] + dict1["Water"] + dict1["Wheat"] + dict1["Grassland"]) // 2)
for i in range(dict1["Clay"] // 8):
    y_train[i] =1 
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


seed = 7
np.random.seed(seed)

# normalize inputs from 0-255 to 0-1
x_train = x_train / 255
x_test = x_test / 255 
num_pixels = bands

# one hot encode outputs

y_train = np_utils.to_categorical(y_train)
y_test = np_utils.to_categorical(y_test)
num_classes = c_c
y_test_new = np.zeros([pixels, c_c], dtype=np.uint8)



print(x_test)
print(20*'#')
print(x_train)
print(20*'#')
print(y_test)
print(20*'#')
print(y_train)

print(x_test.shape)
print(x_train.shape)
print(y_train.shape) 
print(y_test.shape)

X = x_train.reshape(143, 1, 4)

'''
model = Sequential()
model.add(Conv1D(2 ** 3, 1, activation="relu", padding='same', input_shape=[1, 4]))
model.add(Conv1D(2 ** 2, 1, activation="relu", padding='same'))
model.add(Flatten())
model.add(Dense(num_classes, activation='sigmoid'))
model.compile(loss='categorical_crossentropy', optimizer='adam', metrics=['accuracy'])

model.fit(X, y_train, batch_size=10, epochs=2500, verbose=2)

y_test_new = model.predict(x_test, batch_size=10)
y_test1 = np.argmax(y_test_new, axis=1)
'''

time_steps=1
n_units=128
n_inputs=4
n_classes=7
batch_size=10
#n_epochs=5
#data_loaded=false
#trained=false


model=Sequential()
model.add(LSTM(n_units,return_sequences=True, input_shape=(time_steps, n_inputs)))
model.add(LSTM(n_units,return_sequences=True))
model.add(LSTM(n_units))
model.add(Dense(n_classes, activation='sigmoid'))
model.compile(loss='categorical_crossentropy', optimizer='adam', metrics=['accuracy'])
model.fit(X,y_train,batch_size=10,epochs=45,verbose=2)
y_test_new=model.predict(x_test,batch_size=10)
y_test1=np.argmax(y_test_new,axis=1)
print(y_test_new)

print(30*'*')
print("this is predicted output")
#print("Baseline Error: %.2f%%" % (100-scores[1]*100))

img = x_test.reshape(row, col, bands)

plt.imshow(img)
plt.show()
result = Image.fromarray((img * 255).astype('uint8'))
result.save('image.tiff')

"""
k = y_test_new.reshape(row, col, bands)
plt.imshow(k)
plt.show()
result = Image.fromarray((k * 255).astype('uint8'))
result.save('image.tiff')
"""

print(y_test)


img = y_test1.reshape(row, col)
plt.imshow(img)
plt.show()
result = Image.fromarray(((img * 255) / c_c).astype('uint8'))
result.save('Classified_image.tiff')
print("img created")
model.save('Classfication_model.hdf5')

