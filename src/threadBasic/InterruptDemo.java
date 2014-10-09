package threadBasic;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class BlockMutex
{
	private Lock lock = new ReentrantLock();
	public BlockMutex()
	{
		lock.lock();
	}
	
	public void f()
	{
		try 
		{
			lock.lockInterruptibly();
			System.out.println("acquire the lock" );
				
			
		} 
		catch (InterruptedException e) 
		{
			System.out.println("interrupted from the lock acquisition in f()" );
			e.printStackTrace();
		}
	}
}

class TestRun implements Runnable
{
	BlockMutex mutex =  new BlockMutex();

	@Override
	public void run() 
	{
		System.out.println("waiting for the lock in f()" );
		mutex.f();
		System.out.println("broken out of the blocked call" );
	}
	
}

public class InterruptDemo 
{
	public static void main(String[] args) throws InterruptedException
	{
		Thread t = new Thread(new TestRun());
		t.start();
		TimeUnit.SECONDS.sleep(1);
		System.out.println("issuing t.interrupt()" );
		t.interrupt();
	}
}
