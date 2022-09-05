## Word Puzzle Solver

Input: dictionary file, NxM dimensions of the grid

A grid of randomly generated characters is to be checked against a dictionary of words to see if the grid 
contains any real words. A Hash table was used to store the words - its near-constant
lookup times were used to efficiently lower overall runtimes. 

Next, at each spot in the grid, the algorithm traversed in each of the eight cardinal directions
to search for possible words. Special care was taken to avoid running off the edge of the grid. 

Output: Print each word and the total number of words found.
