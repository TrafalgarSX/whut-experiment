 class testSync implements Runnable {
    private int number;
    public byte res[];
    public static int count = 5;

    public testSync(int number, byte a[]) {
        this.number = number;
        res = a;
    }

    @Override
    public void run() {
        synchronized (res) {
            while (count-- > 0) {
                try {
                    res.notify();//唤醒等待res资源的线程，把锁交给线程
                    System.out.println("" + number);
                    res.wait();//释放CPU控制权，释放res的锁，本线程阻塞，等待被唤醒
                    System.out.println("---线程" + Thread.currentThread().getName() + "获得锁，wait()后的代码继续运行" + number);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
    public class waitNoify{
        public static void main(String[] args) {
            final byte a[]={0};//这个对象作为共享资源
            new Thread(new testSync(1,a),"1").start();
            new Thread(new testSync(2,a),"2").start();
        }
    }

