package lockimplementation;

import java.util.Map;

public class ReentrantReadWriteLock 
{
	private Map<Thread, Integer> readersMap;
	private int writer = 0;
	private int writeRequest = 0;
	private Thread writingThread;
	
	public synchronized void readLock() throws InterruptedException
	{
		Thread current = Thread.currentThread();
		while(!canRead(current))
		{
			wait();
		}
		readersMap.put(current, 1);
	}
	
	public synchronized void readUnlock()
	{
		Thread current = Thread.currentThread();
		if(!readersMap.containsKey(current))
		{
			throw new IllegalMonitorStateException("Calling Thread does not" +
			        " hold a read lock on this ReentrantReadWriteLock"); 
		}
		readersMap.put(current, readersMap.get(current) -1);
		if(readersMap.get(current) == 0)
		{
			readersMap.remove(current);
		}
		notifyAll();
	}
	
	public synchronized void writeLock() throws InterruptedException
	{
		writeRequest++;
		Thread current = Thread.currentThread();
		while(!canWrite(current))
		{
			wait();
		}
		writer++;
		writeRequest--;
		writingThread = current;
	}
	
	public synchronized void writeUnlock()
	{
		Thread current = Thread.currentThread();
		if(writingThread != current)
		{
			throw new IllegalMonitorStateException("Calling Thread does not" +
			        " hold a write lock on this ReentrantReadWriteLock"); 
		}
		writer--;
		if(writer == 0)
		{
			writingThread = null;
		}
		notifyAll();
	}
	
	private boolean canRead(Thread current)
	{
		if(writingThread != null && writingThread == current)
		{
			return true;
		}
		if(writer > 0 || writeRequest > 0)
		{
			return false;
		}
		return true;
	}
	
	private boolean canWrite(Thread current)
	{
		if(writingThread != null && writingThread == current)
		{
			return true;
		}
		if(readersMap.size() == 1 && readersMap.containsKey(current))
		{
			return true;
		}
		if(writer > 0 || writeRequest > 0 || readersMap.size() > 0)
		{
			return false;
		}		
		return true;
		
	}

}
