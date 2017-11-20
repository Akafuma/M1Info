
public class ElementBigramme {
	private int numToken;
	private int count;
	
	public ElementBigramme(int num)
	{
		numToken = num;
		count = 1;
	}

	public int getNumToken() {
		return numToken;
	}
	
	public void incr()
	{
		count++;
	}

	public int getCount() {
		return count;
	}
}
