package threadBasic;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UsingExecutors 
{
	
	public static void main(String[] args)
	{
		ExecutorService exec = Executors.newCachedThreadPool();
		Runnable print = new Runnable()
		{

			@Override
			public void run() 
			{
				System.out.println("hello world");
				
			}
			
		};
		exec.execute(print);
		Callable<String> get = new Callable<String>()
		{

			@Override
			public String call() throws Exception 
			{
				return "hello world";
			}
			
		};
		try 
		{
			System.out.println(exec.submit(get).get());
		} 
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		} 
		catch (ExecutionException e) 
		{
			e.printStackTrace();
		}
	}

}
