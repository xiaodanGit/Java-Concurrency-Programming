package shareResources;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class Print implements Runnable
{
	private IntegerGenerator syncObject;
	private int state;
	private char c;
	
	public Print(IntegerGenerator syncObject, int state, char c)
	{
		this.syncObject = syncObject;
		this.state = state;
		this.c = c;
	}
	
	@Override
	public void run() 
	{
		while(true)
		{	
			synchronized(syncObject)		
			{
				if(syncObject.getInteger() % 3 == state)
				{
					System.out.println(c);
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					syncObject.setInteger(syncObject.getInteger() + 1);
					
				}
			}
		}
		
	}
}

class IntegerGenerator
{
	private int startInteger = 0;
	
	public int getInteger()
	{
		return startInteger;
	}
	
	public void setInteger(int val)
	{
		this.startInteger = val;
	}
	
}


public class ThreeThreadsPrint 
{
	public static void main(String[] args)
	{
		IntegerGenerator gen = new IntegerGenerator();
		Print printA = new Print(gen, 0, 'A');
		Print printB = new Print(gen, 1, 'B');
		Print printC = new Print(gen, 2, 'C');
		ExecutorService exec = Executors.newCachedThreadPool();
		exec.execute(printA);
		exec.execute(printB);
		exec.execute(printC);
	}

}


