import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.plaf.*;
/*
 * Created by JFormDesigner on Tue Jun 30 23:28:00 CST 2020
 */



/**
 * @author Brainrain
 */
public class test extends JFrame {
    public test() {
        initComponents();
    }

    private void AllFilesActionPerformed(ActionEvent e) {
        // TODO add your code here
    }

    private void PictureActionPerformed(ActionEvent e) {
        // TODO add your code here
    }

    private void VideoActionPerformed(ActionEvent e) {
        // TODO add your code here
    }

    private void MusicActionPerformed(ActionEvent e) {
        // TODO add your code here
    }

    private void TextActionPerformed(ActionEvent e) {
        // TODO add your code here
    }

    private void SortListActionPerformed(ActionEvent e) {
        // TODO add your code here
    }

    private void SortTypeActionPerformed(ActionEvent e) {
        // TODO add your code here
    }

    private void SearchTextActionPerformed(ActionEvent e) {
        // TODO add your code here
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        frame1 = new JFrame();
        searchPanel = new JPanel();
        AllFiles = new JRadioButton();
        Picture = new JRadioButton();
        Video = new JRadioButton();
        Music = new JRadioButton();
        Text = new JRadioButton();
        SortTxt = new JLabel();
         String Sort_Items[] = {"文件大小","创建时间","首字母"};
        SortList = new JComboBox(Sort_Items);
        String Sort_Type_Items[] = {"升序","降序"};
        SortType = new JComboBox(Sort_Type_Items);
        SearchTxt = new JLabel();
        SearchText = new JTextField();
        SearchType = new JLabel();
        DirCheck = new JCheckBox();
        FileCheck = new JCheckBox();
        panel14 = new JPanel();
        button4 = new JButton();
        button5 = new JButton();
        panel15 = new JPanel();
        button7 = new JButton();
        button6 = new JButton();

        //======== frame1 ========
        {
            frame1.setVisible(true);
            Container frame1ContentPane = frame1.getContentPane();
            frame1ContentPane.setLayout(new BorderLayout());

            //======== searchPanel ========
            {
                searchPanel.setPreferredSize(new Dimension(1200, 30));
                searchPanel.setLayout(new GridBagLayout());
                ((GridBagLayout)searchPanel.getLayout()).columnWidths = new int[] {0, 0, 0, 0, 71, 0, 138, 0, 0, 0, 0, 88, 0, 76, 80, 0};
                ((GridBagLayout)searchPanel.getLayout()).rowHeights = new int[] {0, 0};
                ((GridBagLayout)searchPanel.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};
                ((GridBagLayout)searchPanel.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

                //---- AllFiles ----
                AllFiles.setText("\u6240\u6709\u6587\u4ef6");
                AllFiles.setSelected(true);
                AllFiles.addActionListener(e -> AllFilesActionPerformed(e));
                searchPanel.add(AllFiles, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 5), 0, 0));

                //---- Picture ----
                Picture.setText("\u56fe\u7247");
                Picture.addActionListener(e -> PictureActionPerformed(e));
                searchPanel.add(Picture, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 5), 0, 0));

                //---- Video ----
                Video.setText("\u89c6\u9891");
                Video.addActionListener(e -> VideoActionPerformed(e));
                searchPanel.add(Video, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 5), 0, 0));

                //---- Music ----
                Music.setText("\u97f3\u4e50");
                Music.addActionListener(e -> MusicActionPerformed(e));
                searchPanel.add(Music, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 5), 0, 0));

                //---- Text ----
                Text.setText("\u6587\u6863");
                Text.addActionListener(e -> TextActionPerformed(e));
                searchPanel.add(Text, new GridBagConstraints(4, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 5), 0, 0));

                //---- SortTxt ----
                SortTxt.setText("     \u6392\u5e8f");
                searchPanel.add(SortTxt, new GridBagConstraints(5, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 5), 0, 0));

                //---- SortList ----
                SortList.setSelectedIndex(2);
                SortList.addActionListener(e -> SortListActionPerformed(e));
                searchPanel.add(SortList, new GridBagConstraints(6, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 5), 0, 0));

                //---- SortType ----
                SortType.addActionListener(e -> SortTypeActionPerformed(e));
                searchPanel.add(SortType, new GridBagConstraints(7, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 5), 0, 0));

                //---- SearchTxt ----
                SearchTxt.setText("  \u641c\u7d22");
                searchPanel.add(SearchTxt, new GridBagConstraints(8, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 5), 0, 0));

                //---- SearchText ----
                SearchText.addActionListener(e -> SearchTextActionPerformed(e));
                searchPanel.add(SearchText, new GridBagConstraints(9, 0, 3, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 5), 0, 0));

                //---- SearchType ----
                SearchType.setText("  \u641c\u7d22\u7c7b\u578b");
                searchPanel.add(SearchType, new GridBagConstraints(12, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 5), 0, 0));

                //---- DirCheck ----
                DirCheck.setText("\u76ee\u5f55");
                DirCheck.setSelected(true);
                searchPanel.add(DirCheck, new GridBagConstraints(13, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 5), 0, 0));

                //---- FileCheck ----
                FileCheck.setText("\u6587\u4ef6");
                FileCheck.setSelected(true);
                searchPanel.add(FileCheck, new GridBagConstraints(14, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));
            }
            frame1ContentPane.add(searchPanel, BorderLayout.NORTH);

            //======== panel14 ========
            {
                panel14.setLayout(new BorderLayout());

                //---- button4 ----
                button4.setText("text");
                panel14.add(button4, BorderLayout.WEST);

                //---- button5 ----
                button5.setText("text");
                panel14.add(button5, BorderLayout.EAST);

                //======== panel15 ========
                {
                    panel15.setLayout(new GridLayout(1, 2));

                    //---- button7 ----
                    button7.setText("text");
                    panel15.add(button7);

                    //---- button6 ----
                    button6.setText("text");
                    panel15.add(button6);
                }
                panel14.add(panel15, BorderLayout.CENTER);
            }
            frame1ContentPane.add(panel14, BorderLayout.CENTER);
            frame1.setSize(1010, 640);
            frame1.setLocationRelativeTo(frame1.getOwner());
        }

        //---- Classify ----
        ButtonGroup Classify = new ButtonGroup();
        Classify.add(AllFiles);
        Classify.add(Picture);
        Classify.add(Video);
        Classify.add(Music);
        Classify.add(Text);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JFrame frame1;
    private JPanel searchPanel;
    private JRadioButton AllFiles;
    private JRadioButton Picture;
    private JRadioButton Video;
    private JRadioButton Music;
    private JRadioButton Text;
    private JLabel SortTxt;
    public JComboBox SortList;
    public JComboBox SortType;
    private JLabel SearchTxt;
    private JTextField SearchText;
    private JLabel SearchType;
    private JCheckBox DirCheck;
    private JCheckBox FileCheck;
    private JPanel panel14;
    private JButton button4;
    private JButton button5;
    private JPanel panel15;
    private JButton button7;
    private JButton button6;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
    public static void main(String[] args) {
        test m=new test();
    }
}
