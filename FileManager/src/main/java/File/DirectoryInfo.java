package File;

import java.io.File;

public class DirectoryInfo {
    public static DirectoryInfo _instance=null;
    public long FileNum,DirectoryNum;

    public DirectoryInfo() {
        this._instance=this;
        FileNum=0;
        DirectoryNum=0;
    }

    public long getDirSize(File file) {
        if (file.exists()) {
            //如果是目录就递归计算其内容的总大小
            if (file.isDirectory()) {
                DirectoryNum++;
                File[] children=file.listFiles();
                if(children==null){//如果文件夹无法访问，就返回0
                    return 0;
                }
                long size=0;
                for (File f : children) {
                    size += getDirSize(f);
                }
                return size;
            }
            else{
                FileNum++;
                long size=file.length();
                return size;
            }
        }
        else{
            System.out.println("文件或者文件夹不存在，请检查路径是否正确！");
            return 0;
        }
    }
}

