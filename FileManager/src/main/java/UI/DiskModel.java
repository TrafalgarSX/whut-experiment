/*
 * Created by JFormDesigner on Thu Jun 25 23:03:49 CST 2020
 */

package UI;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

/**
 * @author Brainrain
 */
public class DiskModel extends JFrame {
    public DiskModel() {
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
        MidPanel = new JPanel();
        UsedSizeLabel = new JLabel();
        FileSysLabel = new JLabel();
        FileSys = new JLabel();
        TypeLabel = new JLabel();
        Type = new JLabel();
        AvaiSizeLabel = new JLabel();
        AvaiSize = new JLabel();
        TotalSizeLabel = new JLabel();
        TotalSize = new JLabel();
        UsedSize = new JLabel();
        BotPanel = new JPanel();
        EnsureButton = new JButton();

        //======== this ========
        setFont(new Font("\u5fae\u8f6f\u96c5\u9ed1", Font.BOLD, 14));
        Container contentPane = getContentPane();
        contentPane.setLayout(null);

        //======== HeadPanel ========
        {
            HeadPanel.setBorder(new TitledBorder(null, "\u78c1\u76d8\u540d\u79f0", TitledBorder.LEADING, TitledBorder.DEFAULT_POSITION,
                new Font("\u5fae\u8f6f\u96c5\u9ed1", Font.BOLD, 14)));
            HeadPanel.setFont(new Font("\u5fae\u8f6f\u96c5\u9ed1", Font.BOLD, 14));
            HeadPanel.setLayout(null);

            //---- NameField ----
            NameField.setFont(new Font("\u5fae\u8f6f\u96c5\u9ed1", Font.BOLD, 14));
            HeadPanel.add(NameField);
            NameField.setBounds(80, 25, 205, NameField.getPreferredSize().height);

            //---- ImageLabel ----
            ImageLabel.setText("text");
            ImageLabel.setFont(new Font("\u5fae\u8f6f\u96c5\u9ed1", Font.BOLD, 14));
            HeadPanel.add(ImageLabel);
            ImageLabel.setBounds(new Rectangle(new Point(35, 30), ImageLabel.getPreferredSize()));

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
        HeadPanel.setBounds(5, 15, 330, 85);

        //======== MidPanel ========
        {
            MidPanel.setBorder(new TitledBorder(null, "\u5c5e\u6027", TitledBorder.LEADING, TitledBorder.DEFAULT_POSITION,
                new Font("\u5fae\u8f6f\u96c5\u9ed1", Font.BOLD, 14)));
            MidPanel.setFont(new Font("\u5fae\u8f6f\u96c5\u9ed1", Font.BOLD, 14));
            MidPanel.setLayout(null);

            //---- UsedSizeLabel ----
            UsedSizeLabel.setText("\u5df2\u7528\u7a7a\u95f4\uff1a");
            UsedSizeLabel.setFont(new Font("\u5fae\u8f6f\u96c5\u9ed1", Font.BOLD, 14));
            MidPanel.add(UsedSizeLabel);
            UsedSizeLabel.setBounds(25, 80, 75, 30);

            //---- FileSysLabel ----
            FileSysLabel.setText("\u6587\u4ef6\u7cfb\u7edf\uff1a");
            FileSysLabel.setFont(new Font("\u5fae\u8f6f\u96c5\u9ed1", Font.BOLD, 14));
            MidPanel.add(FileSysLabel);
            FileSysLabel.setBounds(25, 50, 70, 27);

            //---- FileSys ----
            FileSys.setText("NTFS");
            FileSys.setFont(new Font("\u5fae\u8f6f\u96c5\u9ed1", Font.BOLD, 14));
            MidPanel.add(FileSys);
            FileSys.setBounds(105, 50, 135, 27);

            //---- TypeLabel ----
            TypeLabel.setText("\u7c7b\u578b\uff1a");
            TypeLabel.setFont(new Font("\u5fae\u8f6f\u96c5\u9ed1", Font.BOLD, 14));
            MidPanel.add(TypeLabel);
            TypeLabel.setBounds(30, 20, 45, TypeLabel.getPreferredSize().height);

            //---- Type ----
            Type.setText("\u672c\u5730\u78c1\u76d8");
            Type.setFont(new Font("\u5fae\u8f6f\u96c5\u9ed1", Font.BOLD, 14));
            MidPanel.add(Type);
            Type.setBounds(80, 20, 80, Type.getPreferredSize().height);

            //---- AvaiSizeLabel ----
            AvaiSizeLabel.setText("\u53ef\u7528\u7a7a\u95f4\uff1a");
            AvaiSizeLabel.setFont(new Font("\u5fae\u8f6f\u96c5\u9ed1", Font.BOLD, 14));
            MidPanel.add(AvaiSizeLabel);
            AvaiSizeLabel.setBounds(25, 125, 80, 22);

            //---- AvaiSize ----
            AvaiSize.setText("text");
            AvaiSize.setFont(new Font("\u5fae\u8f6f\u96c5\u9ed1", Font.BOLD, 14));
            MidPanel.add(AvaiSize);
            AvaiSize.setBounds(105, 120, 190, 30);

            //---- TotalSizeLabel ----
            TotalSizeLabel.setText("\u603b\u5bb9\u91cf\uff1a");
            TotalSizeLabel.setFont(new Font("\u5fae\u8f6f\u96c5\u9ed1", Font.BOLD, 14));
            MidPanel.add(TotalSizeLabel);
            TotalSizeLabel.setBounds(25, 160, 75, TotalSizeLabel.getPreferredSize().height);

            //---- TotalSize ----
            TotalSize.setText("text");
            TotalSize.setFont(new Font("\u5fae\u8f6f\u96c5\u9ed1", Font.BOLD, 14));
            MidPanel.add(TotalSize);
            TotalSize.setBounds(105, 155, 180, 22);

            //---- UsedSize ----
            UsedSize.setText("text");
            UsedSize.setFont(new Font("\u5fae\u8f6f\u96c5\u9ed1", Font.BOLD, 14));
            MidPanel.add(UsedSize);
            UsedSize.setBounds(105, 80, 205, 22);

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
        MidPanel.setBounds(10, 105, 325, 195);

        //======== BotPanel ========
        {
            BotPanel.setBorder(new TitledBorder(null, "\u4f7f\u7528\u60c5\u51b5", TitledBorder.CENTER, TitledBorder.TOP,
                new Font("\u5fae\u8f6f\u96c5\u9ed1", Font.BOLD, 16), Color.black));
            BotPanel.setFont(new Font("\u5fae\u8f6f\u96c5\u9ed1", Font.BOLD, 14));
            BotPanel.setLayout(null);

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
        BotPanel.setBounds(10, 305, 330, 285);

        //---- EnsureButton ----
        EnsureButton.setText("\u786e\u5b9a");
        EnsureButton.setFont(new Font("\u5fae\u8f6f\u96c5\u9ed1", Font.BOLD, 14));
        EnsureButton.addActionListener(e -> EnsureButtonActionPerformed(e));
        contentPane.add(EnsureButton);
        EnsureButton.setBounds(250, 595, 78, 30);

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
    private JPanel MidPanel;
    private JLabel UsedSizeLabel;
    private JLabel FileSysLabel;
    private JLabel FileSys;
    private JLabel TypeLabel;
    private JLabel Type;
    private JLabel AvaiSizeLabel;
    private JLabel AvaiSize;
    private JLabel TotalSizeLabel;
    private JLabel TotalSize;
    private JLabel UsedSize;
    private JPanel BotPanel;
    private JButton EnsureButton;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
