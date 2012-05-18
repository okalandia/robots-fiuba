# -*- encoding: ISO-8859-1 -*-
import sys
import os.path
from bs4 import BeautifulSoup, Comment

if len(sys.argv) < 3:
    print "Debe indicar archivo de origen y de destino"
    exit()

if not os.path.isfile(sys.argv[1]):
    print "Archivo " + sys.argv[1] + " inexistente"
    exit()
    
textFile = open(sys.argv[2],"wb")
if not textFile:
    print "No se puede escribir en " + sys.argv[2]
    exit()  
    
htmlFile = open(sys.argv[1],"rU")    
soup = BeautifulSoup(htmlFile)
htmlFile.close()

#Remover scripts y estilos
for tagType in ('script','style','noscript','code'):
    for tag in soup(tagType):
        tag.extract()

#Remover comentarios    
comments = soup.findAll(text=lambda text:isinstance(text, Comment))
[comment.extract() for comment in comments]


#Imprimir texto    
for string in soup.body.stripped_strings:
    textFile.write(string.encode("utf8") + "\n")
    
textFile.close() 
htmlFile.close()
    

