
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;


import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.MouseInputAdapter;

public class MyJDialog extends JDialog {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    public static final boolean noAutoClose = true;
    private Dimension size = new Dimension(100, 70);

    // the option window that won't auto close constructor
    public MyJDialog(boolean doNotAutoClose, JFrame parentJFrame,
                     String message) {
        super(parentJFrame, true);
        this.setUndecorated(true);
        size.width = 280;
        size.height = 140;
        setSize(size);


        // due to the unexpected condition happened in the underground panel,
        // so move the move listeners into the dialog
        MouseInputAdaptert mouseLisener = new MouseInputAdaptert();
        this.addMouseListener(mouseLisener);
        this.addMouseMotionListener(mouseLisener);


        // set the location
        if (parentJFrame != null) {
            this.setLocation(parentJFrame.getLocationOnScreen().x
                            + (parentJFrame.getSize().width / 2) - size.width / 2,
                    parentJFrame.getLocationOnScreen().y
                            + (parentJFrame.getSize().height / 2) - size.height
                            / 2);
        } else {
            this.setLocation(500, 300);
        }


        // the underground panel
        UndergroundPanel mainPanel = new UndergroundPanel();
        mainPanel.setLayout(null);


        // the close button and confirm button
        JButton closeButton = new JButton(new ImageIcon("Images/close1.jpg"));
        closeButton.setRolloverIcon(new ImageIcon("Images/close2.jpg"));
        closeButton.setPressedIcon(new ImageIcon("Images/close3.jpg"));
        closeButton.setFocusable(false);
        closeButton.setBorderPainted(false);
        closeButton.setBounds(this.getBounds().width - 51, 7, 36, 18);
        CloseButtonListener CBListener = new CloseButtonListener();
        closeButton.addActionListener(CBListener);
        mainPanel.add(closeButton);


        JButton confirmButton = new JButton("确定");
        confirmButton.setLocation(size.width / 2 - 50, size.height - 50);
        confirmButton.setFocusable(true);
        confirmButton.requestFocus();
        confirmButton.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    close();
                }
            }
        });
        // confirmButton.setMnemonic(KeyEvent.VK_ENTER);
        confirmButton.addActionListener(CBListener);
        mainPanel.add(confirmButton);


        // The message label.
        // Handle the string according to the length to show the complete
        // message.
        if (message.length() > 10) {
            String message1 = message, message2;
            message1 = message.substring(0, 9);
            message2 = message.substring(9, message.length());
            // show the two message labels
            JLabel messageLabel1 = new JLabel(message1);
            messageLabel1.setForeground(Color.black);
            messageLabel1.setFont(new Font("楷体", Font.BOLD, 18));
            messageLabel1.setBounds(size.width / 2
                            - messageLabel1.getPreferredSize().width / 2, size.height
                            / 2 - messageLabel1.getPreferredSize().height / 2 - 25,
                    messageLabel1.getPreferredSize().width, messageLabel1
                            .getPreferredSize().height);
            mainPanel.add(messageLabel1, BorderLayout.CENTER);


            JLabel messageLabel2 = new JLabel(message2);
            messageLabel2.setForeground(Color.black);
            messageLabel2.setFont(new Font("楷体", Font.BOLD, 18));
            messageLabel2.setBounds(size.width / 2
                            - messageLabel2.getPreferredSize().width / 2, size.height
                            / 2 - messageLabel2.getPreferredSize().height / 2 + 4,
                    messageLabel2.getPreferredSize().width, messageLabel2
                            .getPreferredSize().height);
            mainPanel.add(messageLabel2, BorderLayout.CENTER);


        } else {
            JLabel messageLabel = new JLabel(message);
            messageLabel.setForeground(Color.black);
            messageLabel.setFont(new Font("楷体", Font.BOLD, 18));
            messageLabel.setBounds(size.width / 2
                            - messageLabel.getPreferredSize().width / 2, size.height
                            / 2 - messageLabel.getPreferredSize().height / 2 - 12,
                    messageLabel.getPreferredSize().width, messageLabel
                            .getPreferredSize().height);
            mainPanel.add(messageLabel, BorderLayout.CENTER);
        }


        this.getContentPane().add(mainPanel);


        // set the window transparent
        com.sun.awt.AWTUtilities.setWindowOpaque(this, false);


        setVisible(true);


    }


    // the auto closing option window constructor
    public MyJDialog(JFrame parentJFrame, String message) {
        super(parentJFrame);
        this.setUndecorated(true);
        setSize(size);
        if (parentJFrame != null) {
            this.setLocation(parentJFrame.getLocationOnScreen().x
                            + (parentJFrame.getSize().width / 2) - size.width / 2,
                    parentJFrame.getLocationOnScreen().y
                            + (parentJFrame.getSize().height / 2) - size.height
                            / 2);
        } else {
            this.setLocation(500, 300);
        }
        // the underground panel
        UndergroundPanel mainPanel = new UndergroundPanel();


        // the message label
        JLabel messageLabel = new JLabel(message);
        // messageLabel.setMaximumSize(new Dimension(220,170));
        messageLabel.setForeground(Color.black);
        messageLabel.setFont(new Font("楷体", Font.BOLD, 18));
        messageLabel.setBounds(size.width / 2
                        - messageLabel.getPreferredSize().width / 2, size.height / 2
                        - messageLabel.getPreferredSize().height / 2 - 6, messageLabel
                        .getPreferredSize().width,
                messageLabel.getPreferredSize().height);


        mainPanel.setLayout(null);


        mainPanel.add(messageLabel, BorderLayout.CENTER);


        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);


        // set the window transparent
        com.sun.awt.AWTUtilities.setWindowOpaque(this, false);


        // 渐隐效果显示
        float translucent = 0.01f;
        com.sun.awt.AWTUtilities.setWindowOpacity(this, 0.0f);
        setVisible(true);
        while (translucent < 1) {
            com.sun.awt.AWTUtilities.setWindowOpacity(this, translucent);
            translucent += 0.1f;
        }
        // ........................
        // stop some time for letting user get the message
        try {
            Thread.sleep(800);
        } catch (InterruptedException e) {
            // Auto-generated catch block
            e.printStackTrace();
        }
        close();
    }

    /*
     * public void setVisible(boolean in) {
     *
     *
     *
     * }
     */
    // close the window
    public void close() {
        // 渐隐效果
        float translucent = 1.0f;
        while (translucent > 0) {
            com.sun.awt.AWTUtilities.setWindowOpacity(this, translucent);
            translucent -= 0.001f;
        }
        // ........................
        dispose();


    }


    private class UndergroundPanel extends JPanel {


        private static final long serialVersionUID = 1L;


        // paint the underground
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            int width = getWidth();
            int height = getHeight();
            GradientPaint gradient = new GradientPaint(0.0f, 0.0f, new Color(
                    230, 230, 230, 230), 0.0f, (float) height, new Color(230,
                    230, 230, 230));


            g2.setPaint(gradient);


            // 柔性剪裁
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);


            // draw the background
            g2.fillRoundRect(7, 7, width - 14, height - 14, 20, 20);


            // draw the border lines
            g2.setColor(new Color(2, 10, 170));
            g2.drawRoundRect(6, 6, width - 14, height - 14, 20, 20);
            g2.setColor(new Color(77, 82, 170, 200));
            g2.drawRoundRect(5, 5, width - 12, height - 12, 21, 21);
            g2.setColor(new Color(77, 82, 170, 160));
            g2.drawRoundRect(4, 4, width - 10, height - 10, 22, 22);
            g2.setColor(new Color(77, 82, 170, 120));
            g2.drawRoundRect(3, 3, width - 8, height - 8, 23, 23);
            g2.setColor(new Color(77, 82, 170, 80));
            g2.drawRoundRect(2, 2, width - 6, height - 6, 24, 24);
            g2.setColor(new Color(77, 82, 170, 40));
            g2.drawRoundRect(1, 1, width - 4, height - 4, 25, 25);
            g2.setColor(new Color(77, 82, 170, 10));
            g2.drawRoundRect(0, 0, width - 2, height - 2, 26, 26);


        }


    }


    private class CloseButtonListener implements ActionListener {


        @Override
        public void actionPerformed(ActionEvent arg0) {
            close();


        }


    }


    //listen and implement the moving of the dialog
    private class MouseInputAdaptert extends MouseInputAdapter {
        private Point oldCursorPosition;


        public void mouseDragged(MouseEvent e) {
            Point tempScreen = e.getLocationOnScreen();
            // change the position of the frame
            MyJDialog.this.setLocation(MyJDialog.this.getLocationOnScreen().x
                            + tempScreen.x - oldCursorPosition.x,
                    MyJDialog.this.getLocationOnScreen().y + tempScreen.y
                            - oldCursorPosition.y);
            oldCursorPosition = tempScreen;
        }


        public void mousePressed(MouseEvent e) {
            //record the last cursor position
            oldCursorPosition = e.getLocationOnScreen();
        }


    }


    public static void main(String[] args) {
        MyJDialog test = new MyJDialog(MyJDialog.noAutoClose, null, "ddddddddd");
        MyJDialog test2 = new MyJDialog(null, "clear");

    }
}