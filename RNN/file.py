import numpy as np
l=[]
f = open("subfebformosat2", "rb")
k = f.read()
ll = len(k)
print(ll)
img = np.zeros([ll])
i = 0
for w in k:
    img[i] = ord(w)
    i += 1

l = np.reshape(img, newshape=[int(ll/4), 4])
print(l)