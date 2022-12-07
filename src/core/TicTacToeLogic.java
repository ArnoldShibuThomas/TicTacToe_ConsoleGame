/** This class implements the logic behind the game such as the board and mapping movements
 *
 * @author Arnold Shibu
 * @verison 1.0 Aug 5, 2022.
 *
 */
package core;

import java.util.Scanner;

public class TicTacToeLogic {

    //private instance variable that only the class has access to
    private String[][] board;
    private int oCount;
    private int xCount;

    //constructor that has no parameters it takes in
    public TicTacToeLogic(){
        //create a blank board
        board = new String[3][3];

        //loop over the row
        for(int i = 0;i < board.length; i++ ){
            //loop over column
            for(int j =0; j <board[i].length; j++){
                board[i][j] = " ";
            }
        }

    }

    //this will validate the user provide choice and see if it is valid
    public boolean validateMove(int whosTurn, String location){
        //o for O and 1 for X

        //fist find out if the location they provided is valid
        boolean valid;
        valid = locationValidation(whosTurn,location);
        String move;

        //if the move is true return
        if(valid){
            //go ahead and return
            return true;
        }
        else{
            while(!valid){
                if(whosTurn == 0){
                    System.out.println("Player O please make a valid selection! (Please type in a selection in rowColumn order i.e. A1)");
                    Scanner scan = new Scanner(System.in);
                    move = scan.nextLine();
                }
                else{
                    System.out.println("Player X please make a valid selection! (Please type in a selection in rowColumn order i.e. A1)");
                    Scanner scan = new Scanner(System.in);
                    move = scan.nextLine();
                }
                valid = validateMove(whosTurn,move);
            }
            return true;
        }

    }

    //helper method to validate location
    private boolean locationValidation(int whosTurn,String location){

        //store column info into a variable
        String rowFromLocation = location.substring(0,1);
        String columnFromLocation = location.substring(1,location.length());

        //variables that store the translation index on this two-dimensional array
        int rowOnBoard = -1;
        int columnOnBoard = -1;

        //by the time this if else if and else branch is complete we should have translated the row or at least told the user what they did wrong
        if(rowFromLocation.equals("A")){
            rowOnBoard = 0;
        }
        else if(rowFromLocation.equals("B")){
            rowOnBoard = 1;
        }
        else if(rowFromLocation.equals("C")){
            rowOnBoard = 2;
        }
        else if(rowFromLocation.equals("a") || rowFromLocation.equals("b") || rowFromLocation.equals("c")){
            System.out.println("Please make sure to type in coordinates as Upper case letters!");
            return false;
        }
        else{
            System.out.println(rowFromLocation + " is out of bounds!");
            return false;
        }

        //let's extract out the column information
        columnOnBoard = Integer.parseInt(columnFromLocation) - 1;
        if(columnOnBoard >2){
            System.out.println("Sorry but " + columnOnBoard + " is not a column number on the board!");
            return false;
        }

        //now check to see if the board can have anything place at that location!
        if(rowOnBoard != -1 && columnOnBoard != -1){
            //create a location variable to store boolean value that we got as the result of the upcoming method
            boolean onBoard;
            //place on board method
            onBoard = putPieceOnBoard(rowOnBoard,columnOnBoard,whosTurn);
            //if this method returns true attempt to return true
            if(onBoard == true){
                return true;
            }
            //else return false
            return false;

        }

        return false;
    }

    //This is the Method called to place the piece on the board!
    private boolean putPieceOnBoard(int x, int y, int whosTurn){

        //translate whose turn it is into a piece
        String piece;
        if(whosTurn == 0){
            piece = "O";
        }
        else{
            piece = "X";
        }

        //check to see if that spot is empty
        if(board[x][y].equals(" ")){
            //if the place on the board is empty attempt to place
            board[x][y] = piece;
            if(piece.equals("O")){
                oCount++;
            }
            else{
                xCount++;
            }
            System.out.println("Move successful!");
            return true;
        }
        if((board[x][y].equals("X") && whosTurn == 0) || (board[x][y].equals("O") && whosTurn == 1) ){
            System.out.println("Move unsuccessful as you cannot place your piece on top of an opponent");
            return false;
        }

        System.out.println("Place don't try placing your piece in an area you place already");
        return false;
    }


    //this methods checks to see if any of the players have won.
    public int hasWon(){
        //a temporary variable to count
        int counter = oCount;
        int row = 0;
        int column = 0;

        //check O First
        while(counter != 0){
            if(board[row][column].equals("O")){
                //reduce counter
                counter--;

                //check to see if there is 3 in a row from right to left
                if(column-1 != -1){
                    //does the one to the left of me have any similar pieces
                    if(board[row][column-1].equals("O")){
                        if(column+1 != 3){
                            if(board[row][column+1].equals("O")){
                                winMessage("O");
                                return 1; //we have 3 in a row right to left
                            }
                        }
                    }
                }

                //check to see if there is 3 in a row from top to bottom
                if(row -1 != -1){
                    if(board[row-1][column].equals("O")){
                        if(row + 1 != 3){
                            if(board[row + 1][column].equals("O")){
                                winMessage("O");
                                return 1; //we have 3 in a row top to bottom
                            }
                        }
                    }
                }

                //check to see if there is 3 in a row diagonally
                if(row-1 != -1 && column -1 != -1){
                    if(board[row-1][column-1].equals("O")){
                        if(row + 1 != 3 && column +1 != 3){
                            if(board[row + 1][column+1].equals("O")){
                                winMessage("O");
                                return 1; //we have 3 in a row diagonally
                            }
                        }
                    }
                }

            }

            //increment to next spot in 2d array
            column++;
            if(column == 3){
                if(row !=3) {
                    row++;
                    column = 0;
                }
            }

        }

        //if we made it to this part of the code we have determined that Player O has gotten nothing in a row, yet now we want to check Player X
        counter = xCount;
        //reset counter
        row = 0;
        column = 0;
        //Check X now
        while(counter != 0){

            if(board[row][column].equals("X")){
                //reduce counter
                counter--;

                //check to see if there is 3 in a row from right to left
                if(column-1 != -1){
                    //does the one to the left of me have any similar pieces
                    if(board[row][column-1].equals("X")){
                        if(column+1 != 3){
                            if(board[row][column+1].equals("X")){
                                winMessage("X");
                                return 1; //we have 3 in a row right to left
                            }
                        }
                    }
                }

                //check to see if there is 3 in a row from top to bottom
                if(row -1 != -1){
                    if(board[row-1][column].equals("X")){
                        if(row + 1 != 3){
                            if(board[row + 1][column].equals("X")){
                                winMessage("X");
                                return 1; //we have 3 in a row top to bottom
                            }
                        }
                    }
                }

                //check to see if there is 3 in a row diagonally
                if(row-1 != -1 && column -1 != -1){
                    if(board[row-1][column-1].equals("X")){
                        if(row + 1 != 3 && column +1 != 3){
                            if(board[row + 1][column+1].equals("X")){
                                winMessage("X");
                                return 1; //we have 3 in a row diagonally
                            }
                        }
                    }
                }

            }


            //increment to next spot in 2d array
            column++;
            if(column == 3){
                if(row !=3) {
                    row++;
                    column = 0;
                }
            }
        }

        int filledSquares = 0;

        //look at empty square
        for(int i = 0;i < board.length; i++ ){
            //loop over column
            for(int j =0; j <board[i].length; j++){
                if(board[i][j] == "X" || board[i][j] == "O"){
                    filledSquares++;
                }
            }
        }

        //if they game is a tie and no one has any matches
        if(filledSquares == (board.length * board[0].length)){
            winMessage("Nan");
            return 1;
        }


        //if none of the pieces have anything in a row keep playing by returning 0
        return 0;
    }

    private void winMessage(String winner){

        //clear Counts
        xCount = 0;
        oCount = 0;

        //clear board
        for(int i = 0;i < board.length; i++ ){
            //loop over column
            for(int j =0; j <board[i].length; j++){
                board[i][j] = " ";
            }
        }
        //If the game ends in a tie
        if(!winner.equals("Nan")) {
            System.out.println("Congrats Player " + winner + " you have managed to get 3 in a row! Good Job!");
        }
        //if the game is a win for one team
        else{
            System.out.println("The Game was tied! No one wins");
        }
    }

    //this method is called when the board needs to be displayed onto the console
    public void display(){
        String letters[] = {"A", "B", "C"};

        System.out.println("   1 2 3");
        //loop over the row
        for(int i = 0;i < board.length; i++ ){
            //loop over column
            for(int j =0; j <board[i].length; j++){
                if(j ==0){
                    System.out.print(letters[i] + " |" + board[i][j] + "|");
                }
                else{
                    System.out.print(board[i][j] + "|");
                }
            }

            System.out.print("\n");
        }

    }



}
