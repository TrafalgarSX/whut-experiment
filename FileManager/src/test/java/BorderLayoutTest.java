import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class BorderLayoutTest
{
    public static void main(String[] args)
    {
        JFrame frame = new JFrame("xxx");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocation(400, 200);

        frame.setLayout(new BorderLayout());

        JPanel left = new JPanel();
        left.setPreferredSize(new Dimension(300, 320));
        JPanel right = new JPanel();
        right.setPreferredSize(new Dimension(200, 320));

        left.setLayout(new BorderLayout());
        JPanel left_1 = new JPanel();
        left_1.setPreferredSize(new Dimension(300, 300));
        left_1.setBackground(Color.RED);

        JPanel left_2 = new JPanel();
        left_2.setPreferredSize(new Dimension(300, 100));
        left_2.setBackground(Color.GREEN);

        left.add(left_1, BorderLayout.CENTER);
        left.add(left_2, BorderLayout.SOUTH);

        right.setLayout(new BorderLayout());
        JPanel right_1 = new JPanel();
        right_1.setBackground(Color.WHITE);
        right_1.setPreferredSize(new Dimension(200, 100));

        JPanel right_2 = new JPanel();
        right_2.setBackground(Color.BLUE);
        right_2.setPreferredSize(new Dimension(200, 100));

        JPanel right_3 = new JPanel();
        right_3.setBackground(Color.BLACK);
        right_3.setPreferredSize(new Dimension(200, 120));

        right.add(right_1, BorderLayout.NORTH);
        right.add(right_2, BorderLayout.CENTER);
        right.add(right_3, BorderLayout.SOUTH);

        frame.add(left, BorderLayout.CENTER);
        frame.add(right, BorderLayout.EAST);

        frame.setVisible(true);
        frame.pack();
    }
}