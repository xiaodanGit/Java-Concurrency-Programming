package lockimplementation;

/**
 * @author Owner
 * Not reentrant lock
 *
 */
public class ReadWriteLock 
{
	private int reader = 0;
	private int writer = 0;
	private int writerRequest = 0;
	
	public ReadWriteLock()
	{
		
	}
	
	public synchronized void readLock()
	{
		while(writer > 0 || writerRequest > 0 )
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
		reader++;
		
		
	}
	
	public synchronized void readUnlock()
	{
		reader--;
		notifyAll();
	}
	
	public synchronized void writeLock()
	{
		writerRequest++;
		while(writer > 0 || writerRequest > 0 )
		{
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		writer++;
		writerRequest--;
		notifyAll();
	}
	
	public synchronized void writeUnlock()
	{
		writer--;
		notifyAll();
	}
	

}
