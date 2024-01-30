import javax.swing.JButton;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;

public class CircularButton extends JButton {
    //CLASSE POUR AVOIR DES BOUTONS RONDS
    private Color circleColor = Color.WHITE;
    private int col;
    private Puissance4GUI game;
    private Image backgroundImage;

    public Color getCircleColor() { //Getter
        return circleColor;
    }

    @Override
    public Dimension getPreferredSize() { //Setter
        Dimension size = super.getPreferredSize();
        size.width = size.height = Math.max(size.width, size.height);
        return size;
    }

    public void setCircleColor(Color color) { //Setter
        this.circleColor = color;
        this.repaint();
    }

    public void setBackgroundImage(Image image) { //Setter
        this.backgroundImage = image;
        this.repaint();
    }

    public CircularButton(int COL, Puissance4GUI m_game, Player player) { //Constructeur
        ImageIcon backgroundImageIcon = new ImageIcon("image.jpg");
        backgroundImage = backgroundImageIcon.getImage();
        this.setBackgroundImage(backgroundImage);
        col = COL;
        game = m_game;
        setContentAreaFilled(false);
        addMouseListener(new MouseAdapter() {
            @Override
            //HOVER
            public void mouseEntered(MouseEvent evt) { 
                game.ChangeColorCol(col, null);
            }
            @Override
            public void mouseExited(MouseEvent evt) {
                game.ChangeColorCol(col, Color.WHITE);
            }
        });
        
    }

    @Override
    protected void paintComponent(Graphics g) { //Gère les couleurs des jetons
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(circleColor);
        g2d.fillOval(3, 3, getWidth() - 6, getHeight() - 6);
        if (circleColor.equals(Color.BLACK) && backgroundImage != null) {
            g2d.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
        g2d.dispose();
    }
}
