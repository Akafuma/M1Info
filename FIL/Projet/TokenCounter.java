import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;

//Rename as TrainLM
public class TokenCounter {
	
	private String input;
	private String output;
	private int unigramme[];
	private Bigramme bigramme;
	private double lissageAlpha;
	private int V1; //nombre d'unigrammes différents
	private int V2; //nombre de bigrammes différents
	private int N; //nombre de mots du corpus
	
	public TokenCounter() {
		unigramme = new int[90000];
		bigramme = new Bigramme();
		lissageAlpha = 0.1;
		N = 0;
	}
	
	//Produit les comptes des 1-gram et 2-gram
	public void count(String filename, String nameoutput)
	{
		try
		{
			Scanner sc = new Scanner(new File(filename));
			
			input = filename;
			output = nameoutput;
			
			//int unigramme[] = new int[90000]; // / ! \ TokenMax
			//Bigramme bigramme = new Bigramme();
			int num;
			int last;
			
			while(sc.hasNextLine())
			{
				String str = sc.nextLine();
				Scanner sc_str = new Scanner(str);
				
				num = sc_str.nextInt();
				unigramme[num]++;
				N++;
				
				//ajout bigramme 0 num	
				bigramme.incr(0, num);			
				last = num;
				
				while(sc_str.hasNextInt())
				{
					num = sc_str.nextInt();
					N++;
					unigramme[num]++;
					bigramme.incr(last, num);
					last = num;
				}				
				sc_str.close();
			}
			
			sc.close();
			
			
			BufferedWriter bw = new BufferedWriter(new FileWriter(nameoutput));
			bw.write("1-gram:");
			bw.newLine();
			for(int i = 0; i < 90000; i++)
			{
				if(unigramme[i] > 0)
				{
					bw.write(i + " " + unigramme[i]);
					bw.newLine();
				}
			}
			
			bw.write("2-gram:");
			bw.newLine();
			for(int i = 0; i < 90000; i++)
			{
				Vector<ElementBigramme> temp = bigramme.getList().get(i);
				
				for(int j = 0; j < temp.size(); j++)
				{
					ElementBigramme e = temp.get(j);
					String str = i + " " + e.getNumToken() + " " + e.getCount();
					bw.write(str);
					bw.newLine();
				}
			}			
			bw.close();
		}		
		catch(FileNotFoundException e)
		{
			System.out.println(e);
		}
		catch(IOException e)
		{
			System.out.println(e);
		}
	}
	
	//Refaire gestion fichiers communicants
	private void init()
	{
		try
		{
			Scanner sc = new Scanner(new File(output));
			
			int count = 0;
			String str;
			boolean ok = true;
			str = sc.nextLine();
			if(str.compareTo("1-gram:") == 0)
			{
				while(sc.hasNextLine() && ok)
				{
					str = sc.nextLine();
					count++;
					if(str.compareTo("2-gram:") == 0)
					{
						count--;
						V1 = count + 1;
						count = 0;
						ok = false;
					}
				}
				while(sc.hasNextLine())
				{
					sc.nextLine();
					count++;
				}
				V2 = count + 1;
			}
			else
			{
				System.out.println("Erreur : mauvais fichier");
			}
			
			sc.close();
		}
		catch(FileNotFoundException e)
		{
			System.out.println(e);
		}
	}
	
	public void LM()
	{
		init();
		try
		{
			Scanner sc = new Scanner(new File(output));
			BufferedWriter bw = new BufferedWriter(new FileWriter("lm.txt"));
			
			String str;
			int token;
			int token2;
			int count;
			double PL;
			double log;
			boolean uni = true;
			
			str = sc.nextLine(); // "1-gram:"
			bw.write("1-gram:"); bw.newLine();
			
			while(sc.hasNextLine()) // Unigramme
			{
				str = sc.nextLine();
				if(str.compareTo("2-gram:") == 0)
				{
					bw.write("2-gram:");
					bw.newLine();
					uni = false;
				}
				else
				{
					Scanner sc_str = new Scanner(str);
					if(uni) // Calcul unigramme
					{
						token = sc_str.nextInt();
						count = sc_str.nextInt();
						PL = (count + lissageAlpha) / (N + V1 * lissageAlpha);
						//transformer PL en -log
						log = (-1) * Math.log10(PL);
						
						bw.write(token + " " + log);
						bw.newLine();
					}
					else //calcul bigramme
					{
						token = sc_str.nextInt();
						token2 = sc_str.nextInt();
						count = sc_str.nextInt();
						PL = (count + lissageAlpha) / (unigramme[token] + V2 * lissageAlpha);
						// log(a / b) = log a - log b
						log = (-1) * Math.log10(PL);
						
						bw.write(token + " " + token2 + " " + log);
						bw.newLine();
					}
					
					sc_str.close();
				}
			}
				
			sc.close();
			bw.close();
		}
		catch(FileNotFoundException e)
		{
			System.out.println(e);
		}
		catch(IOException e)
		{
			System.out.println(e);
		}
	}
	
	public static void main(String[] args) {
		TokenCounter tc = new TokenCounter();
		tc.count("unigramme.txt", "unigramme-out.txt");
		tc.LM();
	}
	
}
