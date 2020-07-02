package File;

import javax.swing.*;
import java.io.File;

public class FileDelete {
    public static boolean delete(String fileName) {
        File file=new File(fileName);
        if (!file.exists()) {
            JOptionPane.showMessageDialog(null, "ɾ���ļ�ʧ��:" + fileName + "�����ڣ�", "��ʾ",JOptionPane.WARNING_MESSAGE);
            return false;
        }
        else{
            if (file.isDirectory()) {
                return deleteDirectory(fileName);
            }
            else{
                return deleteFile(fileName);
            }
        }
    }

    private static boolean deleteDirectory(String dir) {
        // ���dir�����ļ��ָ�����β���Զ�����ļ��ָ���
        if (!dir.endsWith(File.separator))
            dir = dir + File.separator;
        File dirFile = new File(dir);
        // ���dir��Ӧ���ļ������ڣ����߲���һ��Ŀ¼�����˳�
        if ((!dirFile.exists()) || (!dirFile.isDirectory())) {
            JOptionPane.showMessageDialog(null, "ɾ��Ŀ¼ʧ�ܣ�" + dir + "�����ڣ�", "��ʾ",JOptionPane.WARNING_MESSAGE);
            return false;
        }
        boolean flag=true;
        //ɾ���ļ����е������ļ�������Ŀ¼
        File[] files=dirFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            //ɾ�����ļ�
            if (files[i].isFile()) {
                flag = FileDelete.deleteFile(files[i].getAbsolutePath());
                if (!flag)
                    break;
                }
            else if (files[i].isDirectory()) {
                flag = FileDelete.deleteDirectory(files[i].getAbsolutePath());
                if (!flag) {
                    break;
                }
            }
            }
        if (!flag) {
            JOptionPane.showMessageDialog(null, "ɾ��Ŀ¼ʧ�ܣ�", "��ʾ",JOptionPane.WARNING_MESSAGE);
            return false;
        }
        //ɾ����ǰĿ¼
        if (dirFile.delete()) {
            JOptionPane.showMessageDialog(null, "ɾ��Ŀ¼" + dir + "�ɹ���", "��ʾ",JOptionPane.WARNING_MESSAGE);
            return true;
        }
        else{
            return false;
        }
    }

    private static boolean deleteFile(String fileName) {
        File file = new File(fileName);
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                JOptionPane.showMessageDialog(null, "ɾ�������ļ�" + fileName + "�ɹ���", "��ʾ",JOptionPane.WARNING_MESSAGE);
                return true;
            }
            else{
                JOptionPane.showMessageDialog(null, "ɾ�������ļ�" + fileName + "ʧ�ܣ�", "��ʾ",JOptionPane.WARNING_MESSAGE);
                return false;
            }
        }
        else{
            JOptionPane.showMessageDialog(null, "ɾ�������ļ�ʧ�ܣ�" + fileName + "�����ڣ�", "��ʾ",JOptionPane.WARNING_MESSAGE);
            return false;
        }
    }
}
