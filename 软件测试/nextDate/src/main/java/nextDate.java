import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
public class nextDate {
    int nyear,nmonth,nday;
    int[][] days={{31,28,31,30,31,30,31,31,30,31,30,31},
            {31,29,31,30,31,30,31,31,30,31,30,31}};
    BufferedReader br;
    {
        try {
            br = new BufferedReader(new FileReader("src\\main\\resources\\Newdate.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) throws IOException {
        nextDate nd=new nextDate();
        nd.getDay();//msg1
        int index=nd.isLeap();//msg2
        if(nd.validate(index)){//msg3
            nd.getNextDay(index);//msg4
        }
        return;
    }
    public void getDay() throws IOException {
        String contextLine=br.readLine();
            String year,month,day;
            year=contextLine.substring(0,4);
            month=contextLine.substring(4,6);
            day=contextLine.substring(6,8);
            nyear=Integer.parseInt(year);
            nmonth=Integer.parseInt(month);
            nday=Integer.parseInt(day);
        }
    public  boolean validate(int index){
        boolean dayOK=true,monthOK=true,yearOK=true;
        if(nyear<1900||nyear>2050){//判定结点6
            yearOK=false;
            System.out.println("错误：输入年份不正确");//  string  message="错误：输入年份不正确";
        }
        if(nmonth<1||nmonth>12){//判定结点8
            monthOK=false;
            System.out.println("错误：输入月份不正确"); //string  message="错误：输入日期不正确";
        }
        if(monthOK&&(nday<1||nday>days[index][nmonth-1])){
            dayOK=false;
            System.out.println("错误：输入日期不正确");
        }
        if(yearOK&&dayOK&&monthOK) {//正确的日期
            return true;
        }
        else{
            return false;
        }

    }
    public  int isLeap(){
        int flag;
        if(nyear % 4 ==0 && nyear % 100 != 0){
            flag=1;
        }
        else if(nyear % 400 == 0){
            flag=1;
        }
        else{
            flag=0;
        }
        return flag;
    }
    public void getNextDay(int index){
        //小于输入月份的天数的前两天
        if(nday<days[index][nmonth-1]-1){//判定结点10
            nday+=2;
        }
        else{//每月的后两天
            nday+=2;
            nday%=days[index][nmonth-1];
            if(nmonth==12){//判定结点15
                nyear++;
                nmonth=1;
            }
            else{
                nmonth++;
            }
        }
        System.out.println(nyear+"-"+nmonth+"-"+nday) ;//string   message=year+"-"+month+"-"+day;
    }

}
