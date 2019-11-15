import java.util.Scanner;

public class Game {

    // The following five constants were defined in the starter code (kt54)
    private static String WHITEPLAYS_MSG    = "White plays. Enter move:";
    private static String BLACKPLAYS_MSG    = "Black plays. Enter move:";
    private static String ILLEGALMOVE_MSG   = "Illegal move!";
    private static String WHITEWINS_MSG     = "White wins!";
    private static String BLACKWINS_MSG     = "Black wins!";

    private Board gameBoard;
     
    // Minimal constructor. Expand as needed (kt54)
    public Game() {
        gameBoard = new Board();
        
    }

    

    // Build on this method to implement game logic.
    public void play() {

        Scanner reader = new Scanner(System.in);

        gameBoard = new Board();
       
        boolean done = false;
        boolean isBlackTurn = true;
        String command = "";


        do {
            gameBoard.printBoard();
            if(isBlackTurn){
                System.out.println(BLACKPLAYS_MSG);
            }
            else{
                System.out.println(WHITEPLAYS_MSG);
            }

            String pos1 = reader.nextLine().trim();
            checkForQuit(pos1);
            
            String pos2 = reader.nextLine().trim();
            checkForQuit(pos2);
            

            if(checkValidInput(pos1) && checkValidInput(pos2)){
                if(isBlackTurn){
                    if(gameBoard.isMoveLegal(pos1, pos2, gameBoard.getBlackMan())){
                        gameBoard.makeMove(pos1, pos2, gameBoard.getBlackMan(),gameBoard.getWhiteMan());
                    }
                    else{
                        System.out.println(ILLEGALMOVE_MSG);
                        continue; //leave loop here and start next iteration
                    }
                }
                else{
                    if(gameBoard.isMoveLegal(pos1, pos2, gameBoard.getWhiteMan())){
                        gameBoard.makeMove(pos1, pos2, gameBoard.getWhiteMan(),gameBoard.getBlackMan());
                    }
                    else{
                        System.out.println(ILLEGALMOVE_MSG);
                        continue;
                    }
                }
            }
            else{
                System.out.println(ILLEGALMOVE_MSG);
                continue; 
            }


            isBlackTurn = !isBlackTurn;
            
            
            // This is just demonstration code, so we immediately let white win
            // to avoid unnecessary violence.
            System.out.println(BLACKWINS_MSG);
            //done = true;
            
            
        } while(!done);
        
    }

    public void checkForQuit(String input){
        if(input.equals("quit")){
            System.exit(0);
        }
    }

    // sees if cooridnates are on board

    public boolean checkValidInput(String input){
        if(input.length()==2){
            char col = input.charAt(0);
            int colIndex = (int) col - 97; //turns ascii number into equivalent index in array
            char row = input.charAt(1);
            int rowIndex = Character.getNumericValue(row) - 1;

            if(colIndex >= 0 && colIndex < gameBoard.getBoardSize()){
                if(rowIndex >=0 && rowIndex< gameBoard.getBoardSize()){
                    return true;
                }
            return false;
            }
        }return false;
      
    }
    

    /*public String getValidInput(String input){
        if(checkValidInput(input)){
            return input;
        }
        else {
            return "";
        }
    }*/   

}

