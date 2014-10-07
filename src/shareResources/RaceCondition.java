package shareResources;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class EvenGenerator
{
	private boolean cancel;
	private int currentEvenValue = 1;
	
	public void cancel()
	{
		cancel = true;
	}
	
	public boolean isCancel()
	{
		return cancel  == true;
	}
	
	public int nextInt()
	{
		currentEvenValue++;
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}
		currentEvenValue++;
		System.out.println("the value is " + currentEvenValue);
		return currentEvenValue;
	}
	
}

class Test implements Runnable
{
	EvenGenerator generator;
	public Test(EvenGenerator generator)
	{
		this.generator = generator;
	}
	
	@Override
	public void run() 
	{
		System.out.println("run");
		while(!generator.isCancel())
		{
			int val =  generator.nextInt();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(val%2 == 0)
			{
				generator.cancel();
			}
		}
	}
}

public class RaceCondition 
{
	public static void main(String[] args)
	{
		EvenGenerator gen = new EvenGenerator();
		Test t = new Test(gen);
		ExecutorService exec = Executors.newCachedThreadPool();
		//exec.execute(t);
		for(int i = 0; i < 10; i ++)
		{
			exec.execute(t);
		}
		exec.shutdown();
	}
}
