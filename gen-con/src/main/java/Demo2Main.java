public class Demo2Main {
    public static void main(String[] args) {
        genconDemo2 storage=new genconDemo2();
        Thread p1 = new Thread(new Producer(storage));
        Thread p2 = new Thread(new Producer(storage));
        Thread p3 = new Thread(new Producer(storage));

        Thread c1 = new Thread(new Consumer(storage));
        Thread c2 = new Thread(new Consumer(storage));
        Thread c3 = new Thread(new Consumer(storage));

        p1.start();
        p2.start();
        p3.start();
        c1.start();
        c2.start();
        c3.start();

    }
}
class Producer implements Runnable {
    private genconDemo2 storage;

    public Producer() {
    }

    public Producer(genconDemo2 storage) {
        this.storage = storage;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(100);
                storage.produce();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class Consumer implements Runnable{
    private genconDemo2 storage;
    public Consumer(){}
    public Consumer(genconDemo2 storage){
        this.storage=storage;
    }
    @Override
    public void run() {
        while(true) {
            try {
                Thread.sleep(200);
                storage.consume();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}