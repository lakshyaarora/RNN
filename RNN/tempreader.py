def main():
    tokens=[]
    with open ('temp.txt', 'rt') as in_file: # Open file txt for reading of text data.
        for line in in_file:
            tokens=line.split("//null//")
    in_file.close()
    st=tokens[0].split(":")
    train_cycle=int(st[1])
    st=tokens[1].split(":")
    cls=st[1]
    c_c=int(cls)+1
    st=tokens[2].split(":")
    neurons=int(st[1])
    return train_cycle,c_c,neurons