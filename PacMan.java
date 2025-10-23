import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.awt.Rectangle;

import javax.swing.*;

public class PacMan extends JPanel implements ActionListener, KeyListener {
    
    public int levelselected;
    public char mazeselected;

    class Block {
        int x;
        int y;
        int width;
        int height;
        Image image;

        int startX;
        int startY;
        char direction = 'U';
        int velocityX = 0;
        int velocityY = 0;

        boolean isPortal = false;
        char portalId;
        Color portalColor;

        Block(int x, int y, int width, int height, Image image, int startX, int startY) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.image = image;

            this.startX = x;
            this.startY = y;
        }

        void updateDirection(char direction) {
            char previousDirection = this.direction;
            this.direction = direction;
            updateVelocity();
            this.x += this.velocityX;
            this.y += this.velocityY;
            for (Block wall : walls) {
                if (collision(this, wall)) {
                    // collision detected, move back to previous position
                    this.x -= this.velocityX;
                    this.y -= this.velocityY;
                    this.direction = previousDirection;
                    updateVelocity();
                }
            }
        }

        void updateVelocity() {
            
            if(levelselected==1){
                if (this.direction == 'U') {
                    this.velocityX = 0;
                    this.velocityY = -4;
                } else if (this.direction == 'D') {
                    this.velocityX = 0;
                    this.velocityY = 4;
                } else if (this.direction == 'L') {
                    this.velocityX = -4;
                    this.velocityY = 0;
                } else if (this.direction == 'R') {
                    this.velocityX = 4;
                    this.velocityY = 0;
                }


            } else if(levelselected == 2) {
                if (this.direction == 'U') {
                    this.velocityX = 0;
                    this.velocityY = -4*2;
                } else if (this.direction == 'D') {
                    this.velocityX = 0;
                    this.velocityY = 4*2 ;
                } else if (this.direction == 'L') {
                    this.velocityX = -4*2 ;
                    this.velocityY = 0;
                } else if (this.direction == 'R') {
                    this.velocityX = 4*2 ;
                    this.velocityY = 0;
                }
            }
            else{
                if (this.direction == 'U') {
                    this.velocityX = 0;
                    this.velocityY = -4*(levelselected+1) ;
                } else if (this.direction == 'D') {
                    this.velocityX = 0;
                    this.velocityY = 4*(levelselected+1)  ;
                } else if (this.direction == 'L') {
                    this.velocityX = -4 *(levelselected+1) ;
                    this.velocityY = 0;
                } else if (this.direction == 'R') {
                    this.velocityX = 4*(levelselected+1)  ;
                    this.velocityY = 0;
                }
            }
            
        }

        void reset() {
            this.x = this.startX;
            this.y = this.startY;
        }
    }

    // JPanel setup
    private int rows = 21;
    private int columns = 19;
    private int tileSize = 32;
    private int boardWidth = columns * tileSize;
    private int boardHeight = rows * tileSize;

    // images
    private Image wallImage;
    private Image blueGhostImage;
    private Image orangeGhostImage;
    private Image redGhostImage;
    private Image pinkGhostImage;

    private Image pacmanUpImage;
    private Image pacmanDownImage;
    private Image pacmanLeftImage;
    private Image pacmanRightImage;
    private Image skullImage;
    private Image cherryImage;

    // the maze
    // X = Wall, O = nothing, P = PacMan
    // Ghosts: b = blue, r = red, o = orange, p = pink
    private String[] tileMap1 = {
            "XXXXXXXXXXXXXXXXXXX",
            "X        X       4X",
            "X XX XXX X XXX XX X",
            "X                 X",
            "X XX X XXXXX X XX X",
            "X   1X       X    X",
            "XXXX XXXX XXXX XXXX",
            "OOOX X       X XOOO",
            "XXXX X XXrXX X XXXX",
            "O       bpo       O",
            "XXXX X XXXXX X XXXX",
            "OOOX X       X XOOO",
            "XXXX X XXXXX X XXXX",
            "X        X        X",
            "X XX XXX X XXX XX X",
            "X  X     P     X  X",
            "XX X X XXXXX X X XX",
            "X    X   X   X   2X",
            "X XXXXXX X XXXXXX X",
            "X3                X",
            "XXXXXXXXXXXXXXXXXXX"       
            };
            // Maze B - Crazy
    private String[] tileMap2={
            "XXXXXXXXXXXXXXXXXXX",
            "X   X     X    3X X",
            "X X X XXX X XXX X X",
            "X X X   X X   X X X",
            "X X XXX X XXX X X X",
            "X X     X p   X X X",
            "X XXX XXXXXXX XXX X",
            "X2  X   X X   X   X",
            "XXX X X X X X X XXX",
            "   b           o   ",
            "XXX X X X X X X XXX",
            "X   X   X X   X   X",
            "X XXX XXXXXXX XXX X",
            "X X     X     X X X",
            "X X XXX X XXX X X X",
            "X X X4  X X   X X X",
            "X X X XXX XXX X X X",
            "X X X         X X X",
            "X   X   P     X   X",
            "X          r     1X",
            "XXXXXXXXXXXXXXXXXXX"
            };
            // Maze C – Wall madness
    private String[] tileMap3={
            "XXXXXXXXXXXXXXXXXXX",
            "X2       X  o    3X",
            "X XXXX X X XXXX X X",
            "X X r  X X    X X X",
            "X X XX X X XX X X X",
            "X X X      4X X X X",
            "X X X XXX X X X X X",
            "X X X X   X X X XcX",
            "X X X X   X X X X X",
            "X X X XXXXX X X X X",
            "X X X   b   X X X X",
            "XpX X XXXXX X X X X",
            "X X X X   X X X X X",
            "X X X X XXX X X X X",
            "X X X       X X X X",
            "X X XX X X XX X X X",
            "X X    X X    X X X",
            "X XXXX X X XXXX X X",
            "X        P    o   X",
            "Xc               1X",
            "XXXXXXXXXXXXXXXXXXX"
            };   
        // Maze D - Test Maze
    private String[] tileMap4 = {
        "XXXXXXXXXXXXXXX",
        "X1         X  X",
        "XX   P     X  X",
        "X          X  X",
        "X             X",
        "X  X        XXX",
        "X  X         2X",
        "XXXXXXXXXXXXXXX",
    };
    

    HashSet<Block> walls;
    HashSet<Block> foods;
    HashSet<Block> ghosts;
    HashSet<Block> portals;
    HashSet<Block> cherries;
    Block pacman;

    // portal pairing map: maps a portal char '1'->'2', '2'->'1', '3'->'4', '4'->'3'
    Map<Character, Character> portalPairMap = new HashMap<>();
    // quick map portal id -> Block (assumes one portal tile per id)
    Map<Character, Block> portalById = new HashMap<>();

    char[] directions = { 'U', 'D', 'L', 'R' };
    Random random = new Random();
    int score = 0;
    int lives = 3;
    boolean gameOver = false;
    private Timer gameLoop;

    // teleport cooldown (frames) to avoid immediate bounce-back
    int teleportCooldown = 0;

    //the last user-requested direction that couldn't be applied immediately
    char nextDirection = '\0';

    //how many pixels to forgive when we are checking the space
    final int TURN_TOLERANCE = 2;

    //constructor
    public PacMan(int level,char maze){
        this.levelselected = level;
        this.mazeselected = maze;

        setPreferredSize(new Dimension(boardWidth, boardHeight));
        setBackground(Color.BLACK);
        addKeyListener(this);
        setFocusable(true);

        // load images
        wallImage = new ImageIcon(getClass().getResource("/images/wall.png")).getImage();
        blueGhostImage = new ImageIcon(getClass().getResource("/images/blueGhost.png")).getImage();
        orangeGhostImage = new ImageIcon(getClass().getResource("/images/orangeGhost.png")).getImage();
        redGhostImage = new ImageIcon(getClass().getResource("/images/redGhost.png")).getImage();
        pinkGhostImage = new ImageIcon(getClass().getResource("/images/pinkGhost.png")).getImage();
        pacmanUpImage = new ImageIcon(getClass().getResource("/images/pacmanUp.png")).getImage();
        pacmanDownImage = new ImageIcon(getClass().getResource("/images/pacmanDown.png")).getImage();
        pacmanLeftImage = new ImageIcon(getClass().getResource("/images/pacmanLeft.png")).getImage();
        pacmanRightImage = new ImageIcon(getClass().getResource("/images/pacmanRight.png")).getImage();
        skullImage = new ImageIcon(getClass().getResource("/images/skull.png")).getImage();
        cherryImage = new ImageIcon(getClass().getResource("/images/cherry.png")).getImage();


        // prepare portal pairing
        portalPairMap.put('1','2');
        portalPairMap.put('2','1');
        portalPairMap.put('3','4');
        portalPairMap.put('4','3');

        loadMap();
        for (Block ghost : ghosts) {
            char newDirection = directions[random.nextInt(4)];
            ghost.updateDirection(newDirection);
        }
        // HOW LONG IT TAKES TO START TIMER, MILLISECONDS GONE BETWEEN FRAMES
        gameLoop = new Timer(60, this); // 25 FPS
        gameLoop.start();
    }

    public void loadMap() {
        walls = new HashSet<Block>();
        foods = new HashSet<Block>();
        ghosts = new HashSet<Block>();
        portals = new HashSet<Block>();
        cherries = new HashSet<Block>();
        String[] maze={};

        if(mazeselected=='A'){
            maze=tileMap1;
        }else if(mazeselected=='B'){
            maze=tileMap2;
        }else if (mazeselected=='C'){
            maze=tileMap3;
        } else if (mazeselected == 'D'){
            maze = tileMap4;
        }

        for (int r = 0; r < maze.length; r++) {
            String row = maze[r];
            for (int col = 0; col < row.length(); col++) {
                char tileCharAtPos = row.charAt(col);

                int x = col * tileSize;
                int y = r * tileSize;

                if (tileCharAtPos == 'X') {
                    Block wall = new Block(x, y, tileSize, tileSize, wallImage, x, y);
                    walls.add(wall);
                } else if (tileCharAtPos == ' ') {
                    Block food = new Block(x + 14, y + 14, 4, 4, null, x, y);
                    foods.add(food);
                } else if (tileCharAtPos == 'P') {
                    pacman = new Block(x+4, y+4, tileSize-8, tileSize-8, pacmanRightImage, x, y);
                } else if (tileCharAtPos == 'b') {
                    Block blueGhost = new Block(x, y, tileSize, tileSize, blueGhostImage, x, y);
                    ghosts.add(blueGhost);
                } else if (tileCharAtPos == 'r') {
                    Block redGhost = new Block(x, y, tileSize, tileSize, redGhostImage, x, y);
                    ghosts.add(redGhost);
                } else if (tileCharAtPos == 'o') {
                    Block orangeGhost = new Block(x, y, tileSize, tileSize, orangeGhostImage, x, y);
                    ghosts.add(orangeGhost);
                } else if (tileCharAtPos == 'p') {
                    Block pinkGhost = new Block(x, y, tileSize, tileSize, pinkGhostImage, x, y);
                    ghosts.add(pinkGhost);
                }else if (tileCharAtPos == 'c') {
                    Block cherry = new Block(x, y, tileSize, tileSize, cherryImage, x, y);
                    cherries.add(cherry);
                } else if (Character.isDigit(tileCharAtPos)) {
                    Block portal = new Block(x, y, tileSize, tileSize, null, x, y);
                    portal.isPortal = true;
                    portal.portalId = tileCharAtPos;

                    // color for debugging/visuals
                    switch (tileCharAtPos) {
                        case '1': portal.portalColor = Color.CYAN; break;
                        case '2': portal.portalColor = Color.CYAN; break;
                        case '3': portal.portalColor = Color.GREEN; break;
                        case '4': portal.portalColor = Color.GREEN; break;
                        default:  portal.portalColor = Color.WHITE; break;
                    }

                    // portal collision uses whole tile
                    portal.width = tileSize;
                    portal.height = tileSize;

                    portals.add(portal);
                    // store mapping id -> block (assumes exactly one tile per id on your maps)
                    portalById.put(tileCharAtPos, portal);
                }
            }
        }
        

}

    public void paintComponent(java.awt.Graphics g) {
        super.paintComponent(g);

        // draw walls
        for (Block wall : walls) {
            g.drawImage(wall.image, wall.x, wall.y, wall.width, wall.height, this);
        }

        // draw food
        g.setColor(Color.WHITE);
        for (Block food : foods) {
            g.fillRect(food.x, food.y, food.width, food.height);
        }

        // draw ghosts
        for (Block ghost : ghosts) {
            g.drawImage(ghost.image, ghost.x, ghost.y, ghost.width, ghost.height, this);
        }

        // draw cherries
        for (Block cherry : cherries) {
            g.drawImage(cherry.image, cherry.x, cherry.y, cherry.width, cherry.height, this);
        }

        // draw portals as colored tiles
        if (portals != null) {
            for (Block portal : portals) {
                g.setColor(portal.portalColor);
                g.fillRect(portal.x, portal.y, portal.width, portal.height);
                // border for clarity
                g.setColor(Color.BLACK);
                g.drawRect(portal.x, portal.y, portal.width, portal.height);
            }
        }


        // draw pacman
        g.drawImage(pacman.image, pacman.x, pacman.y, pacman.width, pacman.height, this);

        // draw score
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 18));
        if (gameOver) {
            // fil entire panel with blue?
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, getWidth(), getHeight());

            // Draw the skull image centered
            int imgWidth = 200;
            int imgHeight = 200;
            int x = (getWidth() - imgWidth) / 2;
            int y = (getHeight() - imgHeight) / 2 - 150;
            g.drawImage(skullImage, x, y, imgWidth, imgHeight, this);

            // Print "Game Over" message in big white letters
            g.setColor(Color.LIGHT_GRAY);
            g.setFont(new Font("Arial", Font.BOLD, 72));

            String message = "GAME OVER";
            // Center text horizontally and vertically
            int stringWidth = g.getFontMetrics().stringWidth(message);
            int stringHeight = g.getFontMetrics().getHeight();
            int textX = (getWidth() - stringWidth) / 2;
            int textY = (getHeight() + stringHeight) / 2 - 20;

            g.drawString(message, textX, textY);

            // show score below
            g.setFont(new Font("Arial", Font.BOLD, 36));
            String scoreText = "Score: " + score;
            int scoreWidth = g.getFontMetrics().stringWidth(scoreText);
            g.drawString(scoreText, (getWidth() - scoreWidth) / 2, textY + 50);

            //hint
            g.setFont(new Font("Arial", Font.PLAIN, 24));
            String restartHint = "Press any key to restart";
            int hintWidth = g.getFontMetrics().stringWidth(restartHint);
            g.drawString(restartHint, (getWidth() - hintWidth) / 2, textY + 100);

            return;
        } else {
            g.drawString("Lives x" + String.valueOf(lives), tileSize / 2, tileSize / 2);
            g.drawString("Score: " + String.valueOf(score), tileSize / 2, tileSize / 2 + 20);

        }
    }

    public void move() {
        // decrement teleport cooldown if active
        if (teleportCooldown > 0) teleportCooldown--;

        if (nextDirection != '\0') {
        // if near center OR current direction is blocked, try the new one
        if (nearTileCenter(pacman, TURN_TOLERANCE) || !canMove(pacman, pacman.direction)) {
            if (canMove(pacman, nextDirection)) {
                //calculate the center of the current tile
                int currentTileX = (pacman.x + pacman.width/2) / tileSize;
                int currentTileY = (pacman.y + pacman.height/2) / tileSize;

                int centerX = currentTileX * tileSize + tileSize/2;
                int centerY = currentTileY * tileSize + tileSize/2;
                
                //snap the opposite axis to the center of the tile path
                if (nextDirection == 'L' || nextDirection == 'R'){
                    pacman.y = centerY - pacman.height/2;
                }else{
                    pacman.x = centerX - pacman.width/2;
                } 
                pacman.updateDirection(nextDirection);
                nextDirection = '\0';
            }
        }
    }

    // collision
    int nextX = pacman.x + pacman.velocityX;
    int nextY = pacman.y + pacman.velocityY;
    boolean blocked = false;

    for (Block wall : walls) {
        if (new Rectangle(nextX, nextY, pacman.width, pacman.height)
                .intersects(new Rectangle(wall.x, wall.y, wall.width, wall.height))) {
            blocked = true;
            break;
        }
    }

    if (!blocked) {
        pacman.x = nextX;
        pacman.y = nextY;
    } else {
        pacman.velocityX = 0;
        pacman.velocityY = 0;
        // snap only along the axis of movement
        int centerX = ((pacman.x + pacman.width/2) / tileSize) * tileSize + tileSize/2;
        int centerY = ((pacman.y + pacman.height/2) / tileSize) * tileSize + tileSize/2;
        if (pacman.direction == 'L' || pacman.direction == 'R')
            pacman.x = centerX - pacman.width/2;
        else
            pacman.y = centerY - pacman.height/2;
    }

    if (pacman.x < -pacman.width/2) pacman.x = boardWidth - pacman.width;
    else if (pacman.x + pacman.width > boardWidth + pacman.width/2) pacman.x = 0;

        //Teleport portals (when it is the end of the screen)
        if (levelselected == 1 || levelselected == 2 || levelselected == 3){
            if (pacman.x < -pacman.width/2){
                pacman.x = boardWidth - pacman.width;
            } else if (pacman.x + pacman.width > boardWidth + pacman.width / 2){
                pacman.x = 0;
            }
        }

        // Portal teleport: deterministic pair mapping (1<->2, 3<->4).
        // Only active when cooldown == 0
        if (!gameOver && portals != null && pacman != null && teleportCooldown == 0) {
            for (Block portal : portals) {
                if (collision(pacman, portal)) {
                    char id = portal.portalId;
                    Character pairedId = portalPairMap.get(id);
                    if (pairedId != null) {
                        Block target = portalById.get(pairedId);
                        if (target != null) {
                            // Teleport Pac-Man to center of target tile
                            pacman.x = target.x + (target.width - pacman.width)/2;
                            pacman.y = target.y + (target.height - pacman.height)/2;

                            // Set direction to same as before or reset velocity
                            pacman.velocityX = 0;
                            pacman.velocityY = 0;

                            // set short cooldown so Pac-Man won't immediately re-enter the portal
                            teleportCooldown = 10;
                            break;
                        }
                    }
                }
            }
        }


        // check ghost collision
        for (Block ghost : ghosts) {
            if (collision(ghost, pacman)) {
                lives -= 1;
                if (lives == 0) {
                    gameOver = true;
                    return;
                }
                resetPositions();
            }
            if (ghost.y == tileSize * 9 && ghost.direction != 'U' && ghost.direction != 'D') {
                ghost.updateDirection('U');
            }
            int newGhostX = ghost.x + ghost.velocityX;
            int newGhostY = ghost.y + ghost.velocityY;
            boolean ghostBlocked = false;

            //check for wall collision at the next position
            for (Block wall : walls) {
                if (new Rectangle(newGhostX, newGhostY, ghost.width, ghost.height)
                .intersects(new Rectangle(wall.x, wall.y, wall.width, wall.height))){
                    ghostBlocked = true;
                    break;
                }
            }
            //check for board bounce collision (ghosts don't use the tunel)
            if (newGhostX < 0 || newGhostX + ghost.width > boardWidth || newGhostY < 0 || newGhostY + ghost.height > boardHeight){
                ghostBlocked = true;
            }

            if (ghostBlocked){
                int centerTileX = ((ghost.x + ghost.width/2) / tileSize) * tileSize + tileSize/2;
                int centerTileY = ((ghost.y + ghost.height/2) / tileSize)* tileSize + tileSize/2;
                ghost.x = centerTileX - ghost.width/2;
                ghost.y = centerTileY - ghost.height/2;

                char newDirection = directions[random.nextInt(4)];
                ghost.updateDirection(newDirection);
            } else {
                //move them to the calculated positions
                ghost.x = newGhostX;
                ghost.y = newGhostY;
            }
        }

        Block eatenFood = null;
        for (Block food : foods) {
            if (collision(pacman, food)) {
                eatenFood = food;
                score += 10;
                break;
            }
        }
        foods.remove(eatenFood);
        Block eatenCherries = null;
        for (Block cherry:cherries){
            if (collision(pacman,cherry)){
                eatenCherries = cherry;
                score+=200;
                break;
            }
        }
        cherries.remove(eatenCherries);

        if (foods.isEmpty()&&cherries.isEmpty()) {
            loadMap();
            resetPositions();
        }
    }

    //Can move function to test whether PacMan can move one step in a given direction
        private boolean canMove(Block b, char dir){
            if (b == null || walls == null) return false;
            int step = Math.max(1, Math.min(4, tileSize / 4)); 

            int testX = b.x;
            int testY = b.y;

            if (dir == 'U') testY -= step;
            if (dir == 'D') testY += step;
            if (dir == 'L') testX -= step;
            if (dir == 'R') testX += step;

            //a tolerant rectangle (don't let tolerance make width negative)
            int w = Math.max(1, b.width - 2 * TURN_TOLERANCE);
            int h = Math.max(1, b.height - 2 * TURN_TOLERANCE);
            Rectangle nextRect = new Rectangle(testX + TURN_TOLERANCE, testY + TURN_TOLERANCE, w, h);

            for (Block wall : walls) {
                Rectangle wallRect = new Rectangle(wall.x, wall.y, wall.width, wall.height);
                if (nextRect.intersects(wallRect)) return false;
            }
            return true;
                }
            private boolean nearTileCenter(Block b, int tolerance) {
                int centerX = b.x + b.width / 2;
                int centerY = b.y + b.height / 2;
                int tileCenterX = ((centerX / tileSize) * tileSize) + tileSize / 2;
                int tileCenterY = ((centerY / tileSize) * tileSize) + tileSize / 2;
                return (Math.abs(centerX - tileCenterX) <= tolerance) && (Math.abs(centerY - tileCenterY) <= tolerance);
            }

    public void resetPositions() {
        pacman.reset();
        pacman.velocityX = 0;
        pacman.velocityY = 0;
        for (Block ghost : ghosts) {
            ghost.reset();
            char newDirection = directions[random.nextInt(4)];
            ghost.updateDirection(newDirection);
        }
    }

    // checks if two blocks are colliding
    public boolean collision(Block a, Block b) {
        return (a.x < b.x + b.width &&
                a.x + a.width > b.x &&
                a.y < b.y + b.height &&
                a.y + a.height > b.y);
    }

    //restart game after losing 
        public void restartGame() {
        score = 0;
        lives = 3;
        gameOver = false;
        loadMap();
        resetPositions();
        gameLoop.start();
        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
        if (gameOver) {
            gameLoop.stop();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (gameOver){
            restartGame();
            return;
        }

        char desired = '\0';
        Image desiredImg = null;

    if (e.getKeyCode() == KeyEvent.VK_UP) {
        desired = 'U';
        desiredImg = pacmanUpImage;
    } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
        desired = 'D';
        desiredImg = pacmanDownImage;
    } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
        desired = 'L';
        desiredImg = pacmanLeftImage;
    } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
        desired = 'R';
        desiredImg = pacmanRightImage;
    }

    if (desired != '\0') {
        // Update sprite immediately
        pacman.image = desiredImg;

        // If can move right now, do it immediately
        if (canMove(pacman, desired) && nearTileCenter(pacman, TURN_TOLERANCE)) {
            pacman.updateDirection(desired);
            nextDirection = '\0';
        } else {
            // Otherwise, queue it
            nextDirection = desired;
        }
    }

    }
}
