package shareResources;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class states
{
	private int states1 = 1;
	private int states2 = 1;
	
	public boolean isCanceled()
	{
		return states1 != states2;
	}
	
	public void nextStates()
	{
		states1++;
		System.out.println("states1 is " + states1);
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		states2++;
		System.out.println("states2 is " + states2);
	}
}

class Test1 implements Runnable
{

	private states st;
	public Test1(states st)
	{
		this.st = st;
	}
	
	@Override
	public void run() 
	{
		System.out.println("run");
		while(!st.isCanceled())		
		{			
			System.out.println("not canceled");
			st.nextStates();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("canceled");
	}
	
}

public class RaceConditionResolveWithLock 
{
	public static void main(String[] args)
	{
		ExecutorService exec = Executors.newCachedThreadPool();
		states s = new states();
		Test1 t = new Test1(s);
		
		for(int i = 0; i < 10; i++)
		{
			exec.execute(t);
		}
	}
	
}
