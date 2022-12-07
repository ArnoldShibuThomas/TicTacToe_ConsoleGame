/** This class implements the console interface the user interacts with in the  game and contains the main method
 *
 * @author Arnold Shibu
 * @verison 1.0 Aug 5, 2022.
 *
 */
package ui;

//import statements
import core.TicTacToeLogic;
import java.util.*;

public class TicTacToeConsole {

    //main method
    public static void main(String args[]){
        //create a logic object
        TicTacToeLogic logic = new TicTacToeLogic();

        // this value is by default set to zero for O to go first
        int turn = 0;

        //variable to capture player moves
        String moveO;
        String moveX;

        //this will be default set to zero to represent that the game is not over yet
        int complete = 0;

        //greets player and explains rules
        System.out.println("Welcome to Tic Tac Toe!");
        System.out.println("=====    Rules    =====");
        System.out.println("Get three in a row and you can win!");
        System.out.println("=======================\n");

        //display board
        System.out.println("Here is the Board, lets go!");
        logic.display();
        System.out.print("\n");

        //as long as complete doesn't equal 1 continue asking for input since the game isn't over
        while(complete != 1){

            //check to see who's turn it is and execute appropriate line
            if(turn == 0){
                System.out.println("Player O please make a selection! (Please type in a selection in rowColumn order i.e. A1)");
                //take user input and pass the logic
                //create a new scanner object
                Scanner scan = new Scanner(System.in);
                moveO = scan.nextLine();
                //validate the move the user provided
                logic.validateMove(turn,moveO);
                //give the other player the turn
                turn++;
                //display updated board
                logic.display();
                //check to see if any player has won
                complete = logic.hasWon();
            }
            else{
                System.out.println("Player X please make a selection! (Please type in a selection in rowColumn order i.e. A1)");
                //take user input and pass the logic
                Scanner scan = new Scanner(System.in);
                moveX = scan.nextLine();
                //validate the move the user provided
                logic.validateMove(turn,moveX);
                //give the other player the turn
                turn--;
                //display completed board
                logic.display();
                //check to see if any player has won
                complete = logic.hasWon();
            }
        }
    }

}
