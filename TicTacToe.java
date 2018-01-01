import java.util.Scanner;

public class TicTacToe{
    final private static String TOP_LEFT = "top-left";
    final private static String TOP_CENTER = "top-center";
    final private static String TOP_RIGHT = "top-right";
    final private static String MIDDLE_LEFT = "middle-left";
    final private static String MIDDLE_CENTER = "middle-center";
    final private static String MIDDLE_RIGHT = "middle-right";
    final private static String BOTTOM_LEFT = "bottom-left";
    final private static String BOTTOM_CENTER = "bottom-center";
    final private static String BOTTOM_RIGHT = "bottom-right";
    final private static String PLAYER_ONE = "PLAYER ONE";
    final private static String PLAYER_TWO = "PLAYER TWO";

    public static void main(String args[]){
        //Intialize the board
        char[][] board = new char[3][3];

        //Intialize the current player
        String currentPlayer = PLAYER_ONE;

        //Initialize the scanner for user input
        Scanner scanner = new Scanner(System.in);


        System.out.println("");
        System.out.println("Welcome to Tic-Tac-Toe!");

        //Boolean indicating if there was a win in the game
        boolean theresAWin = false;

        while(!theresAWin){
            //Indicating to players whose turn it is
            System.out.println(currentPlayer + ", it's your turn.");
            System.out.println("");

            //Show the user the possible moves
            System.out.println("Possible moves to make:");
            printPossibleMoves(board);
            System.out.println("");

            //Keeping looping until you get an appropriate user response
            boolean isValidInput = false;
            while(!isValidInput){

                //Get user response
                System.out.println(currentPlayer + ", please type in your desired move.");
                String input = scanner.nextLine();
                System.out.println("");

                isValidInput = processUserInput(board, currentPlayer, input);

                //Print the board to display
                printBoard(board);
            }

            //Check if there's a win
            theresAWin = ifSomeoneWon(board);

            if(theresAWin){
                //Congratulate if a player won
                System.out.println("Congrats, " + currentPlayer  + ", you won!");
                break;
            } else if(areAllElementsTaken(board)){
                /*If a player didn't win, and all elements are taken, it's a draw
                and end the game*/
                System.out.println("It's a draw.");
                break;
            }

            //Switch players
            currentPlayer = getOppositePlayer(currentPlayer);
        }
    }

    /**
    * Indicates if all elements on board are taken
    * @param board The board of the game
    * @return boolean Indicating if all elements are taken on the board
    */
    private static boolean areAllElementsTaken(char[][] board){
      for(int row = 0; row < board.length; row++){
        for(int col = 0; col < board[row].length; col++){
          if(board[row][col] == 0){
            return false;
          }
        }
      }

      return true;
    }

    /**
    * Prints the board
    * @param board char[][] The board of the game
    * @return Nothing
    */
    private static void printBoard(char[][] board){
        System.out.println("BOARD:");

        for(int row = 0; row < board.length; row++){
            String rowString = "";
            for(int col = 0; col < board[row].length; col++){
                char currentElement = board[row][col];

                rowString += ((int)currentElement == 0) ? '_' : currentElement;
            }

            System.out.println(rowString);
        }

        System.out.println("");
    }

    /**
    * Gets the string representing the opposite player of the current players
    * @param player String Representation of current players
    * @return String Representation of the opposite player
    */
    private static String getOppositePlayer(String player){
        if(player.equals(PLAYER_ONE)){
            return PLAYER_TWO;
        } else if(player.equals(PLAYER_TWO)){
            return PLAYER_ONE;
        } else {
            abortGame("Player name, " + player + ", was not recognized.");
        }

        return null;
    }

    /**
    * Abort the game with a reason to display in error message
    * @param reason String Reason to abort abortGame
    * @return Nothing
    */
    private static void abortGame(String reason){
        System.out.println("Oh no, someting went wrong. Reason: " + reason + ". Sorry about that.");

        System.exit(1);
    }

    /**
    * Process the user input on the next move in the game
    * @param board char[][] The board of the game
    * @param currentPlayer String Representation of the current player
    * @param userInput String The user's input
    * @return boolean if the user's input was valid
    */
    private static boolean processUserInput(char[][] board, String currentPlayer, String userInput){
        //Sanitize user's input
        userInput = userInput.toLowerCase().trim();

        /*If it's player one, mark board with 'x'.
        If player two, mark board with 'o'.*/
        if(userInput.equals(TOP_LEFT) && !topLeftIsTaken(board)){
            board[0][0] = (currentPlayer.equals(PLAYER_ONE)) ? 'x' : 'o';
        }else if(userInput.equals(TOP_CENTER) && !topCenterIsTaken(board)){
            board[0][1] = (currentPlayer.equals(PLAYER_ONE)) ? 'x' : 'o';
        }else if(userInput.equals(TOP_RIGHT) && !topRightIsTaken(board)){
            board[0][2] = (currentPlayer.equals(PLAYER_ONE)) ? 'x' : 'o';
        }else if(userInput.equals(MIDDLE_LEFT) && !middleLeftIsTaken(board)){
            board[1][0] = (currentPlayer.equals(PLAYER_ONE)) ? 'x' : 'o';
        }else if(userInput.equals(MIDDLE_CENTER) && !middleCenterIsTaken(board)){
            board[1][1] = (currentPlayer.equals(PLAYER_ONE)) ? 'x' : 'o';
        }else if(userInput.equals(MIDDLE_RIGHT) && !middleRightIsTaken(board)){
            board[1][2] = (currentPlayer.equals(PLAYER_ONE)) ? 'x' : 'o';
        }else if(userInput.equals(BOTTOM_LEFT) && !bottomLeftIsTaken(board)){
            board[2][0] = (currentPlayer.equals(PLAYER_ONE)) ? 'x' : 'o';
        }else if(userInput.equals(BOTTOM_CENTER) && !bottomCenterIsTaken(board)){
            board[2][1] = (currentPlayer.equals(PLAYER_ONE)) ? 'x' : 'o';
        }else if(userInput.equals(BOTTOM_RIGHT) && !bottomRightIsTaken(board)){
            board[2][2] = (currentPlayer.equals(PLAYER_ONE)) ? 'x' : 'o';
        }else{
            /*If user's input doesn't match anything exprected, tell the user and
            return false because user input is not valid.*/
            System.out.println("Not valid option, " + currentPlayer + ". Here are the possible moves:");
            System.out.println("");
            printPossibleMoves(board);
            System.out.println("");

            return false;
        }

        return true;
    }

    /**
    * Indicates if someone has won on the board
    * @param board char[][] The board of the game
    * @return boolean Indicating if somone has won
    */
    private static boolean ifSomeoneWon(char[][] board){

        /*CHECK FOR HORIZONTAL WINS*/

        /*Intialize win to true and set to false if row doesn't have all the
        same characters and all elements were marked by a player*/
        boolean horizontalWin = true;

        //Check by row
        for(int row = 0; row < board.length; row++){

            //Intialize boolean to true when checking each row
            horizontalWin = true;

            //Get the first element of the row
            int firstElement = board[row][0];

            //Check every element in the row
            for(int col = 0; col < board[row].length; col++){

                //Get the current element while iterating through row
                int currentElement = board[row][col];

                /*If current element wasn't marked yet (has value of 0) or the
                current element doesn't match the first in the row (all elements
                don't match), this row won't give us a horizontal win */
                if(currentElement == 0 || firstElement != currentElement){
                    horizontalWin = false;
                    continue;
                }
            }

            /*After traversing through each row, check if all elements where
            marked by a player AND were all the same marks.*/
            if(horizontalWin){
              return true;
            }
        }

        /*CHECK FOR VERTICAL WINS*/

        /*Set boolean to true and if we find a column that has a mark from more
        than one player or has an unmarked element, we set it to false*/
        boolean verticalWin = true;

        //Traverse by column
        for(int col = 0; col < board[0].length; col++){
            //Initialize boolean to true after checking each column
            verticalWin = true;

            /*Get the first element of the column. We use this to check against
            other elements of the same column. If there's a difference, column
            was marked by more than one character, and there is no win here.*/
            int firstElement = board[0][col];

            //Traverse through every element in the column
            for(int row = 0; row < board.length; row++){

                //Get current element while iterating through column
                int currentElement = board[row][col];

                /*If element hasn't been marked by a player or the current
                doesn't match the first (marked by more than one player), theres
                no win in this column*/
                if(currentElement == 0 || firstElement != currentElement){
                    verticalWin = false;
                }
            }

            /*Check if there's a win after traversing each column*/
            if(verticalWin){
              return true;
            }
        }

        /*CHECK DIAGONAL WIN FOR TOP-LEFT TO BOTTOM-RIGHT*/

        //Intialize boolean that indicates a diagonal win
        boolean diagonalWinTopBottom = true;

        //Get the first element in the diagonal row
        int firstElement = board[0][0];

        for(int i = 0; i < board.length; i++){
            int currentElement = board[i][i];

            /*Check the current element against the first element to see if this
            diagonal line was marked by more than one player*/
            if(currentElement == 0 || firstElement != currentElement){
                diagonalWinTopBottom = false;
            }
        }

        //If there was a diagonal win, return true
        if(diagonalWinTopBottom){
          return true;
        }

        /*CHECK DIAGONAL WIN FOR BOTTOM-LEFT TO TOP-RIGHT*/

        //Initialize boolean that indicates a diagonal win
        boolean diagonalWinBottomTop = true;

        //Get the first element in the diagonal row
        firstElement = board[2][0];

        int columnLength = board[0].length;
        for(int i = 0; i < columnLength; i++){
            int currentElement = board[columnLength - 1 - i][i];

            /*Check the curent element agains the first element to see if this
            diagonal line was marked by more than one player*/
            if(currentElement == 0 || firstElement != currentElement){
                diagonalWinBottomTop = false;;
            }
        }

        //If there was a diagonal win, return true
        if(diagonalWinBottomTop){
          return true;
        }

        return false;
    }

    /**
    * Print the moves the user can taken
    * @param board char[][] The board of the game
    * @return Nothing
    */
    private static void printPossibleMoves(char[][] board){
        if(!topLeftIsTaken(board)){
            System.out.println(TOP_LEFT);
        }

        if(!topCenterIsTaken(board)){
            System.out.println(TOP_CENTER);
        }

        if(!topRightIsTaken(board)){
            System.out.println(TOP_RIGHT);
        }

        if(!middleLeftIsTaken(board)){
            System.out.println(MIDDLE_LEFT);
        }

        if(!middleCenterIsTaken(board)){
            System.out.println(MIDDLE_CENTER);
        }

        if(!middleRightIsTaken(board)){
            System.out.println(MIDDLE_RIGHT);
        }

        if(!bottomLeftIsTaken(board)){
            System.out.println(BOTTOM_LEFT);
        }

        if(!bottomCenterIsTaken(board)){
            System.out.println(BOTTOM_CENTER);
        }

        if(!bottomRightIsTaken(board)){
            System.out.println(BOTTOM_RIGHT);
        }
    }

    /**
    * Indication of if the top left element of the board is taken
    * @param board char[][] The board of the game
    * @return boolean Indicating if the top left element of the board is taken
    */
    private static boolean topLeftIsTaken(char[][] board){
        if(board == null){
            return false;
        }

        return board[0][0] != 0;
    }

    /**
    * Indication of if the top center element of the board is taken
    * @param board char[][] The board of the game
    * @return boolean Indicating if the top center element of the board is taken
    */
    private static boolean topCenterIsTaken(char[][] board){
        if(board == null){
            return false;
        }

        return board[0][1] != 0;
    }

    /**
    * Indication of if the top right element of the board is taken
    * @param board char[][] The board of the game
    * @return boolean Indicating if the top right element of the board is taken
    */
    private static boolean topRightIsTaken(char[][] board){
        if(board == null){
            return false;
        }

        return board[0][2] != 0;
    }

    /**
    * Indication of if the middle left element of the board is taken
    * @param board char[][] The board of the game
    * @return boolean Indicating if the middle left element of the board is taken
    */
    private static boolean middleLeftIsTaken(char[][] board){
        if(board == null){
            return false;
        }

        return board[1][0] != 0;
    }

    /**
    * Indication of if the middle center element of the board is taken
    * @param board char[][] The board of the game
    * @return boolean Indicating if the middle center element of the board is taken
    */
    private static boolean middleCenterIsTaken(char[][] board){
        if(board == null){
            return false;
        }

        return board[1][1] != 0;
    }

    /**
    * Indication of if the middle right element of the board is taken
    * @param board char[][] The board of the game
    * @return boolean Indicating if the middle right element of the board is taken
    */
    private static boolean middleRightIsTaken(char[][] board){
        if(board == null){
            return false;
        }

        return board[1][2] != 0;
    }

    /**
    * Indication of if the bottom left element of the board is taken
    * @param board char[][] The board of the game
    * @return boolean Indicating if the bottom left element of the board is taken
    */
    private static boolean bottomLeftIsTaken(char[][] board){
        if(board == null){
            return false;
        }

        return board[2][0] != 0;
    }

    /**
    * Indication of if the bottom center element of the board is taken
    * @param board char[][] The board of the game
    * @return boolean Indicating if the bottom center element of the board is taken
    */
    private static boolean bottomCenterIsTaken(char[][] board){
        if(board == null){
            return false;
        }

        return board[2][1] != 0;
    }

    /**
    * Indication of if the bottom right element of the board is taken
    * @param board char[][] The board of the game
    * @return boolean Indicating if the bottom right element of the board is taken
    */
    private static boolean bottomRightIsTaken(char[][] board){
        if(board == null){
            return false;
        }

        return board[2][2] != 0;
    }
}
