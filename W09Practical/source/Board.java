public class Board {

    // The following four constants were defined in the starter code (kt54)
    private static final int  DEFAULT_SIZE = 8;
    private static final char FREE         = '.';
    private static final char WHITE_MAN    = '⛀';
    private static final char BLACK_MAN    = '⛂';

    // The following constants are needed for Part 4
    private static final char WHITE_KING   = '⛁';
    private static final char BLACK_KING   = '⛃';

    private int boardsize;
    private char[][] board;
    private Game game;
    private char pieceToTake;
    

    // Default constructor was provided by the starter code. Extend as needed (kt54) 
    public Board() {
        this.boardsize = DEFAULT_SIZE;

        board = new char[boardsize][boardsize];

        // Clear all playable fields
        for(int x=0; x<boardsize; x++)
            for(int y=0; y<boardsize; y++)
                board[x][y] = FREE;

        // Put a single piece in the middle
        // Obviously, you will need to replace this with your own initialisation code
        // Initialises the board

        for(int i = 0; i<boardsize; i+=2){

            board[i+1][0] = BLACK_MAN;
            board[i][1]   = BLACK_MAN;
            board[i+1][2] = BLACK_MAN;

            board[i][boardsize-3]  = WHITE_MAN;
            board[i+1][boardsize-2]= WHITE_MAN;
            board[i][boardsize-1]  = WHITE_MAN;

        }
    }

    // Prints the board. This method was provided with the starter code. Better not modify to ensure
    // output consistent with the autochecker (kt54)
    public void printBoard() {

        // Print the letters at the top
        System.out.print(" ");
        for(int x=0; x<boardsize; x++) 
            System.out.print(" " + (char)(x + 'a'));
        System.out.println();

        for(int y=0; y<boardsize; y++)
        {
            // Print the numbers on the left side
            System.out.print(y+1);

            // Print the actual board fields
            for(int x=0; x<boardsize; x++) {
                System.out.print(" ");
                char value = board[x][y];
                if(value == FREE) {
                    System.out.print(".");
                } else if(value == WHITE_MAN  || value == BLACK_MAN ||
                          value == WHITE_KING || value == BLACK_KING) {
                    System.out.print(value);
                } else {
                    System.out.print("X");
                }
            }
            // Print the numbers on the right side
            System.out.println(" " + (y+1));
        }

        // Print the letters at the bottom
        System.out.print(" ");
        for(int x=0; x<boardsize; x++) 
            System.out.print(" " + (char)(x + 'a'));
        System.out.print("\n\n");
    }

    public int getBoardSize(){
        return DEFAULT_SIZE;
    }

    public boolean isPieceThere(int col, int row, char piece){
        if(board[col][row] == piece){
            return true;
        }
        return false;
    }

    
    public boolean isMoveLegal(String pos1, String pos2, char piece){
        char startCol = pos1.charAt(0);
        int startColIndex = (int) startCol - 97; 
        char startRow = pos1.charAt(1);
        int startRowIndex = Character.getNumericValue(startRow) - 1;

        char endCol = pos2.charAt(0);
        int endColIndex = (int) endCol - 97; 
        char endRow = pos2.charAt(1);
        int endRowIndex = Character.getNumericValue(endRow) - 1;

        if(isPieceThere(startColIndex, startRowIndex, piece) && isPieceThere(endColIndex,endRowIndex,FREE)
           && isMoveDiagonal(startColIndex, startRowIndex, endColIndex, endRowIndex, piece)){
            return true;
        }
        
        return false;
    }

    public boolean isMoveDiagonal(int startColIndex, int startRowIndex, int endColIndex, int endRowIndex, char piece){
        if(startColIndex == 0){
            if(endColIndex == startColIndex + 1){
                return true;
            }
        }
        else if(startColIndex == DEFAULT_SIZE - 1){
            if(endColIndex == startColIndex - 1){
                return true;
            }
        }
        else if(startRowIndex == 0){
            if(endRowIndex == startRowIndex + 1){
                return true;
            }
        }
        else if(startRowIndex == DEFAULT_SIZE - 1){
            if(endRowIndex == startRowIndex - 1){
                return true;
            }
        }
        else if(endColIndex == startColIndex + 1 && endRowIndex == startRowIndex + 1 && piece == BLACK_MAN ||
           endColIndex == startColIndex - 1  && endRowIndex == startRowIndex + 1 && piece == BLACK_MAN){
               return true;
           }
        else if(endColIndex == startColIndex + 1 && endRowIndex == startRowIndex - 1 && piece == WHITE_MAN ||
           endColIndex == startColIndex - 1  && endRowIndex == startRowIndex - 1 && piece == WHITE_MAN ){
               return true;
           }
        else if(checkCanTake(startColIndex, startRowIndex, endColIndex, endRowIndex, piece)){
            return true;
        }
            
        return false;
    }

    public boolean makeMove(String pos1, String pos2, char piece, char pieceToTake){
        char startCol = pos1.charAt(0);
        int startColIndex = (int) startCol - 97; 
        char startRow = pos1.charAt(1);
        int startRowIndex = Character.getNumericValue(startRow) - 1;

        char endCol = pos2.charAt(0);
        int endColIndex = (int) endCol - 97; 
        char endRow = pos2.charAt(1);
        int endRowIndex = Character.getNumericValue(endRow) - 1;

        if(isMoveLegal(pos1,pos2, piece) && isMoveDiagonal(startColIndex, startRowIndex, endColIndex, endRowIndex, piece)){
            board[startColIndex][startRowIndex] = FREE;
            board[endColIndex][endRowIndex] = piece;
        }
        if(isMoveLegal(pos1, pos2, piece)&& checkCanTake(startColIndex, startRowIndex, endColIndex, endRowIndex, piece)){
            board[startColIndex][startRowIndex] = FREE;
            board[endColIndex][endRowIndex] = piece;
            
            checkCanTake(startColIndex, startRowIndex, endColIndex, endRowIndex, piece);
            
            return true;
        }
        return false;    
    }

    public char getFree() {
        return FREE;
    }

    public char getWhiteMan() {
        return WHITE_MAN;
    }

    public char getBlackMan() {
        return BLACK_MAN;
    }

    public boolean checkCanTake(int startColIndex, int startRowIndex, int endColIndex, 
                                int endRowIndex, char piece){
       if(endColIndex == startColIndex + 2 && endRowIndex == startRowIndex + 2 ||
          endColIndex == startColIndex - 2 && endRowIndex == startRowIndex + 2 ||
          endColIndex == startColIndex + 2 && endRowIndex == startRowIndex - 2 ||
          endColIndex == startColIndex - 2 && endRowIndex == startRowIndex - 2){
            if(endColIndex == startColIndex + 2 && endRowIndex == startRowIndex + 2 && piece == BLACK_MAN &&
                board[startColIndex + 1][startRowIndex + 1] == WHITE_MAN){
                board[startColIndex + 1][startRowIndex + 1] = FREE;
                return true;
            }
            else if(endColIndex == startColIndex - 2 && endRowIndex == startRowIndex + 2 && piece == BLACK_MAN &&
                board[startColIndex - 1][startRowIndex + 1] == WHITE_MAN){
                board[startColIndex - 1][startRowIndex + 1] = FREE;
                return true;
            }
            else if(endColIndex == startColIndex + 2 && endRowIndex == startRowIndex - 2 && piece == WHITE_MAN &&
                board[startColIndex + 1][startRowIndex - 1] == BLACK_MAN){
                board[startColIndex + 1][startRowIndex - 1] = FREE;
                return true;
            }
            else if(endColIndex == startColIndex - 2 && endRowIndex == startRowIndex - 2 && piece == WHITE_MAN &&
                board[startColIndex - 1][startRowIndex - 1] == BLACK_MAN){
                board[startColIndex - 1][startRowIndex - 1] = FREE;
                return true;
            }
            else{
            
                return false;
            }
        
            
        }
        else{
            return true;
        }   
    }
}