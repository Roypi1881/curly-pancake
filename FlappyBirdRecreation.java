import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class FlappyBirdRecreation extends JPanel implements ActionListener, KeyListener {
    int birdY = 250, birdV = 0, gravity = 1, flap = -12, score = 0;
    boolean alive = true;
    ArrayList<int[]> pipes = new ArrayList<>();
    Timer timer = new Timer(20, this);

    public FlappyBirdRecreation() {
        JFrame f = new JFrame("Flappy Bird Recreation");
        f.setSize(400,600);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(this);
        f.setVisible(true);
        f.addKeyListener(this);
        setBackground(Color.cyan);
        timer.start();
        pipes.add(new int[]{400, randGap()});
    }
    int randGap() { return 150 + new Random().nextInt(200); }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.orange); g.fillRect(0,550,400,50);
        g.setColor(Color.green); g.fillRect(0,540,400,10);
        g.setColor(Color.red); g.fillOval(100,birdY,40,40);
        for (int[] p : pipes) {
            g.setColor(Color.green.darker());
            g.fillRect(p[0], 0, 60, p[1]-80);
            g.fillRect(p[0], p[1]+80, 60, 600-p[1]-80);
        }
        g.setColor(Color.black); g.drawString("Score: "+score, 10, 20);
        if (!alive)
            g.drawString("Game Over! (Press Space)", 120, 300);
    }

    public void actionPerformed(ActionEvent e) {
        if (alive) {
            birdV += gravity; birdY += birdV;
            if (pipes.get(pipes.size()-1)[0] < 220)
                pipes.add(new int[]{400, randGap()});
            for (int[] p : pipes) p[0] -= 4;
            if (pipes.get(0)[0] < -60) { pipes.remove(0); score++; }
            for (int[] p : pipes)
                if (100+40>p[0]&&100<p[0]+60 && (birdY<p[1]-80||birdY+40>p[1]+80)) alive = false;
            if (birdY<0||birdY+40>550) alive=false;
        }
        repaint();
    }
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode()==KeyEvent.VK_SPACE) {
            if (!alive) { birdY=250; birdV=0; pipes.clear(); pipes.add(new int[]{400,randGap()}); score=0; alive=true; }
            else birdV = flap;
        }
    }
    public void keyReleased(KeyEvent e) {}
    public void keyTyped(KeyEvent e) {}

    public static void main(String[] args) { new FlappyBirdRecreation(); }
}