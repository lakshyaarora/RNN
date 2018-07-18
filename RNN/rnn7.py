from __future__ import absolute_import
from __future__ import division
from __future__ import print_function
import numpy as np
import matplotlib.pyplot as plt
from osgeo import gdal
from keras.layers import Dense, Conv1D, Input, MaxPooling1D, Flatten, Dropout
from keras import Sequential
from keras.layers import LSTM,Dense
from keras.models import load_model
from keras.utils import np_utils
from PIL import Image
import array
import sys
import os
'''
gdal.GetDriverByName('EHdr').Register()
img = gdal.Open("apex12bnads")
b = img.RasterCount
col = img.RasterXSize
row = img.RasterYSize
bands = img.RasterCount
datatype = gdal.GetDataTypeName(bands)
#c_c = int(input("Enter Number of Classes"))
c_c = 8
print(datatype)
'''
bands=17
c_c=8
row=1500
col=1000
datatype=np.uint16
def train():
    def ReadBilFile(bil,bands,pixels):
        extract_band = 1
        image = np.zeros([pixels, bands], dtype=np.uint16)
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

    print(os.listdir("C:\\users\\lakshya\\desktop\\iirs_intern\\12_Band"))
    pixels = row * col
    y_test = np.zeros([row * col], dtype=np.uint16)
    x_test = ReadBilFile("C://users/lakshya/desktop/iirs_intern/data/apex12bands", 12, pixels)
    x_test = x_test.reshape(row*col, 12, 1)
    values = []
    path = ["bright_forest_12", "buildings_1", "buildings_2", "dull_forest_12", "grass_12", "river_12", "road_12"]
    dict1 = {"bright_forest_12":0, "buildings_1":0, "buildings_2":0, "dull_forest_12":0, "grass_12":0, "river_12":0, "road_12":0}
    c_l = {"bright_forest_12":1, "buildings_1":2, "buildings_2":3, "dull_forest_12":4, "grass_12":5, "river_12":6, "road_12":7}
    clicks={}
    for address in path:
        with open("17_Band_region/"+address, "rb") as f:
            k = len(f.read())
            clicks[address] = k // 2 // bands
            print('{} ==> {}'.format(address, clicks[address]))
             
    for address in path:
        with open("12_Band/"+address, "rb") as f:
            b = array.array("H")
            b.fromfile(f, clicks[address]*bands)
            if sys.byteorder == "little":
                b.byteswap()
            for v in b:
                values.append(v)

    ll = (len(values))
    rex = ll // bands
    print(ll, rex)
    '''from here'''
    f_in = np.zeros([ll], dtype=np.uint16)
    x = 0
    for i in range(ll):
        f_in[x] = values[i]
        x += 1

    sh = int(rex // bands)
    y_train = np.zeros([rex], dtype=np.uint16)
    
    """    print(
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
        y_train[(dict1["Clay"] + dict1["Dalforest"] + dict1["Eufinal"] + dict1["Water"] + dict1["Wheat"]) // 8 + i] = 6"""
   
    '''
    till here
    '''
    mark = 0
    for add in path:
        for i in range(clicks[add]):
            y_train[mark+i] = c_l[add]
        mark = mark + clicks[add]

        
    x_train = f_in.reshape(rex, bands)

    seed = 7
    np.random.seed(seed)

    x_train = x_train / 2**16-1
    x_test = x_test / 2**16-1
    num_pixels = bands

    for v in y_train:
        print(v, end=" ")

    y_train = np_utils.to_categorical(y_train)
    y_test = np_utils.to_categorical(y_test)
    num_classes = c_c
    y_test_new = np.zeros([pixels, c_c], dtype=np.uint16)

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

    X = x_train.reshape(x_train.shape[0], 12, 1)
    '''
    model = Sequential()
    #model.add(Dense(num_pixels, input_dim=num_pixels, kernel_initializer='normal', activation='relu'))
    model.add(Conv1D(2 ** 2, 2, activation="relu", padding='same', input_shape=[12, 1]))
    model.add(Conv1D(2 ** 3, 2, activation="relu", padding='same'))
    model.add(MaxPooling1D(2))
    model.add(Conv1D(2 ** 4, 2, activation="relu", padding='same'))
    model.add(Conv1D(2 ** 5, 2, activation="relu", padding='same'))
    model.add(MaxPooling1D(2))
    model.add(Conv1D(2 ** 6, 2, activation="relu", padding='same'))
    model.add(Conv1D(2 ** 7, 2, activation="relu", padding='same'))
    model.add(MaxPooling1D(2))
    model.add(Flatten())
    #model.add(Dropout(0.1))
    model.add(Dense(num_classes, activation='sigmoid'))
    '''
    #time_steps=1
    n_units=128
    #n_inputs=4
    n_classes=8
    batch_size=10
    #n_epochs=5
    #data_loaded=false
    #trained=false

    model=Sequential()
    #model.add(Conv1D(2 ** 3, 2, activation="relu", padding='same', input_shape=[12, 1]))
    #model.add(MaxPooling1D(2))

    #model.add(LSTM(2**6,return_sequences=True))
    #model.add(MaxPooling1D(2))
    model.add(LSTM(n_units//2,return_sequences=True,input_shape=[17,1]))
    #model.add(Conv1D(2 ** 5, 2, activation="relu", padding='same'))
    model.add(MaxPooling1D(2))
    model.add(LSTM(2**6,return_sequences=True))
    model.add(MaxPooling1D(2))
    #model.add(MaxPooling1D(2))
    #model.add(LSTM(n_units, return_sequences=True))
    model.add(LSTM(n_units))
    model.add(Dense(n_classes, activation='sigmoid'))
    model.compile(loss='categorical_crossentropy', optimizer='adam', metrics=['accuracy'])
    model.summary()
    model.fit(X, y_train, batch_size=10, epochs=2000,verbose=2)
    #model.fit(x_train, y_train, batch_size=10, epochs=1000, verbose=2)
    y_test_new = model.predict(x_test, batch_size=10)
    print(20*'%')
    #print(y_test_new[:,1])
    print(y_test_new.shape)
    #print(np.squeeze(y_test_new))
    print(20*'%')
    y_test1 = np.argmax(y_test_new, axis=1)
    print(30*'*')
    print("this is predicted output")
    #img = x_test.reshape(row, col, bands)
    #plt.imshow(img)
    #plt.show()
    #result = Image.fromarray((img * 255).astype('uint8'))
    #result.save('image.tiff')

    """
    k = y_test_new.reshape(row, col, bands)
    plt.imshow(k)
    plt.show()
    result = Image.fromarray((k * 255).astype('uint8'))
    result.save('image.tiff')
    """
    mul=2**16-1
    for i in range(1, 8):
        img = y_test_new[:,i].reshape(row, col)
        plt.imshow(img*mul)
        plt.colorbar()
        plt.show()
        
        result = Image.fromarray(((img * mul)).astype('uint16'))
        result.save('Classified_images_3/5_class_'+str(i)+'.tiff')


train()
