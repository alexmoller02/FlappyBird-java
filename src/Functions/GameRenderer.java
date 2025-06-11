package Functions;

import Classes.Particle;
import Classes.Pipe;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class GameRenderer {
    public static void render(Graphics2D g, Image background, GameState state) {
        g.drawImage(background, 0, 0, state.width, state.height, null);
        //g.drawImage(state.bird.img, state.bird.x, state.bird.y, state.bird.width, state.bird.height, null);

        for (Pipe pipe : state.pipes) {
            g.drawImage(pipe.img, pipe.x, pipe.y, pipe.width, pipe.height, null);
        }

        g.setFont(new Font("Arial", Font.BOLD, 32));
        g.setColor(Color.white);

        if (state.gameOver) {
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int boxWidth = 280;
            int boxHeight = 150;
            int boxX = (state.width - boxWidth) / 2;
            int boxY = (state.height - boxHeight) / 2 - 20;

            g.translate(state.width / 2, state.height / 2);
            g.scale(state.stampScale, state.stampScale);
            g.translate(-state.width / 2, -state.height / 2);

            g.setColor(new Color(0, 0, 0, 170));
            g.fillRoundRect(boxX, boxY, boxWidth, boxHeight, 30, 30);

            g.setStroke(new BasicStroke(3));
            g.setColor(Color.white);
            g.drawRoundRect(boxX, boxY, boxWidth, boxHeight, 30, 30);

            g.setFont(new Font("Arial", Font.BOLD, 40));
            g.setColor(Color.red);
            g.drawString("Game Over!", boxX + 45, boxY + 40);

            g.setFont(new Font("Arial", Font.PLAIN, 28));
            g.setColor(Color.white);
            g.drawString("Score: " + (int) state.score, boxX + 80, boxY + 75);

            g.setFont(new Font("Arial", Font.PLAIN, 20));
            g.drawString("Press SPACE to try again.", boxX + 35, boxY + 110);

            g.setTransform(new AffineTransform());
        } else {
            g.drawString(String.valueOf((int) state.score), 10, 35);
        }

        if (state.gamePaused && !state.gameOver) {
            g.setColor(Color.yellow);
            g.setFont(new Font("Arial", Font.BOLD, 40));
            g.drawString("Paused", state.width / 2 - 80, state.height / 2);
        }

        for (Particle p : state.particles) {
            p.draw(g);
        }

        if (state.birdVisible) {
            AffineTransform original = g.getTransform();

            int centerX = state.bird.x + state.bird.width / 2;
            int centerY = state.bird.y + state.bird.height / 2;

            g.translate(centerX, centerY);
            g.rotate(Math.toRadians(state.rotationAngle));
            g.scale(state.birdScaleX, state.birdScaleY);

            // ✅ Use flap sprite briefly when flapping
            Image birdSprite = state.flapTimer > 0 ? state.birdFlapImage : state.birdImage;

            g.drawImage(birdSprite, -state.bird.width / 2, -state.bird.height / 2,
                    state.bird.width, state.bird.height, null);

            g.setTransform(original);
        }
    }
}
