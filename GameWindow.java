import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.*;
import javax.swing.*;

public class GameWindow extends JFrame implements KeyListener {
    private enum GameEnum {
        MENU, LEVEL_SELECT, MAZE_SELECT, PLAYING, GAME_OVER
    }
    private GameState currentState = GameState.MENU;

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
        setupMenuPanel();
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

    //State
    private void showMenu() {
        getContentPane().removeAll();
        getContentPane().add(menuPanel);
        revalidate();
        repaint();
        currentState = GameState.MENU;
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
