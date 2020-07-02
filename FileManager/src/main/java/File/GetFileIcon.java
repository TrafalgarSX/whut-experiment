package File;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.util.List;

public class GetFileIcon {
    /**
     * 获取小图标
     */
    public static Icon getSingleSmallIcon(String path) {
        //获取单个小图标
        FileSystemView fsv = FileSystemView.getFileSystemView();
        File file = new File(path);
        Icon icon = fsv.getSystemIcon(file);
        return icon;
    }

    public static Icon[] getSmallIcon(String path,String[] fileName) {
        //获取目录小所有的小图标
        Icon[] icons = new Icon[9999999];
        FileSystemView fsv=FileSystemView.getFileSystemView();
        int counter=0;
        if (path == "HOME") {
            List<String> Disks=MemoryInfo.getDisk();
            for (int i = 0; i < Disks.size(); ++i) {

                File file = new File(Disks.get(i) + "\\");
                icons[counter++] = fsv.getSystemIcon(file);
            }
        }
        else{
            long start,end;
            start=System.nanoTime();
            /*File file = new File(path);
            File[] files=file.listFiles();
            for (File a : files) {
                if (a != null && a.exists()) {
                    icons[counter++]=fsv.getSystemIcon(a);
                }
            }*/
            int num=fileName.length;
            File[] files=new File[num];
            for(int i=0;i<num;i++){
                files[i]=new File(path+"/"+fileName[i]); //修改了
            }
            for (File a : files) {
                if (a != null && a.exists()) {
                    icons[counter++] = fsv.getSystemIcon(a);
                }
            }
            end=System.nanoTime();
            System.out.println("time is: "+(end-start)*0.000000001+"SECOND");
        }
        return icons;
    }
}
