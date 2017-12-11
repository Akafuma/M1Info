import java.util.Vector;

public class Bigramme{
	
	private Vector<Vector<BigrammeElement> > data;
	
	public Bigramme()
	{
		data = new Vector<Vector<BigrammeElement> >(10000);
		data.setSize(10000);
	}
	

	public void incr(int token1, int token2)
	{
		BigrammeElement e = get(token1, token2);
		if(e == null)
		{
			if(token1 >= data.size())
				data.setSize(token1 + 5000);
			
			Vector<BigrammeElement> v = data.get(token1);
			if(v == null)
			{
				v = new Vector<BigrammeElement>();
				data.setElementAt(v, token1);
			}
			v.add(new BigrammeElement(token1, token2));
		}
		else
			e.incr();
	}
	
	public void add(int token1, int token2, int c)
	{
		if(token1 >= data.size())
			data.setSize(token1 + 5000);
		
		Vector<BigrammeElement> v = data.get(token1);
		if(v == null)
		{
			v = new Vector<BigrammeElement>();
			data.setElementAt(v, token1);
		}
		v.add(new BigrammeElement(token1, token2, c));
	}
	
	public int getCount(int token1, int token2)
	{
		BigrammeElement e = get(token1, token2);
		if(e == null)
			return 0;
		else 
			return e.getCount();
	}
	
	public double getProb(int token1, int token2)
	{
		BigrammeElement e = get(token1, token2);
		if(e == null)
			return 0;
		else 
			return e.getLogprob();
	}
	
	public void setProb(int token1, int token2, double p)
	{
		BigrammeElement e = get(token1, token2);
		if(e == null)
			return;
		else 
			e.setLogprob(p);
	}
	
	public int size()
	{
		return data.size();
	}
	
	private BigrammeElement get(int token1, int token2)
	{
		Vector<BigrammeElement> v = data.get(token1);
		if(v == null)
			return null;
		else
		{
			BigrammeElement e;
			for(int i = 0; i < v.size(); i++)
			{
				e = v.get(i);
				if(e.getNumToken2() == token2)
					return e;
			}
			return null;
		}
	}
	
	public Vector<BigrammeElement> getBigrammes(int token1)
	{
		return data.get(token1);
	}
}