import java.util.Vector;

public class Unigramme {
	private Vector<UnigrammeElement> data;
	
	class UnigrammeElement {
		private int numToken;
		private int count;
		private double logprob;
		
		public UnigrammeElement(int n) 
		{
			numToken = n;
			count = 1;
		}
		
		public UnigrammeElement(int n, int c)
		{
			numToken = n;
			count = c;
		}
		
		public void incr()
		{
			count++;
		}
		
		public String toString()
		{
			return numToken + " " + count;
		}
	}

	public Unigramme()
	{
		data = new Vector<UnigrammeElement>(10000);
		data.setSize(10000);
	}
	
	public void incr(int n)
	{
		UnigrammeElement e = data.elementAt(n);
		if(e == null) // Si l'unigramme n'existe pas, on l'ajoute avec un compteur initialisé à 1
		{
			if(n >= data.size())
				data.setSize(n + 5000);
				
			data.setElementAt(new UnigrammeElement(n), n);
		}
		else
			e.incr();
	}

	public void add(int n, int c) // Utilisé pour charger un ML depuis un fichier
	{
		if(n >= data.size())
			data.setSize(n + 5000);
		
		data.setElementAt(new UnigrammeElement(n, c), n);
	}
	
	public double getProb(int n)
	{
		UnigrammeElement e = data.get(n);
		if(e == null)
			return 0;
		else
			return e.logprob;
	}
	
	public void setProb(int n, double p)
	{
		UnigrammeElement e = data.get(n);
		if(e == null)
			return;
		else
			e.logprob = p;
	}
	
	public int getCount(int n)
	{
		UnigrammeElement e = data.get(n);
		if(e == null)
			return 0;
		else
			return e.count;
	}
	
	public int size()
	{
		return data.size();
	}
	
	public String indexToString(int i)
	{
		return data.get(i).toString();
	}
}
