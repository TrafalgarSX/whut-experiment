package UI;

import sun.applet.Main;

import javax.swing.*;
import java.awt.*;

public class popMessage extends JFrame {
    private Dimension size = new Dimension(100, 70);
    public popMessage(JFrame parentJFrame,String message){
        //super(parentJFrame);
        this.setUndecorated(true);
        setSize(size);
        if (parentJFrame != null) {
         /*   this.setLocation(parentJFrame.getLocationOnScreen().x
                            + (parentJFrame.getSize().width / 2) - size.width / 2,
                    parentJFrame.getLocationOnScreen().y
                            + (parentJFrame.getSize().height / 2) - size.height
                            / 2);*/
            //this.setLocation();
            //this.setLocationRelativeTo(MainForm._instance.clearSelect);


        } else {
            int mouseX=MouseInfo.getPointerInfo().getLocation().x;
            int mouseY=MouseInfo.getPointerInfo().getLocation().y;
            //System.out.println(x+" "+y);
            this.setLocation(mouseX,mouseY);

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
            Thread.sleep(400);
        } catch (InterruptedException e) {
            // Auto-generated catch block
            e.printStackTrace();
        }
        close();
    }

    // close the window
    public void close() {
        // 渐隐效果
        float translucent = 1.0f;
        while (translucent > 0) {
            com.sun.awt.AWTUtilities.setWindowOpacity(this, translucent);
            translucent -= 0.002f;
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
}
