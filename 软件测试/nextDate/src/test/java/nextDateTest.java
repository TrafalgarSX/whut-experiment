import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class nextDateTest {
    BufferedReader br=null;
    @Before
    public void setUp() throws FileNotFoundException {
    br = new BufferedReader(new FileReader("src\\main\\resources\\date.txt"));
    //br=new BufferedReader(new FileReader("src\\main\\resources\\borderDate.txt"));
    }

    @Test
    public void test() throws IOException {
    String contextLine=br.readLine();
    while(contextLine!=null){
        System.out.println(contextLine);
        String year,month,day;

        year=contextLine.substring(0,4);
        month=contextLine.substring(4,6);
        day=contextLine.substring(6,8);
        nextDate nd=new nextDate();

        nd.nyear=Integer.parseInt(year);
        nd.nmonth=Integer.parseInt(month);
        nd.nday=Integer.parseInt(day);

        System.out.println("年份:"+year+" 月份："+month+" 日期"+day);
        int index=nd.isLeap();//msg2
        if(nd.validate(index)){//msg3
            nd.getNextDay(index);//msg4
        }
        //System.out.println(nd.getNextDay(nyear,nmonth,nday));
        contextLine=br.readLine();
    }
    }
    @Test
    public void testvalidate() throws IOException {
        String contextLine=br.readLine();
        while(contextLine!=null){
            System.out.println(contextLine);
            String year,month,day;

            year=contextLine.substring(0,4);
            month=contextLine.substring(4,6);
            day=contextLine.substring(6,8);
            nextDate nd=new nextDate();

            nd.nyear=Integer.parseInt(year);
            nd.nmonth=Integer.parseInt(month);
            nd.nday=Integer.parseInt(day);

            System.out.println("年份:"+year+" 月份："+month+" 日期"+day);
            int index=nd.isLeap();
            System.out.println(nd.validate(index));
            //System.out.println(nd.getNextDay(nyear,nmonth,nday));
            contextLine=br.readLine();
        }
    }
    @Test
    public void testleap(){
        nextDate nd=new nextDate();
        int year[]={2008,2000,1999};
        for(int index=0;index<year.length;index++){
            nd.nyear=year[index];
            System.out.println(nd.isLeap());
        }

    }
    @Test
    public void testSubString(){
        String line="20000202";
        String year,month,day;
        year=line.substring(0,4);
        month=line.substring(4,6);
        day=line.substring(6,8);
        int nyear,nmonth,nday;
        nyear=Integer.parseInt(year);
        nmonth=Integer.parseInt(month);
        nday=Integer.parseInt(day);
        System.out.println("年份:"+nyear+"月份："+nmonth+"日"+nday);
    }
    @After
    public void tearDown() throws IOException {
        if(br!=null){
            br.close();
        }
    }


}
