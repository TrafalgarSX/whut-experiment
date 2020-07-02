package File;

import javax.swing.*;
import java.io.File;

public class FileDelete {
    public static boolean delete(String fileName) {
        File file=new File(fileName);
        if (!file.exists()) {
            JOptionPane.showMessageDialog(null, "删除文件失败:" + fileName + "不存在！", "提示",JOptionPane.WARNING_MESSAGE);
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
        // 如果dir不以文件分隔符结尾，自动添加文件分隔符
        if (!dir.endsWith(File.separator))
            dir = dir + File.separator;
        File dirFile = new File(dir);
        // 如果dir对应的文件不存在，或者不是一个目录，则退出
        if ((!dirFile.exists()) || (!dirFile.isDirectory())) {
            JOptionPane.showMessageDialog(null, "删除目录失败：" + dir + "不存在！", "提示",JOptionPane.WARNING_MESSAGE);
            return false;
        }
        boolean flag=true;
        //删除文件夹中的所有文件包括子目录
        File[] files=dirFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            //删除子文件
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
            JOptionPane.showMessageDialog(null, "删除目录失败！", "提示",JOptionPane.WARNING_MESSAGE);
            return false;
        }
        //删除当前目录
        if (dirFile.delete()) {
            JOptionPane.showMessageDialog(null, "删除目录" + dir + "成功！", "提示",JOptionPane.WARNING_MESSAGE);
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
                JOptionPane.showMessageDialog(null, "删除单个文件" + fileName + "成功！", "提示",JOptionPane.WARNING_MESSAGE);
                return true;
            }
            else{
                JOptionPane.showMessageDialog(null, "删除单个文件" + fileName + "失败！", "提示",JOptionPane.WARNING_MESSAGE);
                return false;
            }
        }
        else{
            JOptionPane.showMessageDialog(null, "删除单个文件失败：" + fileName + "不存在！", "提示",JOptionPane.WARNING_MESSAGE);
            return false;
        }
    }
}
