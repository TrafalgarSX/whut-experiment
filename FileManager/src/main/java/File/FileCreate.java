package File;

import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class FileCreate {
    private static File file;

    public static boolean create(String fileName, String dirOrFile) {
        file = new File(fileName);
        if (file.exists()) {
            JOptionPane.showMessageDialog(null, "创建文件失败:" + fileName + "已存在！", "提示", JOptionPane.WARNING_MESSAGE);
            return false;
        } else {
            if (dirOrFile.equals("文件夹")) {
                return createDirectory();
            } else {
                return createFile();
            }
        }
    }

    private static boolean createFile() {
        try {

            file.createNewFile();
            return true;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static boolean createDirectory() {
        file.mkdir();//创建文件夹
        return true;
    }
}