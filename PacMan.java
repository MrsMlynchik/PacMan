import java.awt.Color;
import java.awt.Dimension;

import javax.swing.*;

public class PacMan extends JPanel {
    // JPanel setup 
    private  int rows = 21;
    private  int columns = 19;
    private  int tileSize = 32;
    private  int boardWidth = columns * tileSize;
    private  int boardHight = rows * tileSize;

    PacMan(){
        setPreferredSize(new Dimension(boardWidth, boardHight));
        setBackground(Color.BLACK);
    }
}
