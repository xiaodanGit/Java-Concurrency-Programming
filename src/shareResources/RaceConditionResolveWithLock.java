package shareResources;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class EvenGenerator1
{
	private int currentEvenNumber = 1;
	private boolean cancel;
	private Lock lock = new ReentrantLock();
	
	public boolean isCanceled()
	{
		return cancel == true;
	}
	
	public int nextInt()
	{
		lock.lock();
		try
		{
			currentEvenNumber++;
			try 
			{
				Thread.sleep(100);
			} 
			catch (InterruptedException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			currentEvenNumber++;
			System.out.println("value is " + currentEvenNumber);
			return currentEvenNumber;
		}
		finally
		{
			lock.unlock();
		}
		
	}
	
	public void cancel()
	{
		this.cancel = true;
	}
}

class Test1 implements Runnable
{

	private  EvenGenerator1 st;
	public Test1(EvenGenerator1 st)
	{
		this.st = st;
	}
	
	@Override
	public void run() 
	{
		while(!st.isCanceled())		
		{			
			int k = st.nextInt();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
			if(k % 2 == 0)
			{
				st.cancel();
				System.out.println("cancel");
			}
			
			
		}
		
	}
	
}

public class RaceConditionResolveWithLock 
{
	public static void main(String[] args)
	{
		ExecutorService exec = Executors.newCachedThreadPool();
		EvenGenerator1 s = new EvenGenerator1();
		Test1 t = new Test1(s);
		
		for(int i = 0; i < 10; i++)
		{
			exec.execute(t);
		}
		//exec.shutdown();
	}
	
}
