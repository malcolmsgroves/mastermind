# Mastermind
A mastermind game in the terminal. A random code is generated. The user can opt to play or to allow the computer to play with an optimized strategy. Mastermind rules and strategies can be found [here](https://en.wikipedia.org/wiki/Mastermind_(board_game)).

## Running
Download the project to a local repository. In the terminal, ```cd``` into the respository and type ```ant build``` to compile the src files and build the jar file. Type ```ant run``` to run the application. You will be prompted to play mastermind. If you opt not to play, the computer will play.

## Optimized Mastermind Algorithm
This project implements an optimized minimax algorithm developed by Donald Knuth in 1977 for solving the mastermind code. The algorithm consists of 5 steps:

1. Create a set S of all possible code permutations. For six colors and four pin positions, there are 1296 possible codes.

2. Begin with the initial guess 0 0 1 1, which makes it possible to always solve the code in 5 moves or less.

3. Score the guess
  - One black pin for every pin with the correct color and position.
  - One white pin for every pin with only the correct color.
  
4. If the score is four black pins, the game is won.
  
5. Remove all codes from S that, if correct, would not give the guess the same score.

6. Select the next guess from S using the following minimax technique
  - For each code in S, count the number of codes in S that would be eliminated for each possible score.
  - Choose the code in S that might eliminate the largest number of codes in S.
  
7. Repeat from step 3 until the correct code is discovered.


**Sample Output**

```
WELCOME TO MASTERMIND!
Do you want to play? (y/n)
n
Computer is playing...
Secret Code: Y B Y R 

R R Y Y 
1 pins with correct position and color
2 pins with only the correct color

G Y Y R 
2 pins with correct position and color
1 pins with only the correct color

Y B Y R 
4 pins with correct position and color
0 pins with only the correct color

CONGRATULATIONS YOU WIN
```

## Improvements
* The mastermind algorithm implemented here is not an exact replicate of Knuth's mastermind algorithm. Guesses should be selected from all code permutations each round. The guesses should be selected based on the minimum 
* The code representation should be refactored so that all classes operate on Guess objects. As it stands now, only the MasterAlg class uses Guess objects to store codes.
