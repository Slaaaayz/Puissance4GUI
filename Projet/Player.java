import java.awt.Color;

public class Player {
    //CLASS DE NOTRE JOUEUR
    private final String name;
    private Color color;

    public Player(String m_name,Color m_color) { //Constructeur
        color = m_color;
        name = m_name;
    }

    public Color getColor() { //Getter
        return color;
    }

    public String getName() { //Getter
        return name;
    }
}