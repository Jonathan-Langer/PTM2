package model;


import java.util.concurrent.ArrayBlockingQueue;

public class ActiveObject {

    ArrayBlockingQueue<Runnable> q;
    volatile boolean stop;
    Runnable end=()->stop=true;
    Thread t;

    public ActiveObject(int capacity)
    {
        q=new ArrayBlockingQueue<>(capacity);
        t=new Thread(()->{
            while(!stop)
            {
                try{
                    Runnable r=q.take();
                    if(r==end)
                        end.run();
                    else
                        r.run();
                }catch(InterruptedException e){}
            }
        });
        t.start();
    }

    public void execute(Runnable r)
    {
        if(!stop)
            try{
                q.put(r);
            } catch(InterruptedException e){
                e.printStackTrace();
            }
    }

    public void shutDown(){
        execute(end);
    }
    public void shutDownNow(){
        stop=true;
        t.interrupt();
    }
}
