/*
 * Created by JFormDesigner on Tue Jun 23 18:29:34 CST 2020
 */

package UI;

import File.DriveChart;
import org.jfree.chart.ChartPanel;


import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.DecimalFormat;


/**
 * @author Brainrain
 */
public class FileProperties extends JFrame implements ActionListener {
    public FileProperties() {
        this._instance = this;
        this.setTitle("文件夹属性");

        initComponents();
    }

    //文件右击选择属性
    public FileProperties(Icon icon, String name, String size, String url, String CreateTime, String ModifyTime, String AccessTime) {
        this._instance = this;
        this.setTitle("文件属性");
        initComponents();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setResizable(false);

        this.path = url;
        this.icon = icon;
        ImageLabel.setIcon(this.icon);
        this.NameField.setText(name);
        this.Size.setText(size);
        this.locationArea.setText(url);
        this.CreateTime.setText(CreateTime);
        this.ModifyTime.setText(ModifyTime);
        this.AccessTime.setText(AccessTime);
    }

    //文件夹右击选择属性
    public FileProperties(Icon icon, String name, String size, long File_Num, long D_Num, String url, String CreateTime) {
        this._instance = this;
        this.setTitle("文件夹属性");
        DirInitComponents();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setResizable(false);

        this.path = url;
        this.icon = icon;
        ImageLabel.setIcon(this.icon);
        this.NameField.setText(name);
        this.Type.setText("文件夹");
        this.Include.setText(File_Num + "个文件" + D_Num + "个文件夹");
        this.Size.setText(size);
        this.Location.setText(url);
        this.CreateTime.setText(CreateTime);

    }

    //磁盘右击选择属性
    public FileProperties(Icon icon, String name, double Used, double Available) {
        this._instance = this;
        this.setTitle("磁盘属性");
        DiskInitComponents();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setResizable(false);

        this.icon = icon;
        ImageLabel.setIcon(this.icon);
        this.NameField.setText(name);
        this.Type.setText("本地磁盘");
        DecimalFormat fNum = new DecimalFormat("##0.00");
        this.UsedSize.setText(fNum.format(Used) + "GB");
        this.AvaiSize.setText(fNum.format(Available) + "GB");
        this.Total = Used + Available;
        this.TotalSize.setText(fNum.format(Total) + "GB");
        ChartPanel cp = DriveChart.GetChartPanel(Used, Available, "使用情况");
        cp.setBounds(0, 0, 330, 250);
        BotPanel.add(cp);
    }

    private void EnsureButtonActionPerformed(ActionEvent e) {
        // TODO add your code here
        new File(MainForm._instance.Cur_URL + MainForm._instance.list.getSelectedValue() + "/").renameTo(new File(MainForm._instance.Cur_URL + NameField.getText() + "/"));
        MainForm._instance.Go_There();
        dispose();
    }

    private void initComponents() {//文件属性界面初始化
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        HeadPanel = new JPanel();
        NameField = new JTextField();
        ImageLabel = new JLabel();
        BotPanel = new JPanel();
        CreateTimeLabel = new JLabel();
        CreateTime = new JLabel();
        ModifyTimeLabel = new JLabel();
        ModifyTime = new JLabel();
        AccessTimeLabel = new JLabel();
        AccessTime = new JLabel();
        MidPanel = new JPanel();
        LocationLabel = new JLabel();
        SizeLabel = new JLabel();
        Size = new JLabel();
        scrollPane1 = new JScrollPane();
        locationArea = new JTextArea();
        EnsureButton = new JButton();
        Location = new JTextField();

        //======== this ========
        setFont(new Font("\u5fae\u8f6f\u96c5\u9ed1", Font.BOLD, 14));
        Container contentPane = getContentPane();
        contentPane.setLayout(null);

        //======== HeadPanel ========
        {
            HeadPanel.setBorder(new TitledBorder(null, "\u6587\u4ef6\u540d\u79f0", TitledBorder.LEADING, TitledBorder.DEFAULT_POSITION,
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
        HeadPanel.setBounds(15, 10, 325, 85);

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

            //---- ModifyTimeLabel ----
            ModifyTimeLabel.setText("\u4fee\u6539\u65f6\u95f4\uff1a");
            ModifyTimeLabel.setFont(new Font("\u5fae\u8f6f\u96c5\u9ed1", Font.BOLD, 14));
            BotPanel.add(ModifyTimeLabel);
            ModifyTimeLabel.setBounds(30, 65, 70, 17);

            //---- ModifyTime ----
            ModifyTime.setText("text");
            ModifyTime.setFont(new Font("\u5fae\u8f6f\u96c5\u9ed1", Font.BOLD, 14));
            BotPanel.add(ModifyTime);
            ModifyTime.setBounds(105, 65, 165, 15);

            //---- AccessTimeLabel ----
            AccessTimeLabel.setText("\u8bbf\u95ee\u65f6\u95f4\uff1a");
            AccessTimeLabel.setFont(new Font("\u5fae\u8f6f\u96c5\u9ed1", Font.BOLD, 14));
            BotPanel.add(AccessTimeLabel);
            AccessTimeLabel.setBounds(30, 90, 70, 17);

            //---- AccessTime ----
            AccessTime.setText("text");
            AccessTime.setFont(new Font("\u5fae\u8f6f\u96c5\u9ed1", Font.BOLD, 14));
            BotPanel.add(AccessTime);
            AccessTime.setBounds(105, 90, 165, 17);

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
        BotPanel.setBounds(15, 300, 320, 165);

        //======== MidPanel ========
        {
            MidPanel.setBorder(new TitledBorder(null, "\u53c2\u6570", TitledBorder.LEADING, TitledBorder.DEFAULT_POSITION,
                new Font("\u5fae\u8f6f\u96c5\u9ed1", Font.BOLD, 14), Color.black));
            MidPanel.setFont(new Font("\u5fae\u8f6f\u96c5\u9ed1", Font.BOLD, 14));
            MidPanel.setLayout(null);

            //---- LocationLabel ----
            LocationLabel.setText("\u4f4d\u7f6e");
            LocationLabel.setFont(new Font("\u5fae\u8f6f\u96c5\u9ed1", Font.BOLD, 14));
            MidPanel.add(LocationLabel);
            LocationLabel.setBounds(30, 60, 30, 30);

            //---- SizeLabel ----
            SizeLabel.setText("\u5927\u5c0f");
            SizeLabel.setFont(new Font("\u5fae\u8f6f\u96c5\u9ed1", Font.BOLD, 14));
            MidPanel.add(SizeLabel);
            SizeLabel.setBounds(30, 25, 30, 27);

            //---- Size ----
            Size.setText("0KB");
            Size.setFont(new Font("\u5fae\u8f6f\u96c5\u9ed1", Font.BOLD, 14));
            MidPanel.add(Size);
            Size.setBounds(75, 25, 160, 27);

            //======== scrollPane1 ========
            {

                //---- locationArea ----
                locationArea.setText("C:\\\\Windows\\\\System32\\\\");
                locationArea.setFont(new Font("\u5fae\u8f6f\u96c5\u9ed1", Font.BOLD, 14));
                locationArea.setEditable(false);
                scrollPane1.setViewportView(locationArea);
            }
            MidPanel.add(scrollPane1);
            scrollPane1.setBounds(75, 65, 230, 75);

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
        MidPanel.setBounds(15, 110, 320, 165);

        //---- EnsureButton ----
        EnsureButton.setText("\u786e\u5b9a");
        EnsureButton.setFont(new Font("\u5fae\u8f6f\u96c5\u9ed1", Font.BOLD, 14));
        EnsureButton.addActionListener(e -> EnsureButtonActionPerformed(e));
        contentPane.add(EnsureButton);
        EnsureButton.setBounds(new Rectangle(new Point(250, 485), EnsureButton.getPreferredSize()));

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

        //---- Location ----
        Location.setFont(new Font("\u5fae\u8f6f\u96c5\u9ed1", Font.BOLD, 14));
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    private void DirInitComponents() {//文件夹属性界面初始化

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
    }

    private void DiskInitComponents() {//磁盘属性界面初始化
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

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JPanel HeadPanel;
    private JTextField NameField;
    private JLabel ImageLabel;
    private JPanel BotPanel;
    private JLabel CreateTimeLabel;
    private JLabel CreateTime;
    private JLabel ModifyTimeLabel;
    private JLabel ModifyTime;
    private JLabel AccessTimeLabel;
    private JLabel AccessTime;
    private JPanel MidPanel;
    private JLabel LocationLabel;
    private JLabel SizeLabel;
    private JLabel Size;
    private JScrollPane scrollPane1;
    private JTextArea locationArea;
    private JButton EnsureButton;
    private JTextField Location;
    // JFormDesigner - End of variables declaration  //GEN-END:variables

    public static FileProperties _instance = null;
    public Icon icon = null;
    public String path;
    public JLabel TypeLabel, Type, IncludeLabel, Include, FileSysLabel, FileSys, UsedSizeLabel, UsedSize, AvaiSizeLabel, AvaiSize, TotalSizeLabel, TotalSize;
    public double  Total;
}
