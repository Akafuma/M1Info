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
	private Unigramme unigramme; //les comptes -- rework utiliser associative list
	private Bigramme bigramme; //les comptes	
	
	public TokenCounter() {
		unigramme = new Unigramme();
		bigramme = new Bigramme();
	}	
	
	public String getInput() {
		return input;
	}

	public String getOutput() {
		return output;
	}

	//Produit les comptes des 1-gram et 2-gram
	public void count(String filename, String nameoutput) // Attention au mots non reconnus par l'analyse lexicale
	{
		try
		{
			Scanner sc = new Scanner(new File(filename));
			
			input = filename;
			output = nameoutput;
			
			int num;
			int last;
			//Comptage bigramme et unigramme
			while(sc.hasNextLine())
			{
				String str = sc.nextLine();
				Scanner sc_str = new Scanner(str);
				
				num = sc_str.nextInt();
				unigramme.incr(num);
				//unigramme[num]++;
				
				//ajout bigramme 0 num	
				bigramme.incr(0, num);			
				last = num;
				
				while(sc_str.hasNextInt())
				{
					num = sc_str.nextInt();
					//unigramme[num]++;
					unigramme.incr(num);
					bigramme.incr(last, num);
					last = num;
				}				
				sc_str.close();
			}
			
			sc.close();
			
			//Ecriture du fichier
			BufferedWriter bw = new BufferedWriter(new FileWriter(nameoutput));
			bw.write("1-gram:");
			bw.newLine();
			for(int i = 0; i < unigramme.size(); i++)
			{
				if(unigramme.getCount(i) > 0)
				{
					String str = unigramme.indexToString(i);
					bw.write(str);
					bw.newLine();
				}
			}
			
			bw.write("2-gram:");
			bw.newLine();
			for(int i = 0; i < bigramme.size(); i++)
			{
				Vector<BigrammeElement> v = bigramme.getBigrammes(i);
				if(v != null)
				{
					for(int j = 0; j < v.size(); j++)
					{
						BigrammeElement e = v.get(j);
						String str = e.getNumToken1() + " " + e.getNumToken2() + " " + e.getCount();
						bw.write(str);
						bw.newLine();
					}
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
	
	public static void main(String[] args) {
		/*
		Tokenizer t = new Tokenizer("lexique_ratp_fr.txt");
		t.tokenize("corpus_ratp_fr.txt", "corpus_ratp_fr_code.txt");
		TokenCounter tc = new TokenCounter();
		tc.count("corpus_ratp_fr_code.txt", "corpus_ratp_fr_lm.txt");
		System.out.println("Compte terminé");
		*/
		LM lm = new LM("corpus_ratp_fr_lm.txt");
		System.out.println("Chargement et calcul de LM terminé");
		Treillis treillis = new Treillis();
		treillis.loadFromFile();
		System.out.println("Treillis chargé");
		String s = treillis.viterbi(lm);
		System.out.println(s);
		System.out.println(lm.evalPPL(s));
		System.out.println(lm.evalPPL("3011 8963 20 5928"));
		//System.out.println(lm.evalPPL(t.tokenizeStr("maison est cool")));
	}
	
}
