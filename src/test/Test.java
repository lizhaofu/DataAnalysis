package test;

/**
 * Created by lizhaofu on 2017/3/8.
 */
public class Test {
    public boolean containsInTheLine(int number,String a){
        String[] str = a.split(" ");
        int num=0;
        for (int i = 0; i < str.length; i++) {
            if(Integer.parseInt(str[i])==number)
                num=1;
        }
        if(num==0){
            return true;
        }else
            return false;
    }
    public static void main(String[] args) {
        String s="111";
        int a=Integer.valueOf(s);
        System.out.println(a);
    }
}
