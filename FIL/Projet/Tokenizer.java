import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Tokenizer {
	
	private ArbrePrefixe lexique;
	
	public Tokenizer(ArbrePrefixe ap)
	{
		lexique = ap;
	}
	
	public Tokenizer(String filename)
	{
		lexique = new ArbrePrefixe(filename);
	}
	
	private boolean is_space(char c)
	{
		return (c == ' ');
	}
	
	private boolean is_separator(char c)
	{
		switch(c)
		{
			case ' ': return true;
			case '.': return true;
			case '?': return true;
			case '!': return true;
			case ',': return true;
			case ';': return true;
			case ':': return true;
			case '"': return true;
			case '\'': return true;
			case '\n': return true;
			case '\t': return true;
			case '(': return true;
			case ')': return true;
			case '[': return true;
			case ']': return true;
			default: return false;
		}
	}

	public void tokenize(String filename)
	{		
		try
		{
			Scanner scanner = new Scanner(new File("exemple.txt"));
			scanner.useDelimiter("");
			String character;
			Noeud noeud = lexique.getRoot(); // init, quand on a reconnu ou pas un mot on retourne sur root
			int codemot = -2;
			boolean consomme = false;
			boolean lit = true;;
			
			while(scanner.hasNext())
			{
				character = scanner.next();
				char c = character.charAt(0);
				
				
				if(lit)
				{
					if(is_separator(c))//différencier separator en début de lecture et pendant en début on l'écrit, pendant il arrete la reconnaissance
					{
						if(codemot == -1)
							codemot = 0;
						if(is_space(c))
						{
							if(codemot != -2) //Si codemot vaut -2, alors nous sommes sur la racine, on peut lire autant de espace qu'on veut
							{
								noeud = noeud.getNoeud('_');
								if(noeud == null)
								{
									System.out.print(codemot + " ");
								}
								else
								{
									codemot = noeud.getCode();
									continue;
								}
								
							}
								
						}							
						else
						{
							noeud = noeud.getNoeud(c);
							if(noeud == null)
							{
								System.out.print(codemot + " "); //affiche le code du mot trouvé
								noeud = lexique.getRoot();
								noeud = noeud.getNoeud(c);
								codemot = noeud.getCode();
								System.out.print(codemot + " "); // affiche le code du séparateur
							}
							else
								System.out.print(noeud.getCode() + " ");
						}
						
						//if arret
						codemot = -2;
						noeud = lexique.getRoot();
					}
					else
					{
						noeud = noeud.getNoeud(c);
						if(noeud == null) // n'appartient pas au lexique, on consomme
						{
							consomme = true;
							lit = false;
						}
						else
						{
							codemot = noeud.getCode();
						}
					}
				}				
				else if(consomme)
				{
					if(is_separator(c)) // on a fini de consommer
					{
						consomme = false;
						if(is_space(c))
							System.out.print("0 ");
						else
						{
							noeud = lexique.getRoot();
							noeud = noeud.getNoeud(c);
							if(noeud != null)
								codemot = noeud.getCode();
							System.out.print("0 " + codemot + " ");
							
						}
						codemot = -2;
						noeud = lexique.getRoot();
						lit = true;
					}
				}
				
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
