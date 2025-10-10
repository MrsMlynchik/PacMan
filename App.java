import javax.swing.JFrame;

public class App {
    public static void main(String[] args) {
        // Dimensions of the window
        int rows = 21;
        int columns = 19;
        int tileSize = 32;
        int boardWidth = columns * tileSize;
        int boardHight = rows * tileSize;

        //Set up the window
        JFrame frame = new JFrame("Pac Man");
        frame.setSize(boardWidth, boardHight);
        frame.setResizable(true);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //JPanel
        PacMan pacManGame = new PacMan();
        frame.add(pacManGame);
        frame.pack();
        frame.setVisible(true);
    }
}
