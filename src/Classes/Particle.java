package Classes;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;

public class Particle {
    public float x, y;
    public float vx, vy;
    public float size;
    public Color color;
    public float life = 1.0f;

    private static final Random rand = new Random();

    public Particle(float x, float y) {
        this.x = x;
        this.y = y;
        this.vx = -2 + rand.nextFloat() * 4;  // Random X velocity
        this.vy = -5 + rand.nextFloat() * 4;  // Random Y velocity
        this.size = 4 + rand.nextFloat() * 6; // Size
        this.color = new Color(200 + rand.nextInt(56), 0, 0); // Red shades
    }

    public void update() {
        x += vx;
        y += vy;
        vy += 0.3f;     // gravity
        life -= 0.03f;  // fade out
    }

    public void draw(Graphics2D g) {
        if (life > 0) {
            g.setColor(color);
            g.fillOval((int) x, (int) y, (int) size, (int) size);
        }
    }

    public boolean isDead() {
        return life <= 0;
    }
}