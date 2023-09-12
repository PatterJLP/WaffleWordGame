
public class TestWaffleGame {
    public static void main(String[]args) {
        testIdentifyHints();
    }

    public static void testIdentifyHints() {
        boolean error = false;

        {
            char[][]puzzle = {
                    {'H','S','A','S','E'},
                    {'R',' ','N',' ','L'},
                    {'D','N','L','G','O'},
                    {'P',' ','I',' ','N'},
                    {'E','C','Y','D','E'}
            };
            char[][]solution = {      //these may not be actual words just checking expected hints
                    {'H','A','S','T','E'},
                    {'R',' ','U',' ','L'},
                    {'D','I','N','G','O'},
                    {'P',' ','K',' ','N'},
                    {'E','C','Y','D','E'}
            };
            Hint[][] expectedHints = {
                    {Hint.CORRECT, Hint.WRONG_POSITION, Hint.WRONG_POSITION, Hint.NOT_IN_WORD, Hint.CORRECT},
                    {Hint.CORRECT, Hint.BLANK, Hint.WRONG_POSITION, Hint.BLANK,
                            Hint.CORRECT},
                    {Hint.CORRECT, Hint.WRONG_POSITION, Hint.NOT_IN_WORD, Hint.CORRECT,
                            Hint.CORRECT},
                    {Hint.CORRECT, Hint.BLANK, Hint.NOT_IN_WORD, Hint.BLANK,
                            Hint.CORRECT},
                    {Hint.CORRECT, Hint.CORRECT, Hint.CORRECT, Hint.CORRECT, Hint.CORRECT}
            };
            Hint[][]actualHints = new Hint[5][5];

            WaffleGame.identifyHints(puzzle, solution, actualHints);

            for ( int row = 0; row < expectedHints.length; row++) {
                for ( int col = 0; col < expectedHints[row].length; col++) {
                    if ( expectedHints[row][col] != actualHints[row][col] ){
                        error = true;
                        System.out.println("testIdentifyHints " + row + "," + col + " exp:"
                                + expectedHints[row][col] + " act:" + actualHints[row][col]);
                        WaffleGame.printPuzzle(puzzle, actualHints);
                    }
                }
            }
        }

        {
            char[][]puzzle = {
                    {'G','A','C','F','T'},
                    {'P',' ','O',' ','E'},
                    {'S','U','T','H','R'},
                    {'R',' ','P',' ','O'},
                    {'E','C','O','D','H'}
            };
            char[][]solution = {
                    {'G','H','O','S','T'},
                    {'R',' ','U',' ','O'},
                    {'A','F','T','E','R'},
                    {'P',' ','D',' ','C'},
                    {'E','P','O','C','H'}
            };
            Hint[][] expectedHints = {
                {Hint.CORRECT, Hint.NOT_IN_WORD, Hint.NOT_IN_WORD, Hint.NOT_IN_WORD, Hint.CORRECT},
                {Hint.WRONG_POSITION, Hint.BLANK, Hint.WRONG_POSITION, Hint.BLANK,
                        Hint.NOT_IN_WORD},
                    {Hint.NOT_IN_WORD, Hint.NOT_IN_WORD, Hint.CORRECT, Hint.NOT_IN_WORD,
                            Hint.CORRECT},
                    {Hint.WRONG_POSITION, Hint.BLANK, Hint.NOT_IN_WORD, Hint.BLANK,
                            Hint.WRONG_POSITION},
                    {Hint.CORRECT, Hint.WRONG_POSITION, Hint.CORRECT, Hint.NOT_IN_WORD, Hint.CORRECT}
            };
            Hint[][]actualHints = new Hint[5][5];

            WaffleGame.identifyHints(puzzle, solution, actualHints);

            for ( int row = 0; row < expectedHints.length; row++) {
                for ( int col = 0; col < expectedHints[row].length; col++) {
                    if ( expectedHints[row][col] != actualHints[row][col] ){
                        error = true;
                        System.out.println("testIdentifyHints " + row + "," + col + " exp:"
                                + expectedHints[row][col] + " act:" + actualHints[row][col]);
                        WaffleGame.printPuzzle(puzzle, actualHints);
                    }
                }
            }
        }


        if (error) {
            System.out.println("testIdentifyHints errors");
        } else {
            System.out.println("testIdentifyHints passed");
        }
    }
}
