import re  

def main(headpath):
    i=1
    r=-1
    c=-1
    b=-1
    pat1 = re.compile(r"\brows\b", re.IGNORECASE) # but matches because pattern ignores case
    pat2 = re.compile(r"\bcols\b", re.IGNORECASE) # but matches because pattern ignores case
    pat3 = re.compile(r"\bbands\b", re.IGNORECASE) # but matches because pattern ignores case
    lines = [] #Declare an empty list named "lines"
    with open (headpath, 'rt') as in_file: # Open file txt for reading of text data.
        for line in in_file:
            if pat1.search(line) != None:
                lines.append(line) 
                i=i+1
                r=line.split(" ",1)[1] 
                print("this is the number of rows extracted:")
                print(r)
                
            elif pat2.search(line) != None:
                lines.append(line) 
                i=i+1
                c=line.split(" ",1)[1] 
                print("this is the number of columns extracted:")
                print(c)
                
            elif pat3.search(line) != None:
                lines.append(line) 
                i=i+1
                b=line.split(" ",1)[1] 
                print("this is the number of bands extracted:")
                print(b)
            else:
                i=i+1

    if( r==-1 or b==-1 or c==-1):
        print("ERROR: header information insuffficient")

    in_file.close()
    rr=int(r)
    cc=int(c)
    bb=int(b)
    return rr,cc,bb