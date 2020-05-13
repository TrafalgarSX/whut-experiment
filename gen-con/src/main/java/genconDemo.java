public class genconDemo {
    private final static  String LOCK="lock";
    private int count=0;
    private static  final int FULL =10;

    public static void main(String[] args) {
        genconDemo genconDemo1=new genconDemo();
        for(int i=1;i<=5;i++){
            new Thread(genconDemo1.new Producer(),"生产者-"+i).start();//进入就绪态，由CPU决定什么时候执行，然后进入运行态
            new Thread(genconDemo1.new Consumer(),"消费者-"+i).start();
            /*
            阻塞有三种：
            1.wait() 使本线程进入到等待阻塞状态
            2.同步阻塞，线程在获取synchronized同步锁失败，会进入同步阻塞状态
            3.其他阻塞，通过调用线程的sleep()或join()或发出了I/O请求时，线程回阻塞
            当SLEEP状态超时，join()等待线程终止或者超时，或者I/O处理完毕后，线程重新进入就绪状态

            死亡状态：线程执行完了run方法，线程的生命周期结束
             */
        }

    }
    class Producer implements Runnable{


        @Override
        public void run() {

            for(int i=0;i<10;i++){
                try{
                    Thread.sleep(100);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                //对象锁
                synchronized (LOCK){
                    while(count==FULL){
                        try{
                            LOCK.wait();
                        }catch (InterruptedException e){
                            e.printStackTrace();
                        }
                    }
                    count++;
                    System.out.println("生产者" + Thread.currentThread().getName() + "总共有" + count + "个资源");
                    LOCK.notifyAll();
                    //不能用notify，因为notify只能任意唤醒一个，如果仅唤醒了一个生产者，其他没唤醒，而这个生产者又进入满循环，进入休眠，就没有线程可以被唤醒了
                }
            }
        }
    }
    class Consumer implements Runnable{


        @Override
        public void run() {
            for(int i=0;i<10;i++){
                try{
                    Thread.sleep(300);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                //对象锁
                synchronized (LOCK){
                    while(count==0){
                        try{
                            LOCK.wait();
                        }catch (InterruptedException e){
                            e.printStackTrace();
                        }
                    }
                    count--;
                    System.out.println("消费者" + Thread.currentThread().getName() + "总共有" + count + "个资源");
                    LOCK.notifyAll();
                }
            }
        }
    }
}
