package File;

import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class FileCreate {
    private static File file;

    public static boolean create(String fileName, String dirOrFile) {
        file = new File(fileName);
        if (file.exists()) {
            JOptionPane.showMessageDialog(null, "�����ļ�ʧ��:" + fileName + "�Ѵ��ڣ�", "��ʾ", JOptionPane.WARNING_MESSAGE);
            return false;
        } else {
            if (dirOrFile.equals("�ļ���")) {
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
        file.mkdir();//�����ļ���
        return true;
    }
}