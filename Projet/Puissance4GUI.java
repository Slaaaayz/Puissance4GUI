import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;

public class Puissance4GUI extends JFrame {
    // NOTRE JEU
    private Board board;
    private Player player1;
    private Player player2;
    private Player currentPlayer;
    private boolean IA;
    Color myYellow = new Color(227, 186, 5);
    Color myRed = new Color(166, 0, 17);


    public Puissance4GUI(boolean ia, String name1, String name2) { //Constructeur
        setTitle("Puissance 4");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 500);
        getContentPane().setBackground(new Color(0, 81, 255));
        setResizable(false);
        setLayout(new GridLayout(6, 7, 30, 5));
        setLocationRelativeTo(null);
        setIconImage(new ImageIcon("image/image.png").getImage());
        board = new Board();
        player1 = new Player(name1, myRed);
        if (ia) {
            player2 = new Player(" IA ", Color.BLACK);
        } else {
            player2 = new Player(name2, myYellow);
        }
        currentPlayer = player1;
        initBoard(board.getBoardButtons());
        IA = ia;
    }

    public Player getPlayer1(){ //Getter
        return  this.player1;
    }

    public Player getCurrentplayer(){ //Getter
        return  this.currentPlayer;
    }

    public void initBoard(JButton[][] boardButtons) { //Initialise la board avec tt les boutons
        for (int row = 5; row >= 0; row--) {
            for (int col = 0; col < 7; col++) {
                boardButtons[row][col] = new CircularButton(col, this, this.currentPlayer);
                boardButtons[row][col].setBackground(new Color(0, 81, 255));
                boardButtons[row][col].setOpaque(true);
                boardButtons[row][col].setBorderPainted(false);
                boardButtons[row][col].addActionListener(new ButtonClickListener(col));
                add(boardButtons[row][col]);
            }
        }
    }

    public void ChangeColorCol(int col, Color color) { //Change la couleur d'une colonne entière (pour le hover)
        for (int x = 0; x<6; x++) {
                if (!(board.getBoardButtons()[x][col].getCircleColor() == myRed || board.getBoardButtons()[x][col].getCircleColor() == myYellow || board.getBoardButtons()[x][col].getCircleColor() == Color.BLACK)) {
                    if (color == Color.WHITE) {
                        board.getBoardButtons()[x][col].setCircleColor(color);
                    } else if (currentPlayer.getColor() == myRed) {
                        board.getBoardButtons()[x][col].setCircleColor(new Color(196, 108, 117));
                    } else {
                        board.getBoardButtons()[x][col].setCircleColor(new Color(255, 239, 171));
                    }  
                }
        }
    }


    private class ButtonClickListener implements ActionListener { //Nos boutons
        private int col;

        public ButtonClickListener(int col) {
            this.col = col;
        }

        @Override
        public void actionPerformed(ActionEvent e) { // Si cliqué
            if (!ApplyChoice(col)) {  //Si colonne pleine on fait rien
                JOptionPane.showMessageDialog(null, "The row is already full !");
            } else {
                //Vérification de la victoire et de l'égalité
                if (board.Win(currentPlayer.getColor())) {
                    openSelectionWindow();
                    JOptionPane.showMessageDialog(null, currentPlayer.getName() + " won !");
                } else if (board.Tie()) {
                    openSelectionWindow();
                    JOptionPane.showMessageDialog(null, "Tie !");
                } else {
                    // Si on joue contre l'IA on la fait jouer
                    if (IA) {
                        switchPlayer();
                        int iachoice = IAChoice()-1; // Choix de l'IA
                        while (!ApplyChoice(iachoice)) { 
                            iachoice = IAChoice()-1;
                        }
                        //Vérification de la victoire et de l'égalité
                        if (board.Win(currentPlayer.getColor())) {
                            RickRoll();
                            openSelectionWindow();
                            JOptionPane.showMessageDialog(null, currentPlayer.getName() + " won !");
                            
                        } else if (board.Tie()) {
                            openSelectionWindow();
                            JOptionPane.showMessageDialog(null, "Tie !");
                        } 
                    } 
                    // On change de joueur
                    switchPlayer();
                }
            }

        }
    }

    public boolean ApplyChoice(int choice) { // Retourne true si le choix est appliqué et applicable sinon retourne false
        if (board.getBoard()[0][choice] != null) {
            System.out.println("This row is already full !");
            return false;
        } 
        for (int i = 5; i >= 0; i--) {
            if (board.getBoard()[i][choice] == null) {
                board.getBoard()[i][choice] = currentPlayer.getColor();
                updateButton(5-i, choice);
                return true;
            }
        }
        return false;
    }

    public int IAChoice() { //L'IA choisit une colonne
        int columns = 7;
        int rows = 6;
        //Simule tt les possibilités du jaune
        for (int col = 0; col < columns; col++) {
            if (board.getBoard()[0][col]==null) {
                for (int row = rows - 1; row >= 0; row--) {
                    if (board.getBoard()[row][col]==null) {
                        board.getBoard()[row][col] = myYellow;
                        if (board.Win(Color.BLACK)) {// Si ça mène à la victoire
                            board.getBoard()[row][col] = null;
                            return col + 1; //On choisit cette colonne
                        }
                        board.getBoard()[row][col] = null;
                        break;
                    }
                }
            }
        }
        //Simule tt les possibilités du rouge
        for (int col = 0; col < columns; col++) {
            if (board.getBoard()[0][col]==null) {
                for (int row = rows - 1; row >= 0; row--) {
                    if (board.getBoard()[row][col] ==null) {
                        board.getBoard()[row][col] = myRed;
                        if (board.Win(myRed)) { //Si il risque de gagner
                            board.getBoard()[row][col] = null;
                            return col + 1;//On le bloque
                        }
                        board.getBoard()[row][col] = null;
                        break;
                    }
                }
            }
        }
        //Sinon on choisit de manière aléatoire
        return (int) (Math.random() * columns) + 1;
    }

    private void updateButton(int row, int col) { // Change la couleur du bouton à la position (row,col)
        CircularButton button = (CircularButton) board.getBoardButtons()[row][col];
        button.setCircleColor(currentPlayer.getColor());
    }


    private void switchPlayer() { // Change le currentPlayer
        if (currentPlayer == player1) {
            currentPlayer = player2;
        } else {
            currentPlayer = player1;
        }
    }

    private void openSelectionWindow() { //Ouvre le menu
        this.dispose();
        SelectionWindow selectionWindow = new SelectionWindow();
        selectionWindow.setVisible(true);
    }

    public static void RickRoll() { //Ouvre le rickroll 
        String url = "https://9.999999999999999999999999999999999999999999999999999999.ovh/";

        try {
            // Vérifier si le bureau est pris en charge
            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                // Ouvrir l'URL dans le navigateur par défaut
                Desktop.getDesktop().browse(new URI(url));
            } else {
                System.out.println("Desktop not supported, cannot open the web page.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}