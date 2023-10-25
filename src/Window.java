import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class Window extends JFrame implements ActionListener {
    private JPanel panel1 = new JPanel();
    private JPanel panel2 = new JPanel();

    private ArrayList<Timer> list = new ArrayList<>();

    JLabel label;

    private int speed = 4;

    private Timer timer1;
    private Timer timer3;

    double score = 0;



    public Window() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        panel1.setLayout(null);
        panel1.setBackground(Color.YELLOW);
        label = new JLabel("Current score: " + score + "%");
        label.repaint();
        panel2.setBorder(BorderFactory.createTitledBorder("Score"));
        panel2.add(label);
        setTitle("Game");
        add(panel1, BorderLayout.CENTER);
        add(panel2, BorderLayout.SOUTH);
        timer1 = new Timer(400, this);
        timer1.start();
        setSize(400, 450);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
        timer3 = new Timer(60000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    timer3.stop();
                    timer1.stop();
                    for (var el : list) {
                        el.stop();
                    }
                    JOptionPane.showMessageDialog(null, "You lost.");
                    System.exit(0);
            }
        });
        timer3.start();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JPanel jPanel = new JPanel();
        int size = 35;
        int x = (int) (Math.random() * (getWidth() - size));
        int[] y = {0};
        jPanel.setBackground(Color.BLUE);
        jPanel.setBounds(x, y[0], size, size);
        jPanel.setVisible(true);
        panel1.add(jPanel);
        Timer currentTimer = new Timer(20, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                y[0] += speed;
                jPanel.setLocation(x, y[0]);
                panel1.repaint();
                stop(y, jPanel, (Timer) e.getSource());
            }
        });
        ClickListener clickListener = new ClickListener(currentTimer);
        jPanel.addMouseListener(clickListener);
        list.add(currentTimer);
        currentTimer.start();

    }

    public void stop(int [] arrgs, JPanel jPanel, Timer timer){
        if (arrgs[0] >= panel1.getHeight()){
            panel1.remove(jPanel);
            list.remove(timer);
            timer.stop();
            panel1.revalidate();
            panel1.repaint();
        }
    }



     class ClickListener extends MouseAdapter {

        Timer timer;

        public ClickListener(Timer timer) {
            this.timer = timer;
        }

        @Override
        public void mousePressed(MouseEvent e) {
            JPanel square = (JPanel) e.getSource();
            Container parent = square.getParent();
            parent.remove(square);
            score +=10;
            if(score >= 100){
                label.setText("Current score: " + (int)score + "%");
                timer1.stop();
                timer3.stop();
                for (var el : list){
                    el.stop();
                }
                JOptionPane.showMessageDialog(null, "You won!");
                System.exit(0);
            }
            list.remove(timer);
            label.setText("Current score: " + (int)score + "%");
            parent.revalidate();
            parent.repaint();
            timer.stop();
        }
    }
}
