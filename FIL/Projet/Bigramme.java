import java.util.Vector;

public class Bigramme{
	
	private Vector< Vector<ElementBigramme>> list;
	
	public Bigramme()
	{
		list = new Vector<>();
		for(int i = 0; i < 90000; i++)
			list.add(i, new Vector<ElementBigramme>());		
	}
	
	public Vector<Vector<ElementBigramme>> getList() {
		return list;
	}

	public void incr(int token1, int token2)
	{
		ElementBigramme e = get(token1, token2);
		if(e == null)
			list.elementAt(token1).add(new ElementBigramme(token2));
		else
			e.incr();
	}
	
	public int getCount(int token1, int token2)
	{
		ElementBigramme e = get(token1, token2);
		if(e == null)
			return 0;
		else
			return e.getCount();
	}
	
	private ElementBigramme get(int token1, int token2)
	{
		Vector<ElementBigramme> suiv = list.get(token1);
		
		if(suiv.size() == 0)
			return null;
		
		for(int i = 0; i < suiv.size(); i++)
		{
			ElementBigramme ele = suiv.get(i);
			if(ele.getNumToken() == token2)
				return ele;				
		}
		
		return null;
	}
}