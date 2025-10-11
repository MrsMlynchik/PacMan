import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.util.HashSet;

import javax.swing.*;

public class PacMan extends JPanel {
    class Block {
        int x;
        int y;
        int width;
        int height;
        Image image;

        int startX;
        int startY;

        Block(int x, int y, int width, int height, Image image, int startX, int startY){
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.image = image;
            this.startX = x;
            this.startY = y;
        }
    }
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

    // the maze
    // X = Wall, O = nothing, P = PacMan
    //Ghosts: b = blue, r = red, o = orange, p = pink
    private String[] tileMap = {
        "XXXXXXXXXXXXXXXXXXX",
        "X   X         X   X",
        "X X X XXXXXXX X X X",
        "X X             X X",
        "X X XXX XXX XXX X X",
        "X               X X",
        "XXX XXX  X  XXX XXX",
        "OOX   X XXX X   XOO",
        "XXX X X  p  X X XXX",
        "X   X X bro X X   X",
        "XXX X X     X X XXX",
        "OOX   X XXX X   XOO",
        "XXX XXX  X  XXX XXX",
        "X X      P        X",
        "X X XXX XXX XXX X X",
        "X X             X X",
        "X X XXXXXXX XXXXX X",
        "X   X         X   X",
        "XXXXXXXXXXXXXXXXXXX",
    };

    HashSet<Block> walls;
    HashSet<Block> foods;
    HashSet<Block> ghosts;
    Block pacman;

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
