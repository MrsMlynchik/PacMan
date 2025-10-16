import java.awt.event.*;
import javax.swing.*;

public class GameWindow extends JFrame implements KeyListener {
    private enum GameEnum {
        MENU, LEVEL_SELECT, MAZE_SELECT, PLAYING, GAME_OVER
    }

    //private GameState currentState = GameState.MENU;

    private JPanel menuPanel, levelPanel, mazePanel;
    private PacMan gamePanel;

    public GameWindow() {
        setTitle("Pac-Man");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(640, 720);
        setLocationRelativeTo(null);
        addKeyListener(this);
        setVisible(true);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'keyTyped'");
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'keyPressed'");
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'keyReleased'");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GameWindow());
    }
}
