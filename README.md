# Assembler
Converts assemble code to machine code 

How it works:
  - The program will first read in a text file containing the asseblem code ("test_file.txt").
  - Then read though it one line at a time and decide whether each instruction is either a I, R, or J type (instructions names and op code are save in an ArrayList).
  - Then the code parses through each instruction and covert the values to Hex, then prints it to an output file ("file_output.txt").
