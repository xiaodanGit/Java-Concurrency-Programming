package threadBasic;

public class SimpleDeamon 
{
	public static void main(String[] args)
	{
		Runnable deamon = new Runnable()	
		{
	
			@Override
			public void run() 
			{
				for(int i = 0; i < 10; i ++)
				{
					System.out.println(i);
					try {
						Thread.sleep(10000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
				
			}
			
		};
		
		Thread t = new Thread(deamon);
		//t.setDaemon(true);
		t.start();
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	

}
