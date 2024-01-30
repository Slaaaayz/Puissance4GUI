import java.awt.Color;

public class Board {
    //CLASSE DE NOTRE PLATEAU DE JEU
    private CircularButton[][] boardButtons;
    private Color[][] board = new Color[6][7];

    public Board() {//Constructeur
        for (int x = 0; x < 6; x++){
            for (int y = 0; y < 7; y++){
                board[x][y] = null;
            }
        }
        boardButtons = new CircularButton[6][7];
    }

    public Color[][] getBoard() { //Getter
        return board;
    }

    public CircularButton[][] getBoardButtons() {//Getter
        return boardButtons;
    }

    public boolean Tie() { //Vérifie si il y a match nul
        for (int x = 0; x < 6; x++){
            for (int y = 0; y < 7; y++){
                if (board[x][y] == null) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean Win(Color couleur) { //Vérifie si la couleur passé en paramètre a gagné
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length - 3; j++) {
                if (board[i][j] == couleur && board[i][j + 1] == couleur && board[i][j + 2] == couleur && board[i][j + 3] == couleur) {
                    return true;
                }
            }
        }
        for (int i = 0; i < board.length - 3; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == couleur && board[i + 1][j] == couleur && board[i + 2][j] == couleur && board[i + 3][j] == couleur) {
                    return true;
                }
            }
        } 
        for (int i = 3; i < board.length; i++) {
            for (int j = 0; j < board[i].length - 3; j++) {
                if (board[i][j] == couleur && board[i - 1][j + 1] == couleur && board[i - 2][j + 2] == couleur && board[i - 3][j + 3] == couleur) {
                    return true;
                }
            }
        }
        for (int i = 0; i < board.length - 3; i++) {
            for (int j = 0; j < board[i].length - 3; j++) {
                if (board[i][j] == couleur && board[i + 1][j + 1] == couleur && board[i + 2][j + 2] == couleur && board[i + 3][j + 3] == couleur) {
                    return true;
                }
            }
        }
        return false;
    }
}
