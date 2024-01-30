import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;

public class PseudoInputWindow extends JFrame {
    //CLASSE POUR NOTRE FENETRE DE RECUPERATION DE PSEUDO
    private JTextField j1Field;
    private JTextField j2Field;
    private boolean IA;

    // Classe interne pour le panneau avec image de fond
    class BackgroundPanel extends JPanel {
        private BufferedImage image;

        public BackgroundPanel() {
            try {
                image = ImageIO.read(new File("image/bgwhite.png"));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (image != null) {
                g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), this);
            }
        }
    }

    public PseudoInputWindow(boolean ia) { //Constructeur
        setTitle("Pseudos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);
        setResizable(false);
        setLocationRelativeTo(null);
        setIconImage(new ImageIcon("image/image.png").getImage());

        // Utilisez BackgroundPanel au lieu de JPanel
        BackgroundPanel mainPanel = new BackgroundPanel();
        mainPanel.setLayout(new GridLayout(3, 2, 10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel j1Label = new JLabel("Joueur Rouge :");
        j1Label.setHorizontalAlignment(JLabel.CENTER);
        j1Field = new JTextField();
        j1Field.setHorizontalAlignment(JLabel.CENTER);
        j1Field.setFont(new Font("Arial", Font.PLAIN, 14));
        mainPanel.add(j1Label);
        mainPanel.add(j1Field);

        if (!ia) { //Si on joue avec l'IA on ne demande pas le pseudo du deuxieme joueur
            JLabel j2Label = new JLabel("Joueur Jaune :");
            j2Label.setHorizontalAlignment(JLabel.CENTER);
            j2Field = new JTextField();
            j2Field.setHorizontalAlignment(JLabel.CENTER);
            j2Field.setFont(new Font("Arial", Font.PLAIN, 14));
            mainPanel.add(j2Label);
            mainPanel.add(j2Field);
        }
        
        IA = ia; 
        // BOUTON VALIDER
        JButton submitButton = new JButton("Valider");
        submitButton.setFont(new Font("Arial", Font.BOLD, 14));
        submitButton.setForeground(Color.WHITE);
        submitButton.setHorizontalAlignment(JLabel.CENTER);
        submitButton.setBackground(new Color(0, 102, 204));
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String j1 = j1Field.getText();
                String j2 = ia ? "IA" : j2Field.getText();

                if (!j1.isEmpty() && !j2.isEmpty()) {
                    openPuissance4GUI(IA, j1, j2);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(PseudoInputWindow.this,
                            "Veuillez remplir tous les champs.");
                }
            }
        });
        mainPanel.add(submitButton);
        add(mainPanel);
    }

    private void openPuissance4GUI(boolean IA, String name1, String name2) { //Bascule sur le jeu avec les paramètres nécéssaires
        this.dispose();
        Puissance4GUI puissance4GUI = new Puissance4GUI(IA, name1, name2);
        puissance4GUI.setVisible(true);
    }
}
