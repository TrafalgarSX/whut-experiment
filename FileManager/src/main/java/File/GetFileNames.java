package File;

import UI.MainForm;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class GetFileNames {

    public static String[] getFileName(String path) {
        //�õ�Ŀ¼�µ�һ�����ļ�/�ļ�����
        File file = new File(path);
        String [] fileName=null;
        String sortItem,sortType;
        sortItem= MainForm._instance.SortList.getSelectedItem().toString();
        sortType=MainForm._instance.SortType.getSelectedItem().toString();
        if(sortItem.equals("����ĸ")){
            fileName=FileSort.nameSort(file.list(),sortType);
        }else if(sortItem.equals("�ļ���С")){
            fileName=FileSort.sizeSort(file.list(),path,sortType);
        }else if(sortItem.equals("����ʱ��")){
            fileName=FileSort.timeSort(file.list(),path,sortType);
        }
        return fileName;
    }

    public static void getAllFileName(String path, ArrayList<String> fileName) {
        //�õ�Ŀ¼�������ļ�/�ļ�����
        File file = new File(path);
        File[] files = file.listFiles();
        String [] names=file.list();
        if (names != null) {
            fileName.addAll(Arrays.asList(names));
        }
        for (File a : files) {
            if (a.isDirectory()) {//��Ŀ¼�͵ݹ�
                getAllFileName(a.getAbsolutePath(),fileName);
            }
        }
    }
}
