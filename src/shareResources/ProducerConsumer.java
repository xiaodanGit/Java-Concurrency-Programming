package shareResources;

import java.util.ArrayList;
import java.util.List;

class Holder
{
	List<String> container;
	int size;
	public Holder(int size)
	{
		container = new ArrayList<String>();
		this.size = size;
	}
	
	public synchronized void put(String str)
	{
		while(container.size() >= size)
		{
			try 
			{
				wait();
			} 
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
		}
		container.add(str);
		System.out.println("put " + str);
		notifyAll();
	}
	
	public synchronized void get()
	{
		while(container.isEmpty())
		{
			try 
			{
				wait();
			} 
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
		}
		String str = container.get(0);
		System.out.println("use " + str);
		container.remove(str);	
		notifyAll();
	}
}

class Producer implements Runnable
{

	Holder holder;	
	public Producer(Holder holder)
	{
		this.holder = holder;
	}
	
	@Override
	public void run() 
	{
		for(int i = 0; i < 50; i++)
		{
			holder.put("" + i);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
}

class Consumer implements Runnable
{

	Holder holder;	
	public Consumer(Holder holder)
	{
		this.holder = holder;
	}
	
	@Override
	public void run() 
	{
		for(int i = 0; i < 50; i++)
		{
			holder.get();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
}

public class ProducerConsumer 
{
	public static void main(String[] args)
	{
		Holder hold = new Holder(5);
		Producer p = new Producer(hold);
		Consumer c = new Consumer(hold);
		Thread t1 = new Thread(p);
		Thread t2 = new Thread(c);
		t1.start();
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		t2.start();
	}

}
