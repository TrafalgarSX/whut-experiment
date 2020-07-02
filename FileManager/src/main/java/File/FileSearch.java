package File;

import UI.MainForm;

import javax.swing.*;
import java.io.File;
import java.util.LinkedList;
import java.util.Queue;

public class FileSearch {
    public static void bfsSearchFile(String path, String regex, boolean isDisplyDir, boolean isDisplayFile) {
        /**
         *���ö��У������������
         *isDisplyDir ����Ŀ¼true
         * isDisplayFile �����ļ�true
         */
        boolean isFind=false;
        regex="(.*)" + regex + "(.*)";
        if (!(isDisplayFile || isDisplyDir)) {
            throw new IllegalArgumentException("isDisplyDir��isDisplayFile������Ҫ��һ��Ϊtrue");
        }
        Queue<File> queue = new LinkedList<>();

        File[] fs=new File(path).listFiles();
        //������һ��
        for (File f : fs) {
            //�ѵ�һ���ļ��м������
            if(f.isDirectory()){
                queue.offer(f);
            }
            else{
                if (f.getName().matches(regex) && isDisplayFile) {
                    isFind=true;
                    MainForm._instance.Maps.put(f.getName(), f.getAbsolutePath());
                    MainForm._instance.defaultListModel.addElement(f.getName());
                    MainForm._instance.AllIcons[MainForm._instance.Icon_Counter++] = GetFileIcon.getSingleSmallIcon(f.getAbsolutePath());
                }
            }
        }
        //���������ȥ
        while (!queue.isEmpty()) {
            File fileTemp=queue.poll();//�Ӷ���ͷȡһ��Ԫ��
            if (isDisplyDir) {
                if (fileTemp.getName().matches(regex)) {
                    isFind=true;
                    MainForm._instance.Maps.put(fileTemp.getName(), fileTemp.getAbsolutePath());
                    MainForm._instance.defaultListModel.addElement(fileTemp.getName());
                    MainForm._instance.AllIcons[MainForm._instance.Icon_Counter++] = GetFileIcon.getSingleSmallIcon(fileTemp.getAbsolutePath());
                }
            }

            File[] fileListTemp=fileTemp.listFiles();
            if (fileListTemp == null) {
                continue;//�����޷����ʵ��ļ�������
            }
            for (File f : fileListTemp) {
                if (f.isDirectory()) {
                    queue.offer(f);//�Ӷ���β����һ��Ԫ��
                }
                else{
                    if (f.getName().matches(regex) && isDisplayFile) {
                        isFind=true;
                        MainForm._instance.Maps.put(f.getName(), f.getAbsolutePath());
                        MainForm._instance.defaultListModel.addElement(f.getName());
                        MainForm._instance.AllIcons[MainForm._instance.Icon_Counter++] = GetFileIcon.getSingleSmallIcon(f.getAbsolutePath());
                    }
                }
                if (!isFind) {
                    JOptionPane.showMessageDialog(null, "δ�ҵ���ؽ��", "ȷ�϶Ի���", JOptionPane.YES_OPTION);

                }
            }
        }
    }
}
