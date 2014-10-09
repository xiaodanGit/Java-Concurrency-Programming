package lockimplementation;

/**
 * Not an reentrant lock
 * @author Owner
 *
 */
public class MySimpleLock 
{
	private boolean locked  = false;
	
	public MySimpleLock()
	{
		
	}
	
	public synchronized void lock()
	{
		while(locked = true)
		{
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		locked = true;
	}
	
	public synchronized void unlock()
	{
		locked = false;
		notifyAll();
	}

}
