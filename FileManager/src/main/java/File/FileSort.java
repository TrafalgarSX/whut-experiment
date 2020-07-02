package File;

import javax.swing.*;
import java.io.File;
import java.util.*;

public class FileSort {
    public static String[] nameSort(String[] fileName,String sortType){
        if(sortType.equals("降序")){//如果是降序，才需要排序，原本就是升序
            Arrays.sort(fileName, Collections.reverseOrder());
        }
        return fileName;
    }
    public static String[] timeSort(String[] fileName,String path,String sortType){
        Map<String,String> nameAndTime=new HashMap();
        for(int index=0;index<fileName.length;index++){
            nameAndTime.put(fileName[index],FileTime.getFileCreateTime(path+fileName[index]));
        }
        List<Map.Entry<String, String>> list = new ArrayList<Map.Entry<String, String>>(nameAndTime.entrySet());

        list.sort(new Comparator<Map.Entry<String, String>>() {
            @Override
            public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2) {
                if(sortType.equals("升序")) {
                    return o1.getValue().compareTo(o2.getValue());
                }else{
                    return o2.getValue().compareTo(o1.getValue());
                }
            }
        });
        for(int i=0;i<fileName.length;i++){
            fileName[i]=list.get(i).getKey();
        }
        return fileName;
    }
    public static String[] sizeSort(String[] fileName,String path,String sortType){
        Map<String, Long> nameAndSize=new HashMap();
        DirectoryInfo Dinfo=new DirectoryInfo();
        Long size = null;
        for(int index=0;index<fileName.length;index++){
            File file=new File(path+fileName[index]);
            if(file.isDirectory()){
                size=new Long(Dinfo.getDirSize(file));
            }else{
                size=new Long(file.length());
            }
            nameAndSize.put(fileName[index],size);
        }
        List<Map.Entry<String, Long>> list = new ArrayList<Map.Entry<String, Long>>(nameAndSize.entrySet());
        list.sort(new Comparator<Map.Entry<String, Long>>() {
            @Override
            public int compare(Map.Entry<String, Long> o1, Map.Entry<String, Long> o2) {
                if(sortType.equals("升序")) {
                    return o1.getValue().compareTo(o2.getValue());
                }else{
                    return o2.getValue().compareTo(o1.getValue());
                }
            }
        });
        for(int i=0;i<fileName.length;i++){
            fileName[i]=list.get(i).getKey();
        }
        return fileName;
    }

}
