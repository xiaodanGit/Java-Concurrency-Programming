package threadBasic;

public class JoinThread 
{
	public static void main(String[] args)
	{
		Runnable run1 = new Runnable()	
		{
	
			@Override
			public void run() 
			{
				
				try {
					Thread.sleep(10000);
					System.out.println("before");
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
		};
		Runnable run2 = new Runnable()
		{
	
			@Override
			public void run() 
			{
				System.out.println("after");
				try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
		};
		
		Thread t1 = new Thread(run1);
		Thread t2 = new Thread(run2);
		t1.start();
		try {
			t1.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		t2.start();
	}
}
