
public class BigrammeElement{
	private int numToken1;
	private int numToken2;
	private int count;
	private double logprob;
	
	public BigrammeElement(int t1, int t2)
	{
		numToken1 = t1;
		numToken2 = t2;
		count = 1;
	}
	
	public BigrammeElement(int t1, int t2, int c)
	{
		numToken1 = t1;
		numToken2 = t2;
		count = c;
	}
	
	public void incr()
	{
		count++;
	}
	
	public String toString()
	{
		return numToken1 + " " + numToken2 + " " + count;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public double getLogprob() {
		return logprob;
	}

	public void setLogprob(double logprob) {
		this.logprob = logprob;
	}

	public int getNumToken1() {
		return numToken1;
	}

	public int getNumToken2() {
		return numToken2;
	}
}