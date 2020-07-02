/*
 * Created by JFormDesigner on Thu Jun 25 22:54:23 CST 2020
 */

package UI;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

/**
 * @author Brainrain
 */
public class model extends JFrame {
    public model() {
        initComponents();
    }

    private void EnsureButtonActionPerformed(ActionEvent e) {
        // TODO add your code here
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        HeadPanel = new JPanel();
        NameField = new JTextField();
        ImageLabel = new JLabel();
        BotPanel = new JPanel();
        CreateTimeLabel = new JLabel();
        CreateTime = new JLabel();
        MidPanel = new JPanel();
        Location = new JTextField();
        LocationLabel = new JLabel();
        SizeLabel = new JLabel();
        Size = new JLabel();
        TypeLabel = new JLabel();
        Type = new JLabel();
        IncludeLabel = new JLabel();
        Include = new JLabel();
        EnsureButton = new JButton();

        //======== this ========
        setFont(new Font("\u5fae\u8f6f\u96c5\u9ed1", Font.BOLD, 14));
        Container contentPane = getContentPane();
        contentPane.setLayout(null);

        //======== HeadPanel ========
        {
            HeadPanel.setBorder(new TitledBorder(null, "\u6587\u4ef6\u5939\u540d\u79f0", TitledBorder.LEADING, TitledBorder.DEFAULT_POSITION,
                new Font("\u5fae\u8f6f\u96c5\u9ed1", Font.BOLD, 14)));
            HeadPanel.setFont(new Font("\u5fae\u8f6f\u96c5\u9ed1", Font.BOLD, 14));
            HeadPanel.setLayout(null);

            //---- NameField ----
            NameField.setFont(new Font("\u5fae\u8f6f\u96c5\u9ed1", Font.BOLD, 14));
            HeadPanel.add(NameField);
            NameField.setBounds(75, 35, 205, NameField.getPreferredSize().height);

            //---- ImageLabel ----
            ImageLabel.setText("text");
            ImageLabel.setFont(new Font("\u5fae\u8f6f\u96c5\u9ed1", Font.BOLD, 14));
            HeadPanel.add(ImageLabel);
            ImageLabel.setBounds(new Rectangle(new Point(35, 45), ImageLabel.getPreferredSize()));

            { // compute preferred size
                Dimension preferredSize = new Dimension();
                for(int i = 0; i < HeadPanel.getComponentCount(); i++) {
                    Rectangle bounds = HeadPanel.getComponent(i).getBounds();
                    preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                    preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                }
                Insets insets = HeadPanel.getInsets();
                preferredSize.width += insets.right;
                preferredSize.height += insets.bottom;
                HeadPanel.setMinimumSize(preferredSize);
                HeadPanel.setPreferredSize(preferredSize);
            }
        }
        contentPane.add(HeadPanel);
        HeadPanel.setBounds(10, 10, 360, 100);

        //======== BotPanel ========
        {
            BotPanel.setBorder(new TitledBorder(null, "\u76f8\u5173\u65f6\u95f4", TitledBorder.LEADING, TitledBorder.DEFAULT_POSITION,
                new Font("\u5fae\u8f6f\u96c5\u9ed1", Font.BOLD, 14)));
            BotPanel.setFont(new Font("\u5fae\u8f6f\u96c5\u9ed1", Font.BOLD, 14));
            BotPanel.setLayout(null);

            //---- CreateTimeLabel ----
            CreateTimeLabel.setText("\u521b\u5efa\u65f6\u95f4\uff1a");
            CreateTimeLabel.setFont(new Font("\u5fae\u8f6f\u96c5\u9ed1", Font.BOLD, 14));
            BotPanel.add(CreateTimeLabel);
            CreateTimeLabel.setBounds(new Rectangle(new Point(30, 35), CreateTimeLabel.getPreferredSize()));

            //---- CreateTime ----
            CreateTime.setText("text");
            CreateTime.setFont(new Font("\u5fae\u8f6f\u96c5\u9ed1", Font.BOLD, 14));
            BotPanel.add(CreateTime);
            CreateTime.setBounds(105, 35, 165, 17);

            { // compute preferred size
                Dimension preferredSize = new Dimension();
                for(int i = 0; i < BotPanel.getComponentCount(); i++) {
                    Rectangle bounds = BotPanel.getComponent(i).getBounds();
                    preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                    preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                }
                Insets insets = BotPanel.getInsets();
                preferredSize.width += insets.right;
                preferredSize.height += insets.bottom;
                BotPanel.setMinimumSize(preferredSize);
                BotPanel.setPreferredSize(preferredSize);
            }
        }
        contentPane.add(BotPanel);
        BotPanel.setBounds(10, 350, 360, 100);

        //======== MidPanel ========
        {
            MidPanel.setBorder(new TitledBorder(null, "\u53c2\u6570", TitledBorder.LEADING, TitledBorder.DEFAULT_POSITION,
                new Font("\u5fae\u8f6f\u96c5\u9ed1", Font.BOLD, 14)));
            MidPanel.setFont(new Font("\u5fae\u8f6f\u96c5\u9ed1", Font.BOLD, 14));
            MidPanel.setLayout(null);

            //---- Location ----
            Location.setEditable(false);
            Location.setText("C:\\\\Windows\\\\System32\\\\");
            Location.setFont(new Font("\u5fae\u8f6f\u96c5\u9ed1", Font.BOLD, 14));
            MidPanel.add(Location);
            Location.setBounds(70, 110, 230, Location.getPreferredSize().height);

            //---- LocationLabel ----
            LocationLabel.setText("\u4f4d\u7f6e");
            LocationLabel.setFont(new Font("\u5fae\u8f6f\u96c5\u9ed1", Font.BOLD, 14));
            MidPanel.add(LocationLabel);
            LocationLabel.setBounds(30, 110, 30, 30);

            //---- SizeLabel ----
            SizeLabel.setText("\u5927\u5c0f");
            SizeLabel.setFont(new Font("\u5fae\u8f6f\u96c5\u9ed1", Font.BOLD, 14));
            MidPanel.add(SizeLabel);
            SizeLabel.setBounds(30, 70, 30, 27);

            //---- Size ----
            Size.setText("0KB");
            Size.setFont(new Font("\u5fae\u8f6f\u96c5\u9ed1", Font.BOLD, 14));
            MidPanel.add(Size);
            Size.setBounds(75, 70, 160, 27);

            //---- TypeLabel ----
            TypeLabel.setText("\u7c7b\u578b");
            TypeLabel.setFont(new Font("\u5fae\u8f6f\u96c5\u9ed1", Font.BOLD, 14));
            MidPanel.add(TypeLabel);
            TypeLabel.setBounds(new Rectangle(new Point(30, 35), TypeLabel.getPreferredSize()));

            //---- Type ----
            Type.setText("\u6587\u4ef6\u5939");
            Type.setFont(new Font("\u5fae\u8f6f\u96c5\u9ed1", Font.BOLD, 14));
            MidPanel.add(Type);
            Type.setBounds(75, 35, 80, Type.getPreferredSize().height);

            //---- IncludeLabel ----
            IncludeLabel.setText("\u5305\u542b");
            IncludeLabel.setFont(new Font("\u5fae\u8f6f\u96c5\u9ed1", Font.BOLD, 14));
            MidPanel.add(IncludeLabel);
            IncludeLabel.setBounds(30, 160, 35, 22);

            //---- Include ----
            Include.setText("text");
            Include.setFont(new Font("\u5fae\u8f6f\u96c5\u9ed1", Font.BOLD, 14));
            MidPanel.add(Include);
            Include.setBounds(75, 155, 220, 30);

            { // compute preferred size
                Dimension preferredSize = new Dimension();
                for(int i = 0; i < MidPanel.getComponentCount(); i++) {
                    Rectangle bounds = MidPanel.getComponent(i).getBounds();
                    preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                    preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                }
                Insets insets = MidPanel.getInsets();
                preferredSize.width += insets.right;
                preferredSize.height += insets.bottom;
                MidPanel.setMinimumSize(preferredSize);
                MidPanel.setPreferredSize(preferredSize);
            }
        }
        contentPane.add(MidPanel);
        MidPanel.setBounds(10, 115, 360, 220);

        //---- EnsureButton ----
        EnsureButton.setText("\u786e\u5b9a");
        EnsureButton.setFont(new Font("\u5fae\u8f6f\u96c5\u9ed1", Font.BOLD, 14));
        EnsureButton.addActionListener(e -> EnsureButtonActionPerformed(e));
        contentPane.add(EnsureButton);
        EnsureButton.setBounds(255, 465, 78, 30);

        { // compute preferred size
            Dimension preferredSize = new Dimension();
            for(int i = 0; i < contentPane.getComponentCount(); i++) {
                Rectangle bounds = contentPane.getComponent(i).getBounds();
                preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
            }
            Insets insets = contentPane.getInsets();
            preferredSize.width += insets.right;
            preferredSize.height += insets.bottom;
            contentPane.setMinimumSize(preferredSize);
            contentPane.setPreferredSize(preferredSize);
        }
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JPanel HeadPanel;
    private JTextField NameField;
    private JLabel ImageLabel;
    private JPanel BotPanel;
    private JLabel CreateTimeLabel;
    private JLabel CreateTime;
    private JPanel MidPanel;
    private JTextField Location;
    private JLabel LocationLabel;
    private JLabel SizeLabel;
    private JLabel Size;
    private JLabel TypeLabel;
    private JLabel Type;
    private JLabel IncludeLabel;
    private JLabel Include;
    private JButton EnsureButton;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
