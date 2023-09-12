
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;


/**
 * A data type for keeping track of the different states of a letter in the waffle. CORRECT means
 * the letter in the position in the puzzle is the same letter in the position in the solution.
 * WRONG_POSITION means the letter in the position in the puzzle is in the solution but in a
 * different position. NOT_IN_WORD means the letter in the position in the puzzle is not in the word
 * in the solution. BLANK means a space character in the puzzle and solution which are at 4
 * positions in the waffle grid.
 */
enum Hint {CORRECT, WRONG_POSITION, NOT_IN_WORD, BLANK}

/**
 * Contains all the code based on the word game waffle, where you have to rearrange letters into the
 * correct words, horizontally and vertically. Capital letter means it's in the right spot, if it's
 * not in the word at all, lowercase will be printed out. () means the letter is in the wrong spot.
 *
 * @author Jack Patterson
 */
public class WaffleGame {
    /**
     * This method opens a file and looks through it until it finds a number inputted by the user
     * correlating to which puzzle they want to play. It then reads the scrambled puzzle into array
     * puzzle and the completed puzzle into array puzzleSolution. Also checks test cases to see if
     * input and puzzles in the file are valid.
     * <p>
     *
     * @param fileName       The string name of the file to be opened.
     * @param puzzleNumber   The int number correlating to different puzzles
     * @param puzzle         a 2d array to store the scrambled puzzle
     * @param puzzleSolution a 2d array to store the completed puzzle.
     * @return If the method is successful, it will return value null. If there is an invalid
     * parameter it will return "Invalid parameter in loadPuzzle". If there is a
     * FileNotFoundException it will return "Unable to open file: " + fileName. If the puzzle and
     * solution don't have 10 lines with exactly 5 characters each it will return "Invalid puzzle: "
     * + puzzleNumber
     */
    public static String loadPuzzle(String fileName, int puzzleNumber, char[][] puzzle,
                                    char[][] puzzleSolution) {
        //Initializes variables
        String line;
        boolean found = false;

        //Checks if any parameters are null or the puzzle number is below zero
        if (fileName == null || puzzleNumber <= 0 || puzzle == null || puzzleSolution == null) {
            return "Invalid parameter in loadPuzzle";
        }

        //Initializes scanner and file and catches error message if file does not exist
        Scanner input;
        try {
            File file = new File(fileName);
            input = new Scanner(file);
        } catch (FileNotFoundException e) {
            return "Unable to open file: " + fileName;
        }

        //Reads through the file and reaches puzzle number.
        while (input.hasNextLine()) {
            if (input.hasNextInt() && input.nextInt() == puzzleNumber) {
                input.nextLine();
                found = true;

                //Fills array puzzle with the puzzle in the file and checks if it is a valid puzzle
                for (int row = 0; row < puzzle.length; row++) {
                    line = input.nextLine();
                    if (line.length() != 5) {
                        return "Invalid puzzle: " + puzzleNumber;
                    }
                    for (int col = 0; col < puzzle[row].length; col++) {
                        puzzle[row][col] = line.charAt(col);
                    }

                }

                //Fills array puzzleSolution with the puzzle in the file and checks if it is a
                // valid solution
                for (int row = 0; row < puzzleSolution.length; row++) {
                    line = input.nextLine();
                    if (line.length() != 5) {
                        return "Invalid puzzle: " + puzzleNumber;
                    }
                    for (int col = 0; col < puzzleSolution[row].length; col++) {
                        puzzleSolution[row][col] = line.charAt(col);


                    }
                }
            } else {
                input.nextLine();
            }

        }

        //Checks if the user inputted a valid puzzleNumber.
        if (!found) {
            return "Puzzle not found: " + puzzleNumber;
        }

        return null;
    }

    /**
     * Prints out the column and row numbers, 0 to 4, and the puzzle using the hints. Prints after
     * each switch made by user
     * <p>
     *
     * @param puzzle a 2D array containing the waffle puzzle.
     * @param hints  a 2D array containing whether a letter in the puzzle is in the right position
     *               or word
     */
    public static void printPuzzle(char[][] puzzle, Hint[][] hints) {
        System.out.println("    0  1  2  3  4 ");

        //Outputs the column and row numbers, 0 to 4 and the puzzle if hints = null.
        for (int row = 0; row < puzzle.length; row++) {
            System.out.print(" " + row + " ");
            for (int col = 0; col < puzzle[row].length; col++) {
                if (hints == null) {
                    System.out.print(" " + puzzle[row][col] + " ");
                    continue;
                }


                //Checks whether the letters are correct, blank, not in the word, or in the wrong
                // position and outputs them with specific UI respectively.
                switch (hints[row][col]) {
                    case CORRECT:
                        System.out.print(" " + puzzle[row][col] + " ");
                        break;

                    case BLANK:
                        System.out.print("   ");
                        break;

                    case NOT_IN_WORD:
                        System.out.print(" " + Character.toLowerCase(puzzle[row][col]) + " ");
                        break;

                    case WRONG_POSITION:
                        System.out.print("(" + Character.toLowerCase(puzzle[row][col]) + ")");
                        break;
                }
            }
            System.out.println();


        }
        System.out.println();


    }


    /**
     * Compares the puzzle the user is playing to the solution, and returns true if they are the
     * same, false if different.
     * <p>
     *
     * @param puzzle         a 2D array containing the scrambled puzzle, updated each time the user
     *                       switches values.
     * @param puzzleSolution a 2D array containing the finished solution to the puzzle
     * @return returns true if the arrays are the same, false if different.
     */
    public static boolean waffleCompleted(char[][] puzzle, char[][] puzzleSolution) {

        //Checks if array puzzle and array puzzleSolution are the same.
        for (int row = 0; row < puzzle.length; row++) {
            for (int col = 0; col < puzzle[row].length; col++) {
                if (puzzle[row][col] != puzzleSolution[row][col]) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * This looks through the puzzle and solution and initializes the hints. The hints indicate
     * whether each letter is in the correct location, wrong position in word or not in the word.
     * The blanks are also identified in hints.
     *
     * @param puzzle   The current state of the puzzle.
     * @param solution The solution to the puzzle.
     * @param hints    The hints are set based on the current state of the puzzle.
     */
    public static void identifyHints(char[][] puzzle, char[][] solution, Hint[][] hints) {

        //initialize hints from solution for blanks, and letters in the correct location. Also,
        //assume every other letter is not in the word, which will be modified for those
        //letters not in position below.
        for (int row = 0; row < puzzle.length; row++) {
            for (int col = 0; col < puzzle[row].length; col++) {
                if (solution[row][col] == ' ') {
                    hints[row][col] = Hint.BLANK;
                } else if (puzzle[row][col] == solution[row][col]) {
                    hints[row][col] = Hint.CORRECT;
                } else {
                    hints[row][col] = Hint.NOT_IN_WORD;
                }
            }
        }

        //for horizontal words that don't have correct letters, see if the letters should be
        // marked as wrong position rather than not in word.
        for (int row = 0; row < puzzle.length; row += 2) {

            ArrayList<Character> unmatchedLettersInSolution = new ArrayList<>();
            for (int col = 0; col < solution[row].length; col++) {
                if (hints[row][col] != Hint.CORRECT) {
                    unmatchedLettersInSolution.add(solution[row][col]);
                }
            }
            for (int col = 0; col < puzzle[row].length; col++) {
                if (hints[row][col] != Hint.CORRECT) {
                    char unmatchedLetter = puzzle[row][col];
                    int indexOfLetter = unmatchedLettersInSolution.indexOf(unmatchedLetter);
                    if (indexOfLetter >= 0) {
                        hints[row][col] = Hint.WRONG_POSITION;
                        unmatchedLettersInSolution.remove(indexOfLetter);
                    }
                }
            }
        }

        //for vertical words that don't have correct letters, see if the letters should be
        // marked as wrong position rather than not in word.
        for (int col = 0; col < puzzle[0].length; col += 2) {

            ArrayList<Character> unmatchedLettersInSolution = new ArrayList<>();
            for (int row = 0; row < solution.length; row++) {
                if (hints[row][col] != Hint.CORRECT) {
                    unmatchedLettersInSolution.add(solution[row][col]);
                }
            }
            for (int row = 0; row < puzzle.length; row++) {
                if (hints[row][col] != Hint.CORRECT) {
                    char unmatchedLetter = puzzle[row][col];
                    int indexOfLetter = unmatchedLettersInSolution.indexOf(unmatchedLetter);
                    if (indexOfLetter >= 0) {
                        hints[row][col] = Hint.WRONG_POSITION;
                        unmatchedLettersInSolution.remove(indexOfLetter);
                    }
                }
            }
        }
    }

    /**
     * Swaps the letters in the specified positions in the puzzle.
     *
     * @param puzzle The current state of the puzzle that will be updated with the swap.
     * @param row1   The row of the first letter to be swapped, starts with index 0.
     * @param col1   The column of the first letter to be swapped.
     * @param row2   The row of the second letter to be swapped.
     * @param col2   The column of the second letter to be swapped.
     * @return true when the swap is successful, false otherwise.
     */
    public static boolean swap(char[][] puzzle, int row1, int col1, int row2, int col2) {
        try {
            //swap the letters
            char temp = puzzle[row1][col1];
            puzzle[row1][col1] = puzzle[row2][col2];
            puzzle[row2][col2] = temp;
            return true;
        } catch (Exception e) {  //catches all the various exceptions
            return false;
        }
    }

    /**
     * This method contains the text interface for the waffle game.
     *
     * @param args unused
     */
    public static void main(String[] args) {
        String puzzleFilename = "waffles.txt";
        if (args.length > 0) {
            puzzleFilename = args[0];
        }

        Scanner input = new Scanner(System.in);
        final int WAFFLE_SIZE = 5;
        char[][] solution = new char[WAFFLE_SIZE][WAFFLE_SIZE];
        char[][] puzzle = new char[WAFFLE_SIZE][WAFFLE_SIZE];
        Hint[][] hints = new Hint[WAFFLE_SIZE][WAFFLE_SIZE];
        int puzzleNumber;

        System.out.println("Welcome to Waffle!");
        System.out.print("Pick a puzzle number from 1 to 10:");
        if (input.hasNextInt()) {
            puzzleNumber = input.nextInt();
        } else {
            System.out.println("Invalid puzzle number: " + input.nextLine());
            return;
        }
        String loadResult = loadPuzzle(puzzleFilename, puzzleNumber, puzzle, solution);
        if (loadResult != null) {
            System.out.println(loadResult);
            return;
        }
        identifyHints(puzzle, solution, hints);

        int swapsRemaining = 15;
        boolean waffleCompleted = false;
        do {
            printPuzzle(puzzle, hints);
            System.out.println(swapsRemaining + " swaps remaining");
            System.out.print("Enter the row and column for each letter to swap.\n" + "1 2 2 3 " +
                    "means swap row 1 column 2 with row 2 column 3:");
            boolean haveGoodInput = false;
            try {
                int row1 = input.nextInt();
                int col1 = input.nextInt();
                int row2 = input.nextInt();
                int col2 = input.nextInt();
                if (swap(puzzle, row1, col1, row2, col2)) {
                    haveGoodInput = true;
                    identifyHints(puzzle, solution, hints);
                    swapsRemaining--;
                    waffleCompleted = waffleCompleted(puzzle, solution);
                } else {
                    input.nextLine(); //clear the rest of the line.
                    System.out.println("Invalid input, please try again.");
                }
            } catch (InputMismatchException e) {
                input.nextLine(); //clear the rest of the line.
                System.out.println("Invalid input, please try again.");
            } catch (NoSuchElementException e) {
                input.nextLine(); //clear the rest of the line.
                System.out.println("Invalid input, please try again.");
            }
        } while (swapsRemaining > 0 && !waffleCompleted);

        if (waffleCompleted) {
            System.out.println("Congratulations! You solved the waffle with " + swapsRemaining +
                    " swaps remaining.");
            printPuzzle(solution, null);
        } else {
            System.out.println("Solution:");
            printPuzzle(solution, null);
        }
    }
}
