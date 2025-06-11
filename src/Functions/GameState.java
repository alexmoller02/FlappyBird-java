package Functions;

import Classes.Bird;
import Classes.Pipe;
import Classes.Particle;
import Assets.SoundPlayer;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

public class GameState {
    public int width = 360, height = 640;
    public int gravity = 1;
    public int velocityY = 0;
    public int velocityX = -4;
    public int pipeGap = 150; // default gap
    public boolean speedIncreased = false;
    public boolean gapReduced = false;

    public boolean gameOver = false;
    public boolean gamePaused = false;
    public float stampScale = 1f;
    public boolean showStamp = false;
    public double score = 0;

    public Bird bird;
    public boolean birdVisible = true;
    public float rotationAngle = 0f;
    public float birdScaleX = 1f;
    public float birdScaleY = 1f;
    public float bounceTimer = 0f;
    public float flapTimer = 0f;
    public ArrayList<Pipe> pipes;
    public Image birdImage, birdFlapImage, topPipeImage, bottomPipeImage;
    public List<Particle> particles = new ArrayList<>();

    private void spawnParticles(int centerX, int centerY) {
        for (int i = 0; i < 20; i++) {
            particles.add(new Particle(centerX, centerY));
        }
    }

    public GameState(Image birdImage, Image topPipeImage, Image bottomPipeImage) {
        this.birdImage = birdImage;
        this.topPipeImage = topPipeImage;
        this.bottomPipeImage = bottomPipeImage;

        bird = new Bird(width / 8, height / 2, 34, 24, birdImage);
        pipes = new ArrayList<>();
    }

    public void move() {
        if (!gameOver) {
            velocityY += gravity;
            bird.y += velocityY;
            bird.y = Math.max(bird.y, 0);
            rotationAngle = Math.max(-30, Math.min(velocityY * 4, 90));

            for (Pipe pipe : pipes) {
                pipe.x += velocityX;

                if (!pipe.passed && bird.x > pipe.x + pipe.width) {
                    pipe.passed = true;
                    SoundPlayer.play("Point");
                    score += 0.5;
                }

                if (collides(bird, pipe)) {
                    gameOver = true;
                    showStamp = true;
                    stampScale = 0.1f;
                    birdVisible = false;
                    SoundPlayer.play("Death");
                    spawnParticles(bird.x + bird.width / 2, bird.y + bird.height / 2);
                }

                // Difficulty scaling
                if (score >= 10 && !speedIncreased) {
                    velocityX -= 1; // speed up
                    speedIncreased = true;
                }

                if (score >= 20 && !gapReduced) {
                    pipeGap -= 30; // reduce gap
                    gapReduced = true;
                }

                // Animate bird bounce
                if (bounceTimer > 0) {
                    bounceTimer -= 1.0 / 60.0; // assuming 60 FPS
                    float progress = 1 - (bounceTimer / 0.1f); // goes from 0 to 1
                    birdScaleX = 1f + (1.3f - 1f) * (1 - progress);
                    birdScaleY = 1f + (0.7f - 1f) * (1 - progress);
                } else {
                    birdScaleX = 1f;
                    birdScaleY = 1f;
                }
            }

            if (bird.y > height) {
                gameOver = true;
                showStamp = true;
                stampScale = 0.1f;
                SoundPlayer.play("Fall");
            }

        }

        // Always update particles regardless of game state
        for (int i = particles.size() - 1; i >= 0; i--) {
            particles.get(i).update();
            if (particles.get(i).isDead()) {
                particles.remove(i);
            }
        }
    }

    private boolean collides(Bird b, Pipe p) {
        return b.x < p.x + p.width &&
                b.x + b.width > p.x &&
                b.y < p.y + p.height &&
                b.y + b.height > p.y;
    }

    public void reset() {
        bird.y = height / 2;
        velocityY = 0;
        pipes.clear();
        score = 0;
        gameOver = false;
        showStamp = false;
        stampScale = 1f;
        birdVisible = true;
        // Reset difficulty
        velocityX = -4;
        pipeGap = 150;
        speedIncreased = false;
        gapReduced = false;
    }
}
