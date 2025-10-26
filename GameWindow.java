import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.*;
import javax.swing.*;

public class GameWindow extends JFrame implements KeyListener {
    public enum GameState {
        MENU, LEVEL_SELECT, MAZE_SELECT, PLAYING, GAME_OVER, YOU_WON
    }

    public int selectedLevel = 1;
    public char selectedMaze = 'A';

    public GameState currentState = GameState.MENU;

    private JPanel menuPanel, levelPanel, mazePanel,gamewonPanel;
    
    public PacMan gamePanel;

    public GameWindow() {
        setTitle("Pac-Man");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(623, 711);
        setLocationRelativeTo(null);
        addKeyListener(this);
        setupMenuPanel();
        setupLevelSelectPanel();
        setupMazeSelectPanel();
        setupGameWonPanel();
        showMenu();
        setVisible(true);
    }

    // Menu
    private void setupMenuPanel() {
        menuPanel = new JPanel();
        menuPanel.setBackground(Color.BLACK);
        menuPanel.setLayout(new GridBagLayout());
        JLabel title = new JLabel("PAC-MAN");
        title.setForeground(Color.YELLOW);
        title.setFont(new Font("Arial", Font.BOLD, 64));

        JLabel hint = new JLabel("Press ENTER to start");
        hint.setForeground(Color.WHITE);
        hint.setFont(new Font("Arial", Font.PLAIN, 24));

        JPanel inner = new JPanel();
        inner.setBackground(Color.BLACK);
        inner.setLayout(new BoxLayout(inner, BoxLayout.Y_AXIS));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        hint.setAlignmentX(Component.CENTER_ALIGNMENT);
        inner.add(title);
        inner.add(Box.createRigidArea(new Dimension(0, 30)));
        inner.add(hint);

        menuPanel.add(inner);
    }

    // Select Level
    private void setupLevelSelectPanel() {
        levelPanel = new JPanel();
        levelPanel.setBackground(Color.BLACK);
        levelPanel.setLayout(new GridLayout(4, 1));
        JLabel label = new JLabel("Select Level:", SwingConstants.CENTER);
        label.setForeground(Color.CYAN);
        label.setFont(new Font("Arial", Font.BOLD, 36));
        levelPanel.add(label);

        JLabel easy = new JLabel("1 - Easy", SwingConstants.CENTER);
        JLabel normal = new JLabel("2 - Normal", SwingConstants.CENTER);
        JLabel hard = new JLabel("3 - Hard", SwingConstants.CENTER);
        easy.setForeground(Color.WHITE);
        normal.setForeground(Color.WHITE);
        hard.setForeground(Color.WHITE);
        easy.setFont(new Font("Arial", Font.BOLD, 36));
        normal.setFont(new Font("Arial", Font.BOLD, 36));
        hard.setFont(new Font("Arial", Font.BOLD, 36));
        levelPanel.add(easy);
        levelPanel.add(normal);
        levelPanel.add(hard);
    }

    //Small preview of maze layout
    public class MazePreviewPanel extends JPanel {
        private String[] maze;
        private int tileSize = 4;

        public MazePreviewPanel(String[] maze){
            this.maze = maze;
            int maxWidth = 0;
            for (String row : maze) {
                if (row.length() > maxWidth) {
                    maxWidth = row.length();
                }
            }
            int width = maxWidth * tileSize;
            int height = maze.length * tileSize;

            setPreferredSize(new Dimension(width, height));
            setBackground(Color.BLACK);
        }

        @Override
        protected void paintComponent(Graphics g){
            super.paintComponent(g);

            int mazeWidth = maze[0].length() * tileSize;
            int mazeHeight = maze.length * tileSize;

            int offsetX = (getWidth() - mazeWidth) / 2;
            int offsetY = (getHeight() - mazeHeight) / 2;

            for (int r = 0; r < maze.length; r++){
                String row = maze[r];
                for (int c = 0; c < row.length(); c++){
                    char ch = row.charAt(c);
                    int x = offsetX + c * tileSize;
                    int y = offsetY + r * tileSize;

                    switch (ch) {
                        case 'X': //walls
                            g.setColor(Color.BLUE);
                            g.fillRect(x, y, tileSize, tileSize);
                            break;
                        case 'P':
                            g.setColor(Color.YELLOW);
                            g.fillOval(x + 1, y + 1, tileSize - 2, tileSize - 2);
                            break;
                        case 'b' : case 'r' : case 'o' : case 'p':
                            g.setColor(Color.ORANGE);
                            g.fillOval(x + 2, y + 2, tileSize - 2, tileSize - 2);
                            break;
                        case 'c':
                            g.setColor(Color.PINK);
                            g.fillOval(x + 3, y + 3, tileSize - 2, tileSize - 2);
                            break;
                        default:
                            g.setColor(Color.BLACK);
                            g.fillRect(x, y, tileSize, tileSize);
                            break;
                    }
                }
            }

        }
    }

    // Select Maze
    private void setupMazeSelectPanel() {
        mazePanel = new JPanel();
        mazePanel.setBackground(Color.BLACK);
        mazePanel.setLayout(new BoxLayout(mazePanel, BoxLayout.Y_AXIS));
        mazePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel label = new JLabel("Select Maze:", SwingConstants.CENTER);
        label.setForeground(Color.ORANGE);
        label.setFont(new Font("Arial", Font.BOLD, 36));
        mazePanel.add(label);

        mazePanel.add(makeMazeOption("A - Classic", Color.WHITE, PacMan.tileMap1));
        mazePanel.add(makeMazeOption("B - Crazy", Color.WHITE, PacMan.tileMap2));
        mazePanel.add(makeMazeOption("C - Wall Madness", Color.WHITE, PacMan.tileMap3));
        mazePanel.add(makeMazeOption("D - Test Maze", Color.WHITE, PacMan.tileMap4));
    }

        private JPanel makeMazeOption(String text, Color color, String[] map) {
        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setBackground(Color.BLACK);
        container.setAlignmentX(Component.CENTER_ALIGNMENT);

        MazePreviewPanel preview = new MazePreviewPanel(map);
        preview.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setForeground(color);
        label.setFont(new Font("Arial", Font.BOLD, 24));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        container.add(preview);
        container.add(Box.createRigidArea(new Dimension(0, 10)));
        container.add(label);
        container.add(Box.createRigidArea(new Dimension(0, 20)));

        return container;
    }


    private void setupGameWonPanel() {
        gamewonPanel = new JPanel();
        gamewonPanel.setBackground(Color.BLACK);

        JPanel centerJPanel = new JPanel();
        centerJPanel.setLayout(new BoxLayout(centerJPanel, BoxLayout.Y_AXIS));
        centerJPanel.setBackground(Color.BLACK);

        JLabel label = new JLabel("Congratulations!", SwingConstants.CENTER);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setForeground(Color.ORANGE);
        label.setFont(new Font("Arial", Font.BOLD, 36));

        JLabel hint = new JLabel("Press ENTER to start");
        hint.setForeground(Color.WHITE);
        hint.setFont(new Font("Arial", Font.PLAIN, 24));
        hint.setAlignmentX(Component.CENTER_ALIGNMENT);

        centerJPanel.add(Box.createVerticalGlue());
        centerJPanel.add(label);
        centerJPanel.add(Box.createVerticalStrut(20));
        centerJPanel.add(hint);
        centerJPanel.add(Box.createVerticalGlue());

        gamewonPanel.setLayout(new BorderLayout());
        gamewonPanel.add(centerJPanel, BorderLayout.CENTER);
    }

    // State
    public void showMenu() {
        getContentPane().removeAll();
        getContentPane().add(menuPanel);
        revalidate();
        repaint();
        currentState = GameState.MENU;
    }

    public void showSelectLevel() {
        getContentPane().removeAll();
        getContentPane().add(levelPanel);
        revalidate();
        repaint();
        currentState = GameState.LEVEL_SELECT;
    }

    public void startGame(int level, char maze) {
        // remove any menu panel
        getContentPane().removeAll();
        // uses PacMan class
        gamePanel = new PacMan(level, maze);
        gamePanel.levelselected = level;
        gamePanel.mazeselected = maze;
        getContentPane().add(gamePanel);
        revalidate();
        repaint();
        currentState = GameState.PLAYING;
    }

    public void showSelectMaze() {
        // TODO Auto-generated method stub
        getContentPane().removeAll();
        getContentPane().add(mazePanel);
        revalidate();
        repaint();
        currentState = GameState.MAZE_SELECT;
    }

    public void showYouWon() {
        getContentPane().removeAll();
        getContentPane().add(gamewonPanel);
        revalidate();
        repaint();
        currentState = GameState.YOU_WON;
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub
        switch (currentState) {
            case MENU:
                if (e.getKeyCode() == KeyEvent.VK_ENTER)
                    showSelectLevel();
                break;
            case LEVEL_SELECT:
                if (e.getKeyCode() == KeyEvent.VK_1) {
                    selectedLevel = 1;
                    showSelectMaze();
                }
                if (e.getKeyCode() == KeyEvent.VK_2) {
                    selectedLevel = 2;
                    showSelectMaze();
                }
                if (e.getKeyCode() == KeyEvent.VK_3) {
                    selectedLevel = 3;
                    showSelectMaze();
                }
                break;
            case MAZE_SELECT:
                if (e.getKeyCode() == KeyEvent.VK_A) {
                    selectedMaze = 'A';
                    startGame(selectedLevel, selectedMaze);
                }
                if (e.getKeyCode() == KeyEvent.VK_B) {
                    selectedMaze = 'B';
                    startGame(selectedLevel, selectedMaze);
                }
                if (e.getKeyCode() == KeyEvent.VK_C) {
                    selectedMaze = 'C';
                    startGame(selectedLevel, selectedMaze);
                }
                if (e.getKeyCode() == KeyEvent.VK_D) {
                    selectedMaze = 'D';
                    startGame(selectedLevel, selectedMaze);
                }
                break;
            case PLAYING:
                if (gamePanel != null)
                    gamePanel.keyReleased(e);
                break;
            case YOU_WON:
                if (e.getKeyCode() == KeyEvent.VK_ENTER)
                    showSelectLevel();
                break;

        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GameWindow());
    }


}
