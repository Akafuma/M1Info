import java.util.Vector;

/**
 * @author Axel
 *
 */
public class Noeud {
	
	private char c;
	private int code;
	private Vector<Noeud> chld;
	
	public Noeud(char c, int code)
	{
		this.c = c;
		this.code = code;
		chld = new Vector<Noeud>();
	}
	
	public Noeud getNoeud(char c)
	{
		Noeud n;
		for(int i = 0; i < chld.size(); i++)
		{
			n = chld.get(i);
			if(n.getC() == c)
				return n;				
		}
		return null;
	}
	
	public void ajouteFils(Noeud f)
	{
		chld.add(f);
	}
	
	public char getC() { return c; }
	
	public void setC(char c) { this.c = c; }
	
	public int getCode() { return code; }
	
	public void setCode(int code) { this.code = code; }
	
	public Vector<Noeud> getChld() { return chld; }
	
}
