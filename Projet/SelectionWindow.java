import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SelectionWindow extends JFrame {
    //CLASSE DE NOTRE MENU
    private Image backgroundImage;

    public SelectionWindow() { //Constructeur
        // Chargez l'image d'arrière-plan
        ImageIcon icon = new ImageIcon("image/bg.jpg");
        backgroundImage = icon.getImage();
        setIconImage(new ImageIcon("image/image.png").getImage());
        setTitle("Main Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(350, 200);
        setLocationRelativeTo(null);
        setResizable(false);

        // Utilisez le panneau personnalisé pour l'arrière-plan
        BackgroundPanel backgroundPanel = new BackgroundPanel();
        setContentPane(backgroundPanel);
        setLayout(new BorderLayout());

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setOpaque(false); // Rend le panneau transparent

        JLabel titleLabel = new JLabel("Menu Puissance 4");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 25));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setForeground(Color.WHITE);

        JButton btnPlayAI = createButton("Jeu contre AI", true);
        JButton btnPlay1v1 = createButton("Jeu 1v1", true);
        JButton btnHelp = createButton("Aide", true);

        centerPanel.add(titleLabel);
        centerPanel.add(Box.createRigidArea(new Dimension(20, 10)));
        centerPanel.add(btnPlayAI);
        centerPanel.add(Box.createRigidArea(new Dimension(20, 10)));
        centerPanel.add(btnPlay1v1);
        centerPanel.add(Box.createRigidArea(new Dimension(20, 10)));
        centerPanel.add(btnHelp);

        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        backgroundPanel.add(centerPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    private JButton createButton(String text, boolean enableAction) { //Créer les boutons pour notre menu
        JButton button = new JButton(text);
        button.setEnabled(enableAction);
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(0, 0, 0, 255));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        setFontAndAddMouseListener(button);
        //En fonction du choix du joueur on le redirige
        if (enableAction) {
            if (text.equals("Jeu 1v1")) {
                button.addActionListener(e -> openPseudoInputWindow(false));
                
            } else if (text.equals("Aide")){
                button.addActionListener(e -> showHelpDialog());
            } else {
                button.addActionListener(e -> openPseudoInputWindow(true));
            }
        
        }
        return button;
    }


    private void openPseudoInputWindow(boolean ia) { //Ouvre la fenêtre des pseudo
        dispose();
        PseudoInputWindow pseudoInputWindow = new PseudoInputWindow(ia);
        pseudoInputWindow.setVisible(true);
    }

    private void showHelpDialog() { //Ouvre une boite de dialogue qui explique les règles
        JOptionPane.showMessageDialog(this,
            "Bienvenue dans Puissance 4 !\n" +
            "- Le jeu se joue à deux joueurs.\n" +
            "- Chacun à son tour, les joueurs déposent un jeton dans une colonne.\n" +
            "- Le jeton tombe au niveau le plus bas de la colonne choisie.\n" +
            "- Le but est d'aligner 4 jetons de sa couleur horizontalement, verticalement ou diagonalement.\n" +
            "- Le premier joueur à aligner 4 jetons gagne la partie.\n"+
            "- Si toutes les cases sont remplies sans alignement de 4 jetons, la partie est déclarée nulle.\n" +
            "Bonne chance et amusez-vous !",
            "Aide", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void setFontAndAddMouseListener(JButton button) { // Hover
        Font originalFont = button.getFont();
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setFont(originalFont.deriveFont(originalFont.getSize() * 1.2f)); // Augmente la taille de la police de 20%
            }
    
            @Override
            public void mouseExited(MouseEvent e) {
                button.setFont(originalFont); // Rétablit la police d'origine
            }
        });
    }
    
    // Classe interne pour le panneau avec image d'arrière-plan
    private class BackgroundPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            // Dessinez l'image en arrière-plan
            g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
        }
    }
    
    public static void main(String[] args) { //Lance le menu
        SwingUtilities.invokeLater(() -> new SelectionWindow());
    }
}    
