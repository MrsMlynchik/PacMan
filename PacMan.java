import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.Random;

import javax.swing.*;

public class PacMan extends JPanel implements ActionListener, KeyListener {
    private GameState currentState = GameState.MENU;

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
            this.direction = direction;
            char previousDirection = this.direction;
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
    private int boardHight = rows * tileSize;

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

    // the maze
    // X = Wall, O = nothing, P = PacMan
    // Ghosts: b = blue, r = red, o = orange, p = pink
    private String[] tileMap = {
            "XXXXXXXXXXXXXXXXXXX",
            "X        X        X",
            "X XX XXX X XXX XX X",
            "X                 X",
            "X XX X XXXXX X XX X",
            "X    X       X    X",
            "XXXX XXXX XXXX XXXX",
            "OOOX X       X XOOO",
            "XXXX X XXrXX X XXXX",
            "X      XbpoX      X",
            "XXXX X XXXXX X XXXX",
            "OOOX X       X XOOO",
            "XXXX X XXXXX X XXXX",
            "X        X        X",
            "X XX XXX X XXX XX X",
            "X  X     P     X  X",
            "XX X X XXXXX X X XX",
            "X    X   X   X    X",
            "X XXXXXX X XXXXXX X",
            "X                 X",
            "XXXXXXXXXXXXXXXXXXX"
    };

    HashSet<Block> walls;
    HashSet<Block> foods;
    HashSet<Block> ghosts;
    Block pacman;

    char[] directions = { 'U', 'D', 'L', 'R' };
    Random random = new Random();
    int score = 0;
    int lives = 3;
    boolean gameOver = false;
    private Timer gameLoop;

    PacMan() {
        setPreferredSize(new Dimension(boardWidth, boardHight));
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

        loadMap();
        for (Block ghost : ghosts) {
            char newDirection = directions[random.nextInt(4)];
            ghost.updateDirection(newDirection);
        }
        // HOW LONG IT TAKES TO START TIMER, MILLISECONDS GONE BETWEEN FRAMES
        gameLoop = new Timer(40, this); // 25 FPS
        gameLoop.start();
    }

    public void loadMap() {
        walls = new HashSet<Block>();
        foods = new HashSet<Block>();
        ghosts = new HashSet<Block>();

        for (int r = 0; r < rows; r++) {
            for (int col = 0; col < columns; col++) {
                String row = tileMap[r];
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
                    pacman = new Block(x, y, tileSize, tileSize, pacmanRightImage, x, y);
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

        // draw pacman
        g.drawImage(pacman.image, pacman.x, pacman.y, pacman.width, pacman.height, this);

        // draw score
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
            g.setColor(Color.RED);
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

            return;
            // g.drawString("Game over "+ String.valueOf(score), tileSize/2, tileSize/2);
        } else {
            g.drawString("Lives x" + String.valueOf(lives), tileSize / 2, tileSize / 2);
            g.drawString("Score: " + String.valueOf(score), tileSize / 2, tileSize / 2 + 20);

        }
    }

    public void move() {
        pacman.x += pacman.velocityX;
        pacman.y += pacman.velocityY;

        // check for wall collisions
        for (Block wall : walls) {
            if (collision(pacman, wall)) {
                // collision detected, move back to previous position
                pacman.x -= pacman.velocityX;
                pacman.y -= pacman.velocityY;
                break;
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
            ghost.x += ghost.velocityX;
            ghost.y += ghost.velocityY;
            for (Block wall : walls) {
                if (collision(ghost, wall) || ghost.x <= 0 || ghost.x + ghost.width >= boardWidth) {
                    ghost.x -= ghost.velocityX;
                    ghost.y -= ghost.velocityY;
                    char newDirection = directions[random.nextInt(4)];
                    ghost.updateDirection(newDirection);
                }
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

        if (foods.isEmpty()) {
            loadMap();
            resetPositions();
        }
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
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            pacman.updateDirection('U');
            pacman.image = pacmanUpImage;
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            pacman.updateDirection('D');
            pacman.image = pacmanDownImage;
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            pacman.updateDirection('L');
            pacman.image = pacmanLeftImage;
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            pacman.updateDirection('R');
            pacman.image = pacmanRightImage;
        }
    }


}
