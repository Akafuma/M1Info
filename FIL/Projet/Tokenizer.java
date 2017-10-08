import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Tokenizer {
	
	ArbrePrefixe lexique;
	
	public Tokenizer(ArbrePrefixe ap)
	{
		lexique = ap;
	}
	
	public Tokenizer(String filename)
	{
		lexique = new ArbrePrefixe(filename);
	}

	public void tokenize(String filename)
	{		
		try
		{
			Scanner scanner = new Scanner(new File("exemple.txt"));
			scanner.useDelimiter("");
			String character;
			while(scanner.hasNext())
			{
				character = scanner.next();
				char c = character.charAt(0);
				System.out.print("[" + c + "]");
			}
			
			scanner.close();
		}
		catch(FileNotFoundException e)
		{
			System.out.println(e);
		}

	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Tokenizer t = new Tokenizer("lexique.txt");
		t.tokenize("exemple.fr");
	}

}
