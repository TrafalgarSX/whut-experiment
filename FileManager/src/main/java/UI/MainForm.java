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
                "微软雅黑", Font.BOLD, 16)));
        UIManager.put("OptionPane.messageFont", new FontUIResource(new Font(
                "微软雅黑", Font.BOLD, 16)));
        UIManager.put("TextField.font", new FontUIResource(new Font(
                "微软雅黑", Font.BOLD, 16)));
        //InternalFrame.titleFont
        UIManager.put("InternalFrame.titleFont", new FontUIResource(new Font(
                "微软雅黑", Font.BOLD, 16)));
        initComponents();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }

    private void AllFilesActionPerformed(ActionEvent e) {
        // TODO add your code here
        //如果是搜索完切换回显示所有文件，则回到主目录
        isSearching=false;
        Home_List();
    }
    public void SearchFunction(){
        //如果选择了搜索功能，就是点击搜索当前目录下所有相关类型的文件（包括子目录）
        isSearching=true;
        Maps.clear();
        defaultListModel.clear();
        Icon_Counter=0;
        AllIcons=new Icon[999999];
        GetAllResults(Cur_URL);//会搜索子目录
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
        //搜索框输入后按回车健触发该事件
        boolean flag_Dir=false,flag_File=false;
        if (FileCheck.isSelected()) {
            //搜索文件名
            flag_File=true;
        }
        if (DirCheck.isSelected()) {
            //搜索文件夹
            flag_Dir=true;
        }
        if(!(flag_File||flag_Dir)){
            JOptionPane.showMessageDialog(null, "请至少选择一个搜索类别", "确认对话框", JOptionPane.YES_OPTION);
        }
        else{
            //开始搜索
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
        //向左
        LatURL=Cur_URL;
        if (!stack.isEmpty()) {
            stack.pop();//每从当前一个目录跳回之前的目录时，stack就要出栈
            stack_return.push(Cur_URL);//把跳之前的目录放入返回栈
            if (!stack.isEmpty()) {
                Cur_URL=stack.peek();//从栈中得到上一个访问的目录，赋给当前目录
            }
            else{
                Cur_URL="";//如果栈为空，说明前面是根目录
            }
            Go_There();
        }
        if (isSearching) {
            //如果正在搜索状态，那此时应该结束
            isSearching=false;
            AllFiles.setSelected(true);
        }
    }

    private void LatBtnActionPerformed(ActionEvent e) {
        // TODO add your code here
        //向右
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
                JOptionPane.showConfirmDialog(null, "没有找到该目录", "确认对话框", JOptionPane.YES_OPTION);
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
                JOptionPane.showConfirmDialog(null, "没有找到该目录", "确认对话框", JOptionPane.YES_OPTION);
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
        File file = new File(Cur_URL + before + "/"); //修改了
        String after="";

        if (file.isDirectory()) {
            after=(String)JOptionPane.showInputDialog(null, "请输入新文件夹名:\n", "重命名", JOptionPane.PLAIN_MESSAGE, null, null,
                    list.getSelectedValue());
        }else{
            after=(String) JOptionPane.showInputDialog(null, "请输入新文件名:\n", "重命名", JOptionPane.PLAIN_MESSAGE, null, null,
                    before);
        }
        if (before != after && after != null) {
            file.renameTo(new File(Cur_URL + after + "/"));//修改了
            Go_There();
        }
        else{
            Go_There();
        }
    }

    private void deleteItemActionPerformed(ActionEvent e) {
        // TODO add your code here
        File file = new File(Cur_URL + "/" + list.getSelectedValue());  //修改了
        int n;
        if (file.isFile()) {
            n = JOptionPane.showConfirmDialog(null, "确定要删除文件 " + file.getName() + " 么?", "文件删除", JOptionPane.YES_NO_OPTION);
        }
        else{
            n = JOptionPane.showConfirmDialog(null, "确定要删除 " + file.getName() + " 及其目录下的文件么?", "文件夹删除", JOptionPane.YES_NO_OPTION);

        }
        if(n==0){
            FileDelete.delete(Cur_URL + list.getSelectedValue() + "/");  //修改了
            Go_There();
        }
    }

    private void propertiesItemActionPerformed(ActionEvent e) {
        // TODO add your code here
        //打开文件/文件夹属性窗口
        String temp_url=Cur_URL+list.getSelectedValue()+"/";//修改了
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
            //目录属性初始化所需参数
            DirectoryInfo Dinfo=new DirectoryInfo();
            size = Dinfo.getDirSize(file);
            File_Num=Dinfo.FileNum;
            Directory_Num=Dinfo.DirectoryNum;
            flag=1;
        }else {//文件属性初始化所需参数
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
        if (flag == 1) {//文件夹属性
            FileProperties properties = new FileProperties(icon, fileName, file_size, File_Num, Directory_Num - 1, temp_url, CreateTime);
        }
        else{//文件属性
            FileProperties properties=new FileProperties(icon,fileName,file_size,temp_url,CreateTime,ModifyTime,LastAccess);
        }
    }

    private void DiskPropertiesItemActionPerformed(ActionEvent e) {
        // TODO add your code here
        //磁盘属性查看
        String temp_url = list.getSelectedValue() + "/";//修改了
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
        newFolder=(String)JOptionPane.showInputDialog(null, "请输入新创建文件夹名:\n", "创建文件夹", JOptionPane.PLAIN_MESSAGE, null, null,"新建文件夹");


        if (FileCreate.create(Cur_URL+newFolder,"文件夹")) {//如果创建成功
            Cur_URL+=newFolder+"/";   //修改了
            stack.push(Cur_URL);
            Go_There();
        }

    }

    private void createTxtItemActionPerformed(ActionEvent e) {
        // TODO add your code here
        String newFile="";
        newFile=(String)JOptionPane.showInputDialog(null, "请输入新创建文件名:\n", "创建文件", JOptionPane.PLAIN_MESSAGE, null, null,"新建文件");
        if (FileCreate.create(Cur_URL+newFile,"文件")) {//如果创建成功
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
        //多选下的删除
        List<String> selectedStr=list.getSelectedValuesList();
        File file;
        int num=selectedStr.size();
        int n = JOptionPane.showConfirmDialog(null, "确定要删除 " + selectedStr.get(0) + " 等" + num + "项么?", "文件删除",JOptionPane.YES_NO_OPTION);
        if (n == 0) {
            if (isSearching) {//如果正在搜索  Cur_URL就乱了 会有子目录中的文件出现，Cur_URL + selectedStr.get(i)少了个子目录就删不了
                for (int i = 0; i < selectedStr.size(); ++i) {
                    file = new File(Maps.get(selectedStr.get(i)));
                    FileDelete.delete(file.getAbsolutePath());
                }
            }
            else{
                for (int i = 0; i < selectedStr.size(); ++i) {
                    FileDelete.delete(Cur_URL + selectedStr.get(i) + "/");//修改了

                }
                Go_There();
            }
        }
    }

    private void listMouseClicked(MouseEvent e) {
        // TODO add your code here
        //点击下方空白不选择
        if (list.locationToIndex(e.getPoint()) == -1 && !e.isShiftDown()
                && !isMenuShortcutKeyDown(e)) {
            list.clearSelection();
        }
        //单击、双击、右键事件处理
        if (list.getSelectedIndex() != -1) {
            if (e.getClickCount() == 1) {
                //单击List时，暂无事件
            } else if (e.getClickCount() == 2) {
                System.out.println(list.getSelectedValue());
                twoClick(list.getSelectedValue());
            }

            if (e.getButton() == 3) {
                //右击list时，打开菜单栏
                if (Cur_URL != "") {
                    //popupMenu4.show(scrollShow, e.getX(), e.getY());

                    if (list.getSelectedValuesList().size() == 1) {
                        ////如果右击的是单个文件夹和文件，则应打开一个功能齐全的菜单栏
                        popupMenu1.show(list, e.getX(), e.getY());
                    } else if (list.getSelectedValuesList().size() > 1) {
                        //如果选中多个文件夹和文件，则只支持删除功能
                        popupMenu3.show(list,e.getX(),e.getY());
                    }
                }
                else if(Cur_URL==""&& list.getSelectedValuesList().size()==1){
                    popupMenu2.show(list, e.getX(), e.getY());//如果右击的是磁盘，菜单栏中只含有“打开”和“属性”功能
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
        //如果此时不在搜索状态，就是正常的点击处理
        if (!isSearching) {
            choice += "/";  //修改了
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
            //搜索状态，那就要从map里提取我们的URL，因为搜索把顺序都打乱了，无法用一个URL对应
            File file = new File(Maps.get(choice));
            OpenIt(file);
        }
    }

    private void scrollShowMouseClicked(MouseEvent e) {
        // TODO add your code here
        int n = JOptionPane.showConfirmDialog(null, "确定要删除等项么?", "文件删除",JOptionPane.YES_NO_OPTION);
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
            source=new File(copyPath+copyFiles.get(i));  //修改了

            dest=new File(Cur_URL+copyFiles.get(i));
            try{
            FileCopy.copyFile(source,dest);
            }catch (IOException ex){
                JOptionPane.showMessageDialog(null, "复制文件失败", "提示",JOptionPane.WARNING_MESSAGE);
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
         String Sort_Items[] = {"文件大小","创建时间","首字母"};
        SortList = new JComboBox(Sort_Items);
        String Sort_Type_Items[] = {"升序","降序"};
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
        Dimension d=Toolkit.getDefaultToolkit().getScreenSize();//得到显示屏的大小
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

    private void Home_List() {//回归初始磁盘界面
        List<String>  Disks= MemoryInfo.getDisk();//磁盘信息
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
        //调用打开“文件 的API
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
        if (Cur_URL != "") {//Cur_URL非空，就跳入目标目录
            fileList();
        }else{
            //Cur_URL为空时，就跳转回根目录
            Home_List();
        }
    }

    public void GetAllResults(String path) {
        //搜索功能核心函数
        if (path != "") {
            String[] getString = GetFileNames.getFileName(path);
            for(int i=0;i<getString.length;i++){
                File file = new File(path + getString[i] + "/");  //修改了
                if (file.isDirectory()) {
                    //遍历子文件夹下  递归
                    GetAllResults(path+getString[i]+"/");   //修改了
                }
                else{
                    String suffix=getString[i].substring(getString[i].lastIndexOf('.')+1);
                    if(VideoType.contains(suffix)&&Video.isSelected()){
                        Maps.put(getString[i],path+getString[i]);//搜索的时候对应文件名称和文件路径，搜索状态下，删改直接用Map找路径
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

    //各类文件格式匹配的初始化
    List<String> VideoType = Arrays.asList("avi","wmv","rm","rmvb","mpeg1","mpeg2","mpeg4","mp4","3gp","asf","swf","vob","dat","mov","m4v","flv","f4v","mkv","mts","ts","qsv","AVI","WMV","RM","RMVB","MPEG1","MPEG2","MPEG4","MP4","3GP","ASF","SWF","VOB","DAT","MOV","M4V","FLV","F4V","MKV","MTS","TS","QSV");
    List<String> GraphType = Arrays.asList("bmp","gif","jpeg","jpeg2000","tiff","psd","png","swf","svg","pcx","dxf","wmf","emf","lic","eps","tga","jpg","BMP","GIF","JPEG","JPEG2000","TIFF","PSD","PNG","SWF","SVG","PCX","DXF","WMF","EMF","LIC","EPS","TGA","JPG");
    List<String> TxtType = Arrays.asList("txt","doc","docx","wps","pdf","chm","pdg","wdl","xls","xlsx","ppt","pptx","java","c","cpp","py");
    List<String> MusicType = Arrays.asList("cd","wave","wav","aiff","au","mp3","midi","wma","aac","ape","CD","WAVE","WAV","AIFF","AU","MP3","MIDI","WMA","RealAudio","VQF","OggVorbis","AAC","APE");
    public Map<String, String> Maps = new HashMap<String,String>();

    //文件列表的相关变量
    public DefaultListModel defaultListModel;
    public Stack<String> stack, stack_return;
    public Icon[] AllIcons = new Icon[999999];//存储搜索得到的文件图标
    public int Icon_Counter = 0;

    //保存GB,MB,KB,B对应的字节数，方便换算文件大小及单位
    long[] Sizes = {1073741824,1048576,1024,1};
    String[] Size_Names = {"GB", "MB", "KB", "B"};
    Boolean isSearching = false;

    //文件复制
    private List<String> copyFiles;
    private String copyPath;
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        MainForm m = new MainForm();
    }

}
