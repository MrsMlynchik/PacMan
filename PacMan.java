import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;

import javax.swing.*;

public class PacMan extends JPanel {
    // JPanel setup 
    private  int rows = 21;
    private  int columns = 19;
    private  int tileSize = 32;
    private  int boardWidth = columns * tileSize;
    private  int boardHight = rows * tileSize;

    //images
    private Image wallImage;
    private Image blueGhostImage;
    private Image orangeGhostImage;
    private Image redGhostImage;
    private Image pinkGhostImage;

    private Image pacmanUpImage;
    private Image pacmanDownImage;
    private Image pacmanLeftImage;
    private Image pacmanRightImage;

    PacMan(){
        setPreferredSize(new Dimension(boardWidth, boardHight));
        setBackground(Color.BLACK);

        //load images
        wallImage = new ImageIcon(getClass().getResource("/images/wall.png")).getImage();
        blueGhostImage = new ImageIcon(getClass().getResource("/images/blueGhost.png")).getImage();
        orangeGhostImage = new ImageIcon(getClass().getResource("/images/orangeGhost.png")).getImage();
        redGhostImage = new ImageIcon(getClass().getResource("/images/redGhost.png")).getImage();

        pacmanUpImage = new ImageIcon(getClass().getResource("/images/pacmanUp.png")).getImage();
        pacmanDownImage = new ImageIcon(getClass().getResource("/images/pacmanDown.png")).getImage();
        pacmanLeftImage = new ImageIcon(getClass().getResource("/images/pacmanLeft.png")).getImage();
        pacmanRightImage = new ImageIcon(getClass().getResource("/images/pacmanRight.png")).getImage();
    }
}
