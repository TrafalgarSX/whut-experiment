public class testVar {
    public static void main(String[] args) {
       guo name=new guo();
        guo newName;
        newName=name;
        newName.setGuo("yawen");
        System.out.println(name.getGuo()+" "+newName.getGuo());

    }
}
class guo{
    private String guo="guo";
    public void setGuo(String name){
        guo=name;
    }
    public String getGuo(){
        return guo;
    }
}