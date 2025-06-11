import javax.swing.*;

import Assets.SoundPlayer;

import java.awt.*;
import java.awt.event.*;

import Functions.AssetLoader;
import Functions.GameRenderer;
import Functions.GameState;
import Functions.PipeSpawner;

public class GamePanel extends JPanel implements ActionListener, KeyListener {
    private Image background;
    private GameState state;

    private Timer gameTimer;
    private Timer pipeSpawner;

    public GamePanel() {
        // Load assets
        background = AssetLoader.load("/Sprites/flappybirdbg.png");
        Image birdImage = AssetLoader.load("/Sprites/flappybird.png");
        Image topPipeImage = AssetLoader.load("/Sprites/toppipe.png");
        Image bottomPipeImage = AssetLoader.load("/Sprites/bottompipe.png");

        SoundPlayer.preload();

        // Initialize game state
        state = new GameState(birdImage, topPipeImage, bottomPipeImage);

        // Panel setup
        setPreferredSize(new Dimension(state.width, state.height));
        setFocusable(true);
        addKeyListener(this);

        // Timers
        gameTimer = new Timer(1000 / 60, this);
        pipeSpawner = new Timer(1500, e -> PipeSpawner.spawnPipes(state));

        gameTimer.start();
        pipeSpawner.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        state.move(); // Always move (includes particle animation)

        if (state.gameOver && state.showStamp && state.stampScale < 1f) {
            state.stampScale += 0.15f;
            if (state.stampScale > 1f)
                state.stampScale = 1f;
        }

        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        GameRenderer.render((Graphics2D) g, background, state);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            state.velocityY = -9;
            state.birdScaleX = 1.3f;
            state.birdScaleY = 0.7f;
            state.bounceTimer = 0.1f;
            // Play random whoosh sound
            SoundPlayer.playRandomWhoosh();

            if (state.gameOver) {
                SoundPlayer.stop("Fall");
                SoundPlayer.stop("Death");
                state.reset();
                gameTimer.start();
                pipeSpawner.start();
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            if (!state.gamePaused) {
                gameTimer.stop();
                pipeSpawner.stop();
            } else {
                gameTimer.start();
                pipeSpawner.start();
            }
            state.gamePaused = !state.gamePaused;
            repaint();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}