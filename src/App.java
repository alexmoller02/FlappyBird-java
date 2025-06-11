import javax.swing.JFrame;

public class App {
    public static void main(String[] args) {
        int windowWidth = 360;
        int windowHeight = 640;

        JFrame frame = new JFrame("Flappy Bird");
        frame.setSize(windowWidth, windowHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GamePanel gamePanel = new GamePanel();
        frame.add(gamePanel);
        frame.pack();

        gamePanel.requestFocus();
        frame.setVisible(true);
    }
}
