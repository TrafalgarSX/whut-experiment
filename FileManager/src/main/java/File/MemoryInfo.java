package File;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MemoryInfo {
    public static List<String> getDisk(){
        List<String> list = new ArrayList<String>();
        for(char c='A';c<='Z';c++){
            String dirName=c+":";
            File win = new File(dirName);
            if(win.exists()){
                String str=c+":";
                list.add(str);
            }
        }
        if(list.size()==0){
            list.add("/");
        }
        return list;
    }
}
