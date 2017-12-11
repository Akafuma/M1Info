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
		treillis = new Vector< Vector <TreillisNode>>();
		String str = "treillis.txt";
		try
		{
			Scanner sc = new Scanner(new File(str));
			sc.useLocale(Locale.US);
			int codemot;
			double prob;
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
	
	private static int Min(Vector<TreillisNode> v)
	{
		int rcode = 0;;
		double min = Double.MAX_VALUE;
		double tmp;
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
	
	public String viterbi(LM lm)
	{
		TreillisNode n = runViterbi(lm);
		int tokens[] = new int[treillis.size()];
		TreillisNode curr = n;
		for(int i = treillis.size() - 1; i >= 0; i--)
		{
			tokens[i] = curr.getCodemot();
			curr = curr.getBeta();
		}
		
		String str = "" + tokens[0];
		for(int i = 1; i < tokens.length; i++)
			str = str + " " + tokens[i];
		
		return str;
	}
	
	public TreillisNode runViterbi(LM lm)
	{		
		//initialisation

		Vector<TreillisNode> start = treillis.get(0);
		for(int i = 0; i < start.size(); i++)
		{
			TreillisNode e = start.get(i);
			double var = e.getProbEmission() + lm.get(0, e.getCodemot()); //LPO bigramme 0 x
			e.setAlpha(var);
		}
		
		TreillisNode min = null;
		
		for(int i = 1; i < treillis.size(); i++)
		{			
			Vector<TreillisNode> nodes = treillis.get(i);
			for(int j = 0; j < nodes.size(); j++)
			{
				TreillisNode currJ = nodes.get(j);
				min = null;
				double valueMin = Double.MAX_VALUE;
				Vector<TreillisNode> lastNodes = treillis.get(i - 1);
				
				for(int k = 0; k < lastNodes.size(); k++)
				{
					TreillisNode currK = lastNodes.get(k);
					double value = currK.getAlpha() + lm.get(currK.getCodemot(), currJ.getCodemot()) + currJ.getProbEmission();
					if(value < valueMin)
					{
						min = currK;
						valueMin = value;
					}
				}
				
				currJ.setAlpha( min.getAlpha() + lm.get(min.getCodemot(), currJ.getCodemot()) + currJ.getProbEmission());
				currJ.setBeta(min);
			}
		}
		
		//Séléction du point de départ
		Vector<TreillisNode> end = treillis.get(treillis.size() - 1);
		TreillisNode startEnd = end.get(0);
		double minValue = startEnd.getAlpha();
		
		for(int i = 1; i < end.size(); i++)
		{
			TreillisNode tmp = end.get(i);
			double tmpAlpha = tmp.getAlpha();
			if(tmpAlpha < minValue)
			{
				minValue = tmpAlpha;
				startEnd = tmp;
			}
		}
		
		return startEnd;
	}
	
	public static void main(String args[])
	{
		Vector<Integer> vec = new Vector<Integer>(15);
		vec.setSize(10);
		Double num = 1.5 * 10000;
		int newsize = num.intValue();
		System.out.println(newsize);
	}
}
