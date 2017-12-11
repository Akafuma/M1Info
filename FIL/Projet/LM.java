import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;

//Charge un modèle de langage en mémoire
public class LM {
	private Unigramme unigramme;
	private Bigramme bigramme;
	private double lissageAlpha;
	private int N;
	private int V1;
	private int V2;
	private double unigrammeInconnu;
	
	public LM()
	{
		lissageAlpha = 0.1;
	}
	
	public LM(String filename) // fichier de compte
	{
		lissageAlpha = 0.1;
		load(filename);
	}
	
	public void load(String filename)
	{
		try
		{
			Scanner sc = new Scanner(new File(filename));
			
			unigramme = new Unigramme();
			bigramme = new Bigramme();
			
			boolean uni = true;
			int token1;
			int token2;
			int count;
			
			int total = 0;
			
			sc.nextLine();//"1-gram"
			String str;
			while(sc.hasNextLine())
			{
				str = sc.nextLine();
				if(str.compareTo("2-gram:") == 0)
				{
					uni = false;
				}
				else
				{
					Scanner sc_str = new Scanner(str);
					if(uni)
					{
						token1 = sc_str.nextInt();
						count = sc_str.nextInt();
						
						unigramme.add(token1, count);
						
						total += count;
					}
					else
					{
						token1 = sc_str.nextInt();
						token2 = sc_str.nextInt();
						count = sc_str.nextInt();

						bigramme.add(token1, token2, count);
					}
					
					sc_str.close();
				}
			}
			
			sc.close();
			
			N = total;
			V1 = unigramme.size();
			V2 = bigramme.size();

			double PL;
			PL = Math.log10(lissageAlpha) - Math.log10(N + V1 * lissageAlpha);
			unigrammeInconnu = (-1) * PL;
			
			//On a les comptes en mémoire, maintenant on calcule les logprobs
			
			for(int i = 0; i < unigramme.size(); i++)
			{
				if(unigramme.getCount(i) > 0)
				{	
					PL = Math.log10(unigramme.getCount(i) + lissageAlpha) - Math.log10(N + V1 * lissageAlpha);
					unigramme.setProb(i, (-1) * PL);
				}
			}
			for(int i = 0; i < bigramme.size(); i++)
			{
				Vector<BigrammeElement> v = bigramme.getBigrammes(i);
				if(v != null)
				{
					for(int j = 0; j < v.size(); j++)
					{
						BigrammeElement e = v.get(j);
						PL = Math.log10(e.getCount() + lissageAlpha) - Math.log10(unigramme.getProb(e.getNumToken1()) + V2 * lissageAlpha);
						e.setLogprob((-1) * PL);
					}
				}
			}
			//e
		}
		catch(IOException e)
		{
			System.out.println(e);
		}
	}
	
	public double get(int token1)
	{
		double pl = unigramme.getProb(token1);
		if(pl == 0)
			pl = unigrammeInconnu;
		return pl;
	}
	
	public double get(int token1, int token2)
	{
		double pl = bigramme.getProb(token1, token2);
		if(pl == 0)
		{
			int c = unigramme.getCount(token1);
			pl = Math.log10(lissageAlpha) - Math.log10(c + V2 * lissageAlpha);
			pl = pl * (-1);
		}
		return pl;
	}
	
	public double evalPPL(String s)//tokénisé
	{
		Scanner sc = new Scanner(s);
		
		double ppl = 0;
		int token1, token2;
		int k = 0;//compter le nombre de mots de la phrase
		double PL_W = 0;
		
		if(sc.hasNextInt())
		{
			token1 = sc.nextInt();
			k++;
			PL_W += get(token1);
		}
		else //erreur arguments
		{
			sc.close();
			return 0;
		}
		
		while(sc.hasNextInt())
		{
			token2 = sc.nextInt();
			k++;
			PL_W += get(token1, token2);
			
			token1 = token2; // l'élément courant devient l'élément précédant
		}
		sc.close();
		
		ppl = Math.pow(2, (1.0 / k) * PL_W);
		
		return ppl;
	}
}
