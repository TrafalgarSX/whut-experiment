/*
 * Created by JFormDesigner on Mon Jun 22 23:46:22 CST 2020
 */

package UI;

import java.awt.event.*;
import File.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.filechooser.FileSystemView;
import javax.swing.plaf.FontUIResource;


/**
 * @author Brainrain
 */
public class MainForm extends JFrame implements ActionListener {
    public MainForm() {
        this._instance=this;
        UIManager.put("OptionPane.buttonFont", new FontUIResource(new Font(
                "΢���ź�", Font.BOLD, 16)));
        UIManager.put("OptionPane.messageFont", new FontUIResource(new Font(
                "΢���ź�", Font.BOLD, 16)));
        UIManager.put("TextField.font", new FontUIResource(new Font(
                "΢���ź�", Font.BOLD, 16)));
        //InternalFrame.titleFont
        UIManager.put("InternalFrame.titleFont", new FontUIResource(new Font(
                "΢���ź�", Font.BOLD, 16)));
        initComponents();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }

    private void AllFilesActionPerformed(ActionEvent e) {
        // TODO add your code here
        //������������л�����ʾ�����ļ�����ص���Ŀ¼
        isSearching=false;
        Home_List();
    }
    public void SearchFunction(){
        //���ѡ�����������ܣ����ǵ��������ǰĿ¼������������͵��ļ���������Ŀ¼��
        isSearching=true;
        Maps.clear();
        defaultListModel.clear();
        Icon_Counter=0;
        AllIcons=new Icon[999999];
        GetAllResults(Cur_URL);//��������Ŀ¼
        list.setModel(defaultListModel);
        list.setCellRenderer(new MyCellRenderer(AllIcons));
    }
    private void PictureActionPerformed(ActionEvent e) {
        // TODO add your code here
        SearchFunction();
    }

    private void VideoActionPerformed(ActionEvent e) {
        // TODO add your code here
        SearchFunction();
    }

    private void MusicActionPerformed(ActionEvent e) {
        // TODO add your code here
        SearchFunction();
    }

    private void TextActionPerformed(ActionEvent e) {
        // TODO add your code here
        SearchFunction();
    }

    private void SearchTextActionPerformed(ActionEvent e) {
        // TODO add your code here
        //����������󰴻س����������¼�
        boolean flag_Dir=false,flag_File=false;
        if (FileCheck.isSelected()) {
            //�����ļ���
            flag_File=true;
        }
        if (DirCheck.isSelected()) {
            //�����ļ���
            flag_Dir=true;
        }
        if(!(flag_File||flag_Dir)){
            JOptionPane.showMessageDialog(null, "������ѡ��һ���������", "ȷ�϶Ի���", JOptionPane.YES_OPTION);
        }
        else{
            //��ʼ����
            isSearching=true;
            Maps.clear();
            defaultListModel.clear();
            Icon_Counter=0;
            AllIcons = new Icon[999999];
            FileSearch.bfsSearchFile(Cur_URL, SearchText.getText(), flag_Dir, flag_File);
            list.setModel(defaultListModel);
            list.setCellRenderer(new MyCellRenderer(AllIcons));
        }
    }



    private void PreBtnActionPerformed(ActionEvent e) {
        // TODO add your code here
        //����
        LatURL=Cur_URL;
        if (!stack.isEmpty()) {
            stack.pop();//ÿ�ӵ�ǰһ��Ŀ¼����֮ǰ��Ŀ¼ʱ��stack��Ҫ��ջ
            stack_return.push(Cur_URL);//����֮ǰ��Ŀ¼���뷵��ջ
            if (!stack.isEmpty()) {
                Cur_URL=stack.peek();//��ջ�еõ���һ�����ʵ�Ŀ¼��������ǰĿ¼
            }
            else{
                Cur_URL="";//���ջΪ�գ�˵��ǰ���Ǹ�Ŀ¼
            }
            Go_There();
        }
        if (isSearching) {
            //�����������״̬���Ǵ�ʱӦ�ý���
            isSearching=false;
            AllFiles.setSelected(true);
        }
    }

    private void LatBtnActionPerformed(ActionEvent e) {
        // TODO add your code here
        //����
        if (!stack_return.isEmpty()) {
            Cur_URL=stack_return.peek();
            stack_return.pop();
            stack.push(Cur_URL);
            Go_There();
        }
        if (isSearching) {
            isSearching=false;
            AllFiles.setSelected(true);
        }
    }

    private void GoBtnActionPerformed(ActionEvent e) {
        // TODO add your code here
        String url=GuideText.getText();
        if (url.length() > 0) {
            File file = new File(url);
            if (file.exists()) {
                stack.push(Cur_URL);
                Cur_URL=url;
                Go_There();
            }
            else{
                JOptionPane.showConfirmDialog(null, "û���ҵ���Ŀ¼", "ȷ�϶Ի���", JOptionPane.YES_OPTION);
            }
        }
        else{
            Home_List();
        }
    }

    private void GuideTextActionPerformed(ActionEvent e) {
        // TODO add your code here
        String url=GuideText.getText();
        if (url.length() > 0) {
            File file = new File(url);
            if (file.exists()) {
                stack.push(Cur_URL);
                Cur_URL=url;
                Go_There();
            }
            else{
                JOptionPane.showConfirmDialog(null, "û���ҵ���Ŀ¼", "ȷ�϶Ի���", JOptionPane.YES_OPTION);
            }
        }
        else{
            Home_List();
        }
    }

    private void createUIComponents() {
        // TODO: add custom component creation code here
    }

    private void openItemActionPerformed(ActionEvent e) {
        // TODO add your code here
        if (!isSearching) {
            String fileName=list.getSelectedValue();
            twoClick(fileName);
        }
        else{
            File file = new File(Maps.get(list.getSelectedValue()));
            OpenIt(file);
        }
    }

    private void renameItemActionPerformed(ActionEvent e) {
        // TODO add your code here
        String before=list.getSelectedValue();
        File file = new File(Cur_URL + before + "/"); //�޸���
        String after="";

        if (file.isDirectory()) {
            after=(String)JOptionPane.showInputDialog(null, "���������ļ�����:\n", "������", JOptionPane.PLAIN_MESSAGE, null, null,
                    list.getSelectedValue());
        }else{
            after=(String) JOptionPane.showInputDialog(null, "���������ļ���:\n", "������", JOptionPane.PLAIN_MESSAGE, null, null,
                    before);
        }
        if (before != after && after != null) {
            file.renameTo(new File(Cur_URL + after + "/"));//�޸���
            Go_There();
        }
        else{
            Go_There();
        }
    }

    private void deleteItemActionPerformed(ActionEvent e) {
        // TODO add your code here
        File file = new File(Cur_URL + "/" + list.getSelectedValue());  //�޸���
        int n;
        if (file.isFile()) {
            n = JOptionPane.showConfirmDialog(null, "ȷ��Ҫɾ���ļ� " + file.getName() + " ô?", "�ļ�ɾ��", JOptionPane.YES_NO_OPTION);
        }
        else{
            n = JOptionPane.showConfirmDialog(null, "ȷ��Ҫɾ�� " + file.getName() + " ����Ŀ¼�µ��ļ�ô?", "�ļ���ɾ��", JOptionPane.YES_NO_OPTION);

        }
        if(n==0){
            FileDelete.delete(Cur_URL + list.getSelectedValue() + "/");  //�޸���
            Go_There();
        }
    }

    private void propertiesItemActionPerformed(ActionEvent e) {
        // TODO add your code here
        //���ļ�/�ļ������Դ���
        String temp_url=Cur_URL+list.getSelectedValue()+"/";//�޸���
        File file = new File(temp_url);
        Icon icon = GetFileIcon.getSingleSmallIcon(temp_url);
        String fileName=list.getSelectedValue();
        long size;
        double final_size;
        long File_Num=0,Directory_Num=0;
        int flag=0;
        String file_size="";

        String CreateTime = FileTime.getFileCreateTime(temp_url);
        String ModifyTime = FileTime.getModifiedTime(temp_url);
        String LastAccess = FileTime.getLatesAccessTime(temp_url);

        if (file.isDirectory()) {
            //Ŀ¼���Գ�ʼ���������
            DirectoryInfo Dinfo=new DirectoryInfo();
            size = Dinfo.getDirSize(file);
            File_Num=Dinfo.FileNum;
            Directory_Num=Dinfo.DirectoryNum;
            flag=1;
        }else {//�ļ����Գ�ʼ���������
            size=file.length();
        }
        final_size=0;
        for (int i = 0; i < Sizes.length; ++i) {
            if (size / Sizes[i] > 0) {
                final_size = size * 1.0 / Sizes[i];
                DecimalFormat fnum = new DecimalFormat("##0.00");
                file_size = fnum.format(final_size) + Size_Names[i];
                break;
            }
        }
        if (flag == 1) {//�ļ�������
            FileProperties properties = new FileProperties(icon, fileName, file_size, File_Num, Directory_Num - 1, temp_url, CreateTime);
        }
        else{//�ļ�����
            FileProperties properties=new FileProperties(icon,fileName,file_size,temp_url,CreateTime,ModifyTime,LastAccess);
        }
    }

    private void DiskPropertiesItemActionPerformed(ActionEvent e) {
        // TODO add your code here
        //�������Բ鿴
        String temp_url = list.getSelectedValue() + "/";//�޸���
        Icon icon = GetFileIcon.getSingleSmallIcon(temp_url);
        File file = new File(temp_url);
        FileSystemView fileSys = FileSystemView.getFileSystemView();
        String name = fileSys.getSystemDisplayName(file);
        double Available = file.getFreeSpace() * 1.0 / Sizes[0];
        double Used=file.getTotalSpace()*1.0/Sizes[0]-Available;
        FileProperties properties = new FileProperties(icon, name, Used, Available);

    }
    private void createDirItemActionPerformed(ActionEvent e) {
        // TODO add your code here

        String newFolder="";
        newFolder=(String)JOptionPane.showInputDialog(null, "�������´����ļ�����:\n", "�����ļ���", JOptionPane.PLAIN_MESSAGE, null, null,"�½��ļ���");


        if (FileCreate.create(Cur_URL+newFolder,"�ļ���")) {//��������ɹ�
            Cur_URL+=newFolder+"/";   //�޸���
            stack.push(Cur_URL);
            Go_There();
        }

    }

    private void createTxtItemActionPerformed(ActionEvent e) {
        // TODO add your code here
        String newFile="";
        newFile=(String)JOptionPane.showInputDialog(null, "�������´����ļ���:\n", "�����ļ�", JOptionPane.PLAIN_MESSAGE, null, null,"�½��ļ�");
        if (FileCreate.create(Cur_URL+newFile,"�ļ�")) {//��������ɹ�
            Go_There();
        }
    }


    private void diskOpenItemActionPerformed(ActionEvent e) {
        // TODO add your code here
        if (!isSearching) {
            String fileName=list.getSelectedValue();
            twoClick(fileName);
        }
        else{
            File file = new File(Maps.get(list.getSelectedValue()));
            OpenIt(file);
        }
    }

    private void deleteActionPerformed(ActionEvent e) {
        // TODO add your code here
        //��ѡ�µ�ɾ��
        List<String> selectedStr=list.getSelectedValuesList();
        File file;
        int num=selectedStr.size();
        int n = JOptionPane.showConfirmDialog(null, "ȷ��Ҫɾ�� " + selectedStr.get(0) + " ��" + num + "��ô?", "�ļ�ɾ��",JOptionPane.YES_NO_OPTION);
        if (n == 0) {
            if (isSearching) {//�����������  Cur_URL������ ������Ŀ¼�е��ļ����֣�Cur_URL + selectedStr.get(i)���˸���Ŀ¼��ɾ����
                for (int i = 0; i < selectedStr.size(); ++i) {
                    file = new File(Maps.get(selectedStr.get(i)));
                    FileDelete.delete(file.getAbsolutePath());
                }
            }
            else{
                for (int i = 0; i < selectedStr.size(); ++i) {
                    FileDelete.delete(Cur_URL + selectedStr.get(i) + "/");//�޸���

                }
                Go_There();
            }
        }
    }

    private void listMouseClicked(MouseEvent e) {
        // TODO add your code here
        //����·��հײ�ѡ��
        if (list.locationToIndex(e.getPoint()) == -1 && !e.isShiftDown()
                && !isMenuShortcutKeyDown(e)) {
            list.clearSelection();
        }
        //������˫�����Ҽ��¼�����
        if (list.getSelectedIndex() != -1) {
            if (e.getClickCount() == 1) {
                //����Listʱ�������¼�
            } else if (e.getClickCount() == 2) {
                System.out.println(list.getSelectedValue());
                twoClick(list.getSelectedValue());
            }

            if (e.getButton() == 3) {
                //�һ�listʱ���򿪲˵���
                if (Cur_URL != "") {
                    //popupMenu4.show(scrollShow, e.getX(), e.getY());

                    if (list.getSelectedValuesList().size() == 1) {
                        ////����һ����ǵ����ļ��к��ļ�����Ӧ��һ��������ȫ�Ĳ˵���
                        popupMenu1.show(list, e.getX(), e.getY());
                    } else if (list.getSelectedValuesList().size() > 1) {
                        //���ѡ�ж���ļ��к��ļ�����ֻ֧��ɾ������
                        popupMenu3.show(list,e.getX(),e.getY());
                    }
                }
                else if(Cur_URL==""&& list.getSelectedValuesList().size()==1){
                    popupMenu2.show(list, e.getX(), e.getY());//����һ����Ǵ��̣��˵�����ֻ���С��򿪡��͡����ԡ�����
                }

            }
        }else {
            if(e.getButton()==3) {
                popupMenu4.show(list, e.getX(), e.getY());
            }
        }
    }
    private boolean isMenuShortcutKeyDown(InputEvent event) {
        return (event.getModifiers() & Toolkit.getDefaultToolkit()
                .getMenuShortcutKeyMask()) != 0;
    }
    private void twoClick(String choice) {
        //�����ʱ��������״̬�����������ĵ������
        if (!isSearching) {
            choice += "/";  //�޸���
            File file = new File(Cur_URL + choice);
            if (file.isDirectory()) {
                Cur_URL+=choice;
                stack.push(Cur_URL);
                Go_There();
            }
            else{
                OpenIt(file);
            }
        }
        else{
            //����״̬���Ǿ�Ҫ��map����ȡ���ǵ�URL����Ϊ������˳�򶼴����ˣ��޷���һ��URL��Ӧ
            File file = new File(Maps.get(choice));
            OpenIt(file);
        }
    }

    private void scrollShowMouseClicked(MouseEvent e) {
        // TODO add your code here
        int n = JOptionPane.showConfirmDialog(null, "ȷ��Ҫɾ������ô?", "�ļ�ɾ��",JOptionPane.YES_NO_OPTION);
        //popupMenu4.show(scrollShow, e.getX(), e.getY());

    }

    private void listFocusLost(FocusEvent e) {
        // TODO add your code here
        list.clearSelection();
    }

    private void clearSelectActionPerformed(ActionEvent e) {
        // TODO add your code here
        list.clearSelection();
        popMessage test2 = new popMessage(null, "clear");

    }
    private void clearSelectMouseClicked(MouseEvent e) {
        // TODO add your code here

        popMessage clearMessage = new popMessage(null, "clear");
       // clearMessage.setLocation(e.getPoint());
        //.setLocationRelativeTo(clearSelect);
    }
    private void SortListActionPerformed(ActionEvent e) {
        // TODO add your code here
        fileList();
    }

    private void SortTypeActionPerformed(ActionEvent e) {
        // TODO add your code here
        fileList();
    }

    private void copyItemActionPerformed(ActionEvent e) {
        // TODO add your code here
        copyFiles = list.getSelectedValuesList();
        copyPath = Cur_URL;
        pasteItem.setEnabled(true);
    }

    private void pasteItemActionPerformed(ActionEvent e)  {
        // TODO add your code here
        int num=copyFiles.size();
        File source=null;
        File dest=null;
        for(int i=0;i<num;i++){
            source=new File(copyPath+copyFiles.get(i));  //�޸���

            dest=new File(Cur_URL+copyFiles.get(i));
            try{
            FileCopy.copyFile(source,dest);
            }catch (IOException ex){
                JOptionPane.showMessageDialog(null, "�����ļ�ʧ��", "��ʾ",JOptionPane.WARNING_MESSAGE);
                ex.printStackTrace();
            }
        }
        Go_There();
        pasteItem.setEnabled(false);
    }

    private void multiCopyItemActionPerformed(ActionEvent e) {
        // TODO add your code here
        copyFiles = list.getSelectedValuesList();
        copyPath = Cur_URL;
        pasteItem.setEnabled(true);
    }



    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        NorthPanel = new JPanel();
        searchPanel = new JPanel();
        ButtonPanel = new JPanel();
        AllFiles = new JRadioButton();
        Picture = new JRadioButton();
        Video = new JRadioButton();
        Music = new JRadioButton();
        Text = new JRadioButton();
        sortPanel = new JPanel();
        SortTxt = new JLabel();
         String Sort_Items[] = {"�ļ���С","����ʱ��","����ĸ"};
        SortList = new JComboBox(Sort_Items);
        String Sort_Type_Items[] = {"����","����"};
        SortType = new JComboBox(Sort_Type_Items);
        searchTxtPanel = new JPanel();
        SearchTxt = new JLabel();
        panel10 = new JPanel();
        SearchType = new JLabel();
        DirCheck = new JCheckBox();
        FileCheck = new JCheckBox();
        SearchText = new JTextField();
        goPanel = new JPanel();
        historyPanel = new JPanel();
        PreBtn = new JButton();
        LatBtn = new JButton();
        goButtonPanel = new JPanel();
        GoBtn = new JButton();
        goTxtPanel = new JPanel();
        GuideText = new JTextField();
        TreePane = new JPanel();
        filesTree = new FilesTree();
        scrollTree = new JScrollPane(filesTree);
        ShowPane = new JPanel();
        scrollShow = new JScrollPane();
        listPanel = new JPanel();
        list = new JList<String>(){
        @Override
             public int locationToIndex(Point location) {
                int index = super.locationToIndex(location);
                if (index != -1 && !getCellBounds(index, index).contains(location)) {
                        return -1;
                     }
                else {
                        return index;
                     }
             }
        };
        clearSelect = new JButton();
        popupMenu1 = new JPopupMenu();
        openItem = new JMenuItem();
        deleteItem = new JMenuItem();
        renameItem = new JMenuItem();
        propertiesItem = new JMenuItem();
        copyItem = new JMenuItem();
        popupMenu2 = new JPopupMenu();
        diskOpenItem = new JMenuItem();
        DiskPropertiesItem = new JMenuItem();
        popupMenu3 = new JPopupMenu();
        multiCopyItem = new JMenuItem();
        delete = new JMenuItem();
        popupMenu4 = new JPopupMenu();
        pasteItem = new JMenuItem();
        createDirItem = new JMenuItem();
        createTxtItem = new JMenuItem();

        //======== this ========
        Dimension d=Toolkit.getDefaultToolkit().getScreenSize();//�õ���ʾ���Ĵ�С
        int x= (int) d.getWidth();
        int y= (int) d.getHeight();
        setPreferredSize(new Dimension((x*3)/4,(y*3)/4));
        setTitle("\u6587\u4ef6\u8d44\u6e90\u7ba1\u7406\u5668");
        setMaximizedBounds(new Rectangle(0, 0, 36500, 36500));
        setFont(new Font("\u5fae\u8f6f\u96c5\u9ed1", Font.BOLD, 18));
        setIconImage(new ImageIcon(getClass().getResource("/mac.jpg")).getImage());
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== NorthPanel ========
        {
            NorthPanel.setPreferredSize(new Dimension(1200, 80));
            NorthPanel.setLayout(new BorderLayout());

            //======== searchPanel ========
            {
                searchPanel.setPreferredSize(new Dimension(1200, 40));
                searchPanel.setLayout(new BorderLayout());

                //======== ButtonPanel ========
                {
                    ButtonPanel.setPreferredSize(new Dimension(400, 19));
                    ButtonPanel.setLayout(new GridLayout(1, 5));

                    //---- AllFiles ----
                    AllFiles.setText("\u6240\u6709\u6587\u4ef6");
                    AllFiles.setSelected(true);
                    AllFiles.setPreferredSize(new Dimension(100, 19));
                    AllFiles.setFont(new Font("\u5e7c\u5706", Font.BOLD, 14));
                    AllFiles.addActionListener(e -> AllFilesActionPerformed(e));
                    ButtonPanel.add(AllFiles);

                    //---- Picture ----
                    Picture.setText("\u56fe\u7247");
                    Picture.setFont(new Font("\u5e7c\u5706", Font.BOLD, 14));
                    Picture.addActionListener(e -> PictureActionPerformed(e));
                    ButtonPanel.add(Picture);

                    //---- Video ----
                    Video.setText("\u89c6\u9891");
                    Video.setFont(new Font("\u5e7c\u5706", Font.BOLD, 14));
                    Video.addActionListener(e -> VideoActionPerformed(e));
                    ButtonPanel.add(Video);

                    //---- Music ----
                    Music.setText("\u97f3\u4e50");
                    Music.setFont(new Font("\u5e7c\u5706", Font.BOLD, 14));
                    Music.addActionListener(e -> MusicActionPerformed(e));
                    ButtonPanel.add(Music);

                    //---- Text ----
                    Text.setText("\u6587\u6863");
                    Text.setFont(new Font("\u5e7c\u5706", Font.BOLD, 14));
                    Text.addActionListener(e -> TextActionPerformed(e));
                    ButtonPanel.add(Text);
                }
                searchPanel.add(ButtonPanel, BorderLayout.WEST);

                //======== sortPanel ========
                {
                    sortPanel.setPreferredSize(new Dimension(260, 30));
                    sortPanel.setLayout(new BorderLayout());

                    //---- SortTxt ----
                    SortTxt.setText("     \u6392\u5e8f");
                    SortTxt.setFont(new Font("\u5e7c\u5706", Font.BOLD, 14));
                    sortPanel.add(SortTxt, BorderLayout.WEST);

                    //---- SortList ----
                    SortList.setSelectedIndex(2);
                    SortList.setFont(new Font("\u5e7c\u5706", Font.BOLD, 14));
                    SortList.addActionListener(e -> SortListActionPerformed(e));
                    sortPanel.add(SortList, BorderLayout.CENTER);

                    //---- SortType ----
                    SortType.setFont(new Font("\u5e7c\u5706", Font.BOLD, 14));
                    SortType.addActionListener(e -> SortTypeActionPerformed(e));
                    sortPanel.add(SortType, BorderLayout.EAST);
                }
                searchPanel.add(sortPanel, BorderLayout.EAST);

                //======== searchTxtPanel ========
                {
                    searchTxtPanel.setPreferredSize(new Dimension(290, 30));
                    searchTxtPanel.setLayout(new BorderLayout());

                    //---- SearchTxt ----
                    SearchTxt.setText("  \u641c\u7d22");
                    SearchTxt.setFont(new Font("\u5e7c\u5706", Font.BOLD, 14));
                    searchTxtPanel.add(SearchTxt, BorderLayout.WEST);

                    //======== panel10 ========
                    {
                        panel10.setLayout(new GridLayout(1, 3));

                        //---- SearchType ----
                        SearchType.setText("  \u641c\u7d22\u7c7b\u578b");
                        SearchType.setFont(new Font("\u5e7c\u5706", Font.BOLD, 14));
                        panel10.add(SearchType);

                        //---- DirCheck ----
                        DirCheck.setText("\u76ee\u5f55");
                        DirCheck.setSelected(true);
                        DirCheck.setFont(new Font("\u5e7c\u5706", Font.BOLD, 14));
                        panel10.add(DirCheck);

                        //---- FileCheck ----
                        FileCheck.setText("\u6587\u4ef6");
                        FileCheck.setSelected(true);
                        FileCheck.setFont(new Font("\u5e7c\u5706", Font.BOLD, 14));
                        panel10.add(FileCheck);
                    }
                    searchTxtPanel.add(panel10, BorderLayout.EAST);

                    //---- SearchText ----
                    SearchText.setFont(new Font("\u5fae\u8f6f\u96c5\u9ed1", Font.BOLD, 14));
                    SearchText.addActionListener(e -> SearchTextActionPerformed(e));
                    searchTxtPanel.add(SearchText, BorderLayout.CENTER);
                }
                searchPanel.add(searchTxtPanel, BorderLayout.CENTER);
            }
            NorthPanel.add(searchPanel, BorderLayout.PAGE_START);

            //======== goPanel ========
            {
                goPanel.setPreferredSize(new Dimension(1200, 50));
                goPanel.setLayout(new BorderLayout());

                //======== historyPanel ========
                {
                    historyPanel.setPreferredSize(new Dimension(200, 30));
                    historyPanel.setLayout(new GridLayout(1, 2));

                    //---- PreBtn ----
                    PreBtn.setIcon(new ImageIcon(getClass().getResource("/back.png")));
                    PreBtn.addActionListener(e -> PreBtnActionPerformed(e));
                    historyPanel.add(PreBtn);

                    //---- LatBtn ----
                    LatBtn.setIcon(new ImageIcon(getClass().getResource("/forward.jpg")));
                    LatBtn.addActionListener(e -> LatBtnActionPerformed(e));
                    historyPanel.add(LatBtn);
                }
                goPanel.add(historyPanel, BorderLayout.WEST);

                //======== goButtonPanel ========
                {
                    goButtonPanel.setPreferredSize(new Dimension(130, 30));
                    goButtonPanel.setLayout(new GridLayout(1, 1));

                    //---- GoBtn ----
                    GoBtn.setIcon(new ImageIcon(getClass().getResource("/go.png")));
                    GoBtn.addActionListener(e -> GoBtnActionPerformed(e));
                    goButtonPanel.add(GoBtn);
                }
                goPanel.add(goButtonPanel, BorderLayout.EAST);

                //======== goTxtPanel ========
                {
                    goTxtPanel.setLayout(new GridLayout(1, 1));

                    //---- GuideText ----
                    GuideText.setFont(new Font("\u5fae\u8f6f\u96c5\u9ed1", Font.BOLD, 16));
                    GuideText.addActionListener(e -> GuideTextActionPerformed(e));
                    goTxtPanel.add(GuideText);
                }
                goPanel.add(goTxtPanel, BorderLayout.CENTER);
            }
            NorthPanel.add(goPanel, BorderLayout.CENTER);
        }
        contentPane.add(NorthPanel, BorderLayout.NORTH);

        //======== TreePane ========
        {
            TreePane.setBorder(LineBorder.createBlackLineBorder());
            TreePane.setBackground(Color.white);
            TreePane.setForeground(new Color(0, 0, 51));
            TreePane.setPreferredSize(new Dimension(200, 14));
            TreePane.setLayout(new BorderLayout());
            TreePane.add(scrollTree, BorderLayout.CENTER);
        }
        contentPane.add(TreePane, BorderLayout.WEST);

        //======== ShowPane ========
        {
            stack = new Stack<String>();
            stack_return = new Stack<String>();
            ShowPane.setBorder(LineBorder.createBlackLineBorder());
            ShowPane.setBackground(Color.white);
            ShowPane.setForeground(Color.black);
            ShowPane.setPreferredSize(new Dimension(250, 66));
            ShowPane.setLayout(new BorderLayout());

            //======== scrollShow ========
            {
                list.add(popupMenu1);
                list.add(popupMenu2);
                list.add(popupMenu3);
                list.add(popupMenu4);
                Home_List();
                scrollShow.setMaximumSize(new Dimension(10, 10));

                //======== listPanel ========
                {
                    listPanel.setLayout(new BorderLayout());

                    //---- list ----
                    list.setPreferredSize(null);
                    list.setVisibleRowCount(10);
                    list.setMaximumSize(new Dimension(10, 10));
                    list.setFont(new Font("\u5fae\u8f6f\u96c5\u9ed1", Font.BOLD, 16));
                    list.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            listMouseClicked(e);
                        }
                    });
                    listPanel.add(list, BorderLayout.CENTER);

                    //---- clearSelect ----
                    clearSelect.setText("clear");
                    clearSelect.setPreferredSize(new Dimension(180, 30));
                    clearSelect.setIcon(new ImageIcon(getClass().getResource("/lhasaTiger.jpg")));
                    clearSelect.addActionListener(e -> clearSelectActionPerformed(e));
                    listPanel.add(clearSelect, BorderLayout.EAST);
                }
                scrollShow.setViewportView(listPanel);
            }
            ShowPane.add(scrollShow, BorderLayout.CENTER);
        }
        contentPane.add(ShowPane, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(getOwner());

        //======== popupMenu1 ========
        {
            popupMenu1.setFont(new Font("\u5fae\u8f6f\u96c5\u9ed1", Font.BOLD, 16));
            popupMenu1.setPreferredSize(new Dimension(100, 117));

            //---- openItem ----
            openItem.setText("    \u6253\u5f00");
            openItem.setFont(new Font("\u5fae\u8f6f\u96c5\u9ed1", Font.BOLD, 16));
            openItem.setPreferredSize(new Dimension(100, 117));
            openItem.addActionListener(e -> openItemActionPerformed(e));
            popupMenu1.add(openItem);

            //---- deleteItem ----
            deleteItem.setText("    \u5220\u9664");
            deleteItem.setFont(new Font("\u5fae\u8f6f\u96c5\u9ed1", Font.BOLD, 16));
            deleteItem.setPreferredSize(new Dimension(100, 117));
            deleteItem.addActionListener(e -> deleteItemActionPerformed(e));
            popupMenu1.add(deleteItem);

            //---- renameItem ----
            renameItem.setText("   \u91cd\u547d\u540d");
            renameItem.setFont(new Font("\u5fae\u8f6f\u96c5\u9ed1", Font.BOLD, 16));
            renameItem.setPreferredSize(new Dimension(100, 117));
            renameItem.addActionListener(e -> renameItemActionPerformed(e));
            popupMenu1.add(renameItem);

            //---- propertiesItem ----
            propertiesItem.setText("    \u5c5e\u6027");
            propertiesItem.setFont(new Font("\u5fae\u8f6f\u96c5\u9ed1", Font.BOLD, 16));
            propertiesItem.setPreferredSize(new Dimension(100, 117));
            propertiesItem.addActionListener(e -> propertiesItemActionPerformed(e));
            popupMenu1.add(propertiesItem);

            //---- copyItem ----
            copyItem.setText("    \u590d\u5236");
            copyItem.setFont(new Font("\u5fae\u8f6f\u96c5\u9ed1", Font.BOLD, 16));
            copyItem.setPreferredSize(new Dimension(100, 117));
            copyItem.addActionListener(e -> copyItemActionPerformed(e));
            popupMenu1.add(copyItem);
        }

        //======== popupMenu2 ========
        {
            popupMenu2.setFont(new Font("\u5fae\u8f6f\u96c5\u9ed1", Font.BOLD, 16));
            popupMenu2.setPreferredSize(new Dimension(80, 80));

            //---- diskOpenItem ----
            diskOpenItem.setText("   \u6253\u5f00");
            diskOpenItem.setFont(new Font("\u5fae\u8f6f\u96c5\u9ed1", Font.BOLD, 16));
            diskOpenItem.setPreferredSize(new Dimension(80, 80));
            diskOpenItem.addActionListener(e -> diskOpenItemActionPerformed(e));
            popupMenu2.add(diskOpenItem);

            //---- DiskPropertiesItem ----
            DiskPropertiesItem.setText("   \u5c5e\u6027");
            DiskPropertiesItem.setFont(new Font("\u5fae\u8f6f\u96c5\u9ed1", Font.BOLD, 16));
            DiskPropertiesItem.setPreferredSize(new Dimension(80, 80));
            DiskPropertiesItem.addActionListener(e -> DiskPropertiesItemActionPerformed(e));
            popupMenu2.add(DiskPropertiesItem);
        }

        //======== popupMenu3 ========
        {
            popupMenu3.setFont(new Font("\u5fae\u8f6f\u96c5\u9ed1", Font.BOLD, 16));

            //---- multiCopyItem ----
            multiCopyItem.setText("\u590d\u5236");
            multiCopyItem.setFont(new Font("\u5fae\u8f6f\u96c5\u9ed1", Font.BOLD, 16));
            multiCopyItem.addActionListener(e -> multiCopyItemActionPerformed(e));
            popupMenu3.add(multiCopyItem);

            //---- delete ----
            delete.setText("\u5220\u9664");
            delete.setFont(new Font("\u5fae\u8f6f\u96c5\u9ed1", Font.BOLD, 16));
            delete.addActionListener(e -> deleteActionPerformed(e));
            popupMenu3.add(delete);
        }

        //======== popupMenu4 ========
        {
            popupMenu4.setFont(new Font("\u5fae\u8f6f\u96c5\u9ed1", Font.BOLD, 16));
            popupMenu4.setPreferredSize(new Dimension(120, 100));

            //---- pasteItem ----
            pasteItem.setText("      \u7c98\u8d34");
            pasteItem.setEnabled(false);
            pasteItem.setFont(new Font("\u5fae\u8f6f\u96c5\u9ed1", Font.BOLD, 16));
            pasteItem.setPreferredSize(new Dimension(120, 100));
            pasteItem.addActionListener(e -> pasteItemActionPerformed(e));
            popupMenu4.add(pasteItem);

            //---- createDirItem ----
            createDirItem.setText("   \u521b\u5efa\u76ee\u5f55");
            createDirItem.setFont(new Font("\u5fae\u8f6f\u96c5\u9ed1", Font.BOLD, 16));
            createDirItem.setPreferredSize(new Dimension(120, 100));
            createDirItem.addActionListener(e -> createDirItemActionPerformed(e));
            popupMenu4.add(createDirItem);

            //---- createTxtItem ----
            createTxtItem.setText("\u521b\u5efa\u6587\u672c\u6587\u4ef6");
            createTxtItem.setFont(new Font("\u5fae\u8f6f\u96c5\u9ed1", Font.BOLD, 16));
            createTxtItem.setPreferredSize(new Dimension(120, 100));
            createTxtItem.addActionListener(e -> createTxtItemActionPerformed(e));
            popupMenu4.add(createTxtItem);
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

    private void Home_List() {//�ع��ʼ���̽���
        List<String>  Disks= MemoryInfo.getDisk();//������Ϣ
        defaultListModel=new DefaultListModel();
        for (int i = 0; i < Disks.size(); ++i) {
            defaultListModel.addElement(Disks.get(i));
        }
        Icon[] icons = GetFileIcon.getSmallIcon("HOME",null);
        list.setModel(defaultListModel);
        list.setCellRenderer(new MyCellRenderer(icons));
        GuideText.setText("");
        Cur_URL = "";
        stack.push(Cur_URL);
    }

    public void OpenIt(File file) {
        //���ô򿪡��ļ� ��API
        try {
            Desktop.getDesktop().open(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void fileList(){
        defaultListModel.clear();
        String[] getString = GetFileNames.getFileName(Cur_URL);
        for (int i = 0; i < getString.length; ++i) {
            defaultListModel.addElement(getString[i]);
        }
        Icon[] icons = GetFileIcon.getSmallIcon(Cur_URL,getString);
        list.setModel(defaultListModel);
        list.setCellRenderer(new MyCellRenderer(icons));
    }
    public void Go_There() {
        GuideText.setText(Cur_URL);
        if (Cur_URL != "") {//Cur_URL�ǿգ�������Ŀ��Ŀ¼
            fileList();
        }else{
            //Cur_URLΪ��ʱ������ת�ظ�Ŀ¼
            Home_List();
        }
    }

    public void GetAllResults(String path) {
        //�������ܺ��ĺ���
        if (path != "") {
            String[] getString = GetFileNames.getFileName(path);
            for(int i=0;i<getString.length;i++){
                File file = new File(path + getString[i] + "/");  //�޸���
                if (file.isDirectory()) {
                    //�������ļ�����  �ݹ�
                    GetAllResults(path+getString[i]+"/");   //�޸���
                }
                else{
                    String suffix=getString[i].substring(getString[i].lastIndexOf('.')+1);
                    if(VideoType.contains(suffix)&&Video.isSelected()){
                        Maps.put(getString[i],path+getString[i]);//������ʱ���Ӧ�ļ����ƺ��ļ�·��������״̬�£�ɾ��ֱ����Map��·��
                        defaultListModel.addElement(getString[i]);
                        AllIcons[Icon_Counter++] = GetFileIcon.getSingleSmallIcon(path + getString[i]);
                    } else if (GraphType.contains(suffix) && Picture.isSelected()) {
                        Maps.put(getString[i],path+getString[i]);
                        defaultListModel.addElement(getString[i]);
                        AllIcons[Icon_Counter++] = GetFileIcon.getSingleSmallIcon(path + getString[i]);
                    } else if (TxtType.contains(suffix) && Text.isSelected()) {
                        Maps.put(getString[i],path+getString[i]);
                        defaultListModel.addElement(getString[i]);
                        AllIcons[Icon_Counter++] = GetFileIcon.getSingleSmallIcon(path + getString[i]);
                    } else if (MusicType.contains(suffix) && Music.isSelected()) {
                        Maps.put(getString[i],path+getString[i]);
                        defaultListModel.addElement(getString[i]);
                        AllIcons[Icon_Counter++] = GetFileIcon.getSingleSmallIcon(path + getString[i]);
                    }
                }
            }
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {

    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JPanel NorthPanel;
    private JPanel searchPanel;
    private JPanel ButtonPanel;
    private JRadioButton AllFiles;
    private JRadioButton Picture;
    private JRadioButton Video;
    private JRadioButton Music;
    private JRadioButton Text;
    private JPanel sortPanel;
    private JLabel SortTxt;
    public JComboBox SortList;
    public JComboBox SortType;
    private JPanel searchTxtPanel;
    private JLabel SearchTxt;
    private JPanel panel10;
    private JLabel SearchType;
    private JCheckBox DirCheck;
    private JCheckBox FileCheck;
    private JTextField SearchText;
    private JPanel goPanel;
    private JPanel historyPanel;
    private JButton PreBtn;
    private JButton LatBtn;
    private JPanel goButtonPanel;
    private JButton GoBtn;
    private JPanel goTxtPanel;
    private JTextField GuideText;
    private JPanel TreePane;
    private JScrollPane scrollTree;
    private JPanel ShowPane;
    private JScrollPane scrollShow;
    private JPanel listPanel;
    public JList<String> list;
    public JButton clearSelect;
    private JPopupMenu popupMenu1;
    private JMenuItem openItem;
    private JMenuItem deleteItem;
    private JMenuItem renameItem;
    private JMenuItem propertiesItem;
    private JMenuItem copyItem;
    private JPopupMenu popupMenu2;
    private JMenuItem diskOpenItem;
    private JMenuItem DiskPropertiesItem;
    private JPopupMenu popupMenu3;
    private JMenuItem multiCopyItem;
    private JMenuItem delete;
    private JPopupMenu popupMenu4;
    private JMenuItem pasteItem;
    private JMenuItem createDirItem;
    private JMenuItem createTxtItem;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
    public static MainForm _instance;
    FilesTree filesTree;
    public String Cur_URL = "";
    public String LatURL = "";

    //�����ļ���ʽƥ��ĳ�ʼ��
    List<String> VideoType = Arrays.asList("avi","wmv","rm","rmvb","mpeg1","mpeg2","mpeg4","mp4","3gp","asf","swf","vob","dat","mov","m4v","flv","f4v","mkv","mts","ts","qsv","AVI","WMV","RM","RMVB","MPEG1","MPEG2","MPEG4","MP4","3GP","ASF","SWF","VOB","DAT","MOV","M4V","FLV","F4V","MKV","MTS","TS","QSV");
    List<String> GraphType = Arrays.asList("bmp","gif","jpeg","jpeg2000","tiff","psd","png","swf","svg","pcx","dxf","wmf","emf","lic","eps","tga","jpg","BMP","GIF","JPEG","JPEG2000","TIFF","PSD","PNG","SWF","SVG","PCX","DXF","WMF","EMF","LIC","EPS","TGA","JPG");
    List<String> TxtType = Arrays.asList("txt","doc","docx","wps","pdf","chm","pdg","wdl","xls","xlsx","ppt","pptx","java","c","cpp","py");
    List<String> MusicType = Arrays.asList("cd","wave","wav","aiff","au","mp3","midi","wma","aac","ape","CD","WAVE","WAV","AIFF","AU","MP3","MIDI","WMA","RealAudio","VQF","OggVorbis","AAC","APE");
    public Map<String, String> Maps = new HashMap<String,String>();

    //�ļ��б����ر���
    public DefaultListModel defaultListModel;
    public Stack<String> stack, stack_return;
    public Icon[] AllIcons = new Icon[999999];//�洢�����õ����ļ�ͼ��
    public int Icon_Counter = 0;

    //����GB,MB,KB,B��Ӧ���ֽ��������㻻���ļ���С����λ
    long[] Sizes = {1073741824,1048576,1024,1};
    String[] Size_Names = {"GB", "MB", "KB", "B"};
    Boolean isSearching = false;

    //�ļ�����
    private List<String> copyFiles;
    private String copyPath;
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        MainForm m = new MainForm();
    }

}
