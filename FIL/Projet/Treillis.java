import java.io.File;
import java.io.FileNotFoundException;
import java.util.Locale;
import java.util.Scanner;
import java.util.Vector;


public class Treillis {
	
	private Vector< Vector <TreillisNode>> treillis;
	
	
	public Treillis()
	{
		treillis = new Vector< Vector <TreillisNode>>();
	}
	
	public void loadFromFile()
	{
		String str = "treillis.txt";
		try
		{
			Scanner sc = new Scanner(new File(str));
			sc.useLocale(Locale.US);
			int codemot;
			float prob;
			int index = -1;
			while(sc.hasNextLine())
			{
				String txt = sc.next();
				if(txt.compareTo("%col") == 0)
				{
					sc.nextLine();
					index++;
					//treillis.setSize(index + 1);
					treillis.add(new Vector<TreillisNode>());
				}
				else
				{
					codemot = Integer.parseInt(txt);
					prob = sc.nextFloat();
					TreillisNode n = new TreillisNode(codemot, prob);
					treillis.elementAt(index).add(n);					
				}
			}
			
			sc.close();
		}
		catch(FileNotFoundException e)
		{
			System.out.println(e);
		}
	}
	
	public void parcoursTest()
	{
		for(int i = 0; i < treillis.size(); i++)
		{
			System.out.println( Min(treillis.elementAt(i)) );
		}
	}
	
	private static int Max(Vector<TreillisNode> v)
	{
		int rcode = 0;;
		float max = 0;
		float tmp;
		for(int i = 0; i < v.size(); i++)
		{
			TreillisNode n = v.elementAt(i);
			tmp = n.getProbEmission();
			if(tmp > max)
			{
				max = tmp;
				rcode = n.getCodemot();
			}
		}
		return rcode;
	}
	
	private static int Min(Vector<TreillisNode> v)
	{
		int rcode = 0;;
		float min = Float.MAX_VALUE;
		float tmp;
		for(int i = 0; i < v.size(); i++)
		{
			TreillisNode n = v.elementAt(i);
			tmp = n.getProbEmission();
			if(tmp < min)
			{
				min = tmp;
				rcode = n.getCodemot();
			}
		}
		return rcode;
	}
	
	public static void main(String args[])
	{
		Treillis t = new Treillis();
		t.loadFromFile();
		t.parcoursTest();
	}
}
