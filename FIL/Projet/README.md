  Projet de FIL


Tokenizer :
  Scanner scanne une string, on tokenize la string, on écrit la string dans un fichier de sortie, boucle tant que le scanner peut scanner
  
  On cherche à manger les tabulations en plus des espaces

  On enregistre le code d'un mot si le caractère suivant est un séparateur.
  variable csuiv
  
  Java string ne possède pas '\0', on peut le concaténer à la string pour avoir un caractère séparateur terminal
