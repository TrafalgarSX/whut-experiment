import java.util.LinkedList;
import java.util.concurrent.Semaphore;

/*
信号量方法实现   Semaphore基于计数的信号量
我们也可以创建计数为1的Semaphore，将其作为一种类似互斥锁的机制，这也叫二元信号量，表示两种互斥状态。
此外用法很多
 */

public class genconDemo2 {
    //仓库存储的载体
    private LinkedList<Object> list=new LinkedList<>();
    //仓库的最大容量
    final Semaphore Full=new Semaphore(10);
    //将线程挂起，等待其他来触发
    final Semaphore Empty=new Semaphore(0);
    //互斥锁
    final Semaphore mutex=new Semaphore(1);

    public void produce(){
        try{
            Full.acquire();
            mutex.acquire();
            list.add(new Object());
            System.out.println("生产者"+Thread.currentThread().getName()+"生产了一个产品，现库存"+list.size());
            mutex.release();
            Empty.release();
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void consume(){
        try{
            Empty.acquire();
            mutex.acquire();
            list.remove();
            System.out.println("消费者"+Thread.currentThread().getName()+"消费了一个产品，现库存"+list.size());
            mutex.release();
            Full.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
