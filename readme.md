# Sudoku

This program can solve and generate standard 9 by 9 Sudoku's with a simple GUI.

![Sudoku grid screenshot](https://i.imgur.com/N8y2YD5.png)

## Solving

This uses a standard randomised backtracking algorithm to solve the Sudoku, and returns that the given Sudoku is unsolvable if no solution is found after searching all possible arrangements.

## Generation

To generate a new Sudoku grid, this program solves the empty grid: because the solving algorithm is randomised, this generates a randomised solution. Then, the algorithm randomly empties tiles to generate an incomplete grid. 

You can change the difficulty of the generated Sudoku's by changing the value of ```NUM_GENERATE``` in ```Gameboard.java```. This is the number of tiles which are generated.
