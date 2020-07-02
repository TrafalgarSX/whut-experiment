package File;

import UI.MainForm;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class GetFileNames {

    public static String[] getFileName(String path) {
        //得到目录下第一级的文件/文件夹名
        File file = new File(path);
        String [] fileName=null;
        String sortItem,sortType;
        sortItem= MainForm._instance.SortList.getSelectedItem().toString();
        sortType=MainForm._instance.SortType.getSelectedItem().toString();
        if(sortItem.equals("首字母")){
            fileName=FileSort.nameSort(file.list(),sortType);
        }else if(sortItem.equals("文件大小")){
            fileName=FileSort.sizeSort(file.list(),path,sortType);
        }else if(sortItem.equals("创建时间")){
            fileName=FileSort.timeSort(file.list(),path,sortType);
        }
        return fileName;
    }

    public static void getAllFileName(String path, ArrayList<String> fileName) {
        //得到目录下所有文件/文件夹名
        File file = new File(path);
        File[] files = file.listFiles();
        String [] names=file.list();
        if (names != null) {
            fileName.addAll(Arrays.asList(names));
        }
        for (File a : files) {
            if (a.isDirectory()) {//是目录就递归
                getAllFileName(a.getAbsolutePath(),fileName);
            }
        }
    }
}
