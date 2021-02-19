import com.google.zxing.WriterException;


import java.io.IOException;

public class OrderTest extends Thread{
    String name;
    public OrderTest(String name){
        this.name = name;
    }

    public static synchronized void toLc(String name){
        for(int i = 0;i<100;i++){
            System.out.println(name+i);
        }
    }


    @Override
    public  void run() {
      toLc(name);
    }

    public static void main(String[] args) {
        OrderTest orderTest1 = new OrderTest("xiao");
        OrderTest orderTest2 = new OrderTest("tian");
        OrderTest orderTest3 = new OrderTest("di");
        orderTest1.start();
        orderTest2.start();
        orderTest3.start();
    }





}
