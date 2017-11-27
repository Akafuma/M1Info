import java.util.Scanner;

public class PPL {
	String filename; // nom du fichier lm avec les log prob
	Tokenizer tokenizer;
	
	public PPL(String filename, Tokenizer t)
	{
		this.filename = filename;
		tokenizer = t;
	}
	
	public double eval(String s)
	{
		//1 tokeniser la phrase
		String str = tokenizer.tokenizeStr(s);
		
		//load les proba
		
		//2 evaluer
		double ppl = 0;
		Scanner sc = new Scanner(str);
		int token1, token2;
		if(sc.hasNextInt())
			token1 = sc.nextInt();
		
		return ppl;
	}
}
