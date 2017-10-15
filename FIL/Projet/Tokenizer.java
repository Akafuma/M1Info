import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
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
	
	private boolean is_whitespaces(char c) //espace + tabulation
	{
		return (c == ' ' || c == '\t');
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
			case '\0': return true;
			case '\t': return true;
			case '(': return true;
			case ')': return true;
			case '[': return true;
			case ']': return true;
			default: return false;
		}
	}
	
	public String tokenizeString(String string)//String avec \0 en fin de chaine
	{
		String tokens = "";
		Noeud node = lexique.getRoot();
		Noeud prec = null;
		char c;
		int pos = 0;
		int posretour = 0;
		int codemot = 0;
		int code = 0;
		boolean mangeEspaces = true;
		boolean lectureMot = false;
		boolean lectureMotLong = false;
		boolean consomme = false;
		boolean fin = false;
		
		while(pos < string.length())
		{
			c = string.charAt(pos);
			pos++;
			if(c == '\0') // need fix
				fin = true;
			
			if(mangeEspaces)
			{
				if(!is_whitespaces(c))
				{
					mangeEspaces = false;
					lectureMot = true;
				}
			}
			if(lectureMot)
			{
				prec = node;
				node = node.getNoeud(c);
				
				if(node == null)
				{
					if(is_separator(c))
					{
						if(is_space(c))
						{
							node = prec.getNoeud('_');
							if(node == null)
							{
								code = prec.getCode();
								if(code < 0)
									code = 0;
								tokens = tokens + code + " ";
								//RAZ
								node = lexique.getRoot();
								mangeEspaces = true;
								lectureMot = false;
							}
							else
							{
								codemot = prec.getCode();
								if(codemot < 0)
									codemot = 0;
								posretour = pos - 1;
								lectureMotLong = true;
								lectureMot = false;
							}
						}
						else//on a fini de lire un mot
						{
							code = prec.getCode();
							if(code < 0) 
								code = 0;
							tokens = tokens + code + " ";
							//RAZ
							node = lexique.getRoot();
							mangeEspaces = true;
							lectureMot = false;
							
							if(!fin)
								pos--; // on relit le séparateur
						}								
					}
					else // pas un mot du lexique
					{
						consomme = true;
						lectureMot = false;
					}
				}
				else
				{
					if(is_separator(c))
					{
						codemot = node.getCode();
						if(codemot < 0)
							codemot = 0;
						posretour = pos;
						lectureMotLong = true;
						lectureMot = false;
					}
				}
			}
			else if(lectureMotLong)
			{
				prec = node;
				node = node.getNoeud(c);
				if(node == null)
				{
					if(is_separator(c))
					{
						if(is_space(c))
						{
							node = prec.getNoeud('_');
							if(node == null)
							{
								code = prec.getCode();
								if(code < 0)//On ne reconnait pas le mot
								{
									tokens = tokens + "0 ";
									pos = posretour;//on reviens en arriere
								}
								else
								{
									tokens = tokens + code + " ";
									if(!fin)
										pos--;
								}
								//RAZ
								node = lexique.getRoot();
								lectureMotLong = false;
								mangeEspaces = true;
							}
							else
							{
								code = prec.getCode();
								if(code > 0)//on reconnait tout le préfixe sans l'espace
								{
									codemot = code;
									posretour = pos - 1;
								}
							}
						}
						else // Le séparateur n'est pas un espace et découpe un mot
						{
							code = prec.getCode();
							if(code < 0)//On ne reconnait pas le mot
							{
								tokens = tokens + "0 ";
								pos = posretour;//on reviens en arriere
							}
							else
							{
								tokens = tokens + code + " ";
								if(!fin) // need fix
									pos--;
							}
							//RAZ
							node = lexique.getRoot();
							lectureMotLong = false;
							mangeEspaces = true;
						}
					}
					else//abandon
					{
						tokens = tokens + codemot + " ";
						pos = posretour;
						node = lexique.getRoot();
						lectureMotLong = false;
						mangeEspaces = true;
					}
				}
				else
				{
					if(is_separator(c))
					{
						code = prec.getCode();
						if(code > 0)
						{
							codemot = code;
							posretour = pos - 1;
						}
					}
				}
			}
			else if(consomme)
			{
				if(is_separator(c))
				{
					tokens = tokens + "0 ";
					consomme = false;
					mangeEspaces = true;
					node = lexique.getRoot();
					if(!fin)
						pos--;
				}
			}
		}
		return tokens;
	}

	public void tokenize(String filename)//ajouter le \0 à la chaine lu avant de l'envoyer
	{		
		try
		{
			Scanner sc = new Scanner(new File(filename));
			String s;
			while(sc.hasNextLine())
			{
				s = sc.nextLine() + '\0';
				System.out.println(s);
				System.out.println(tokenizeString(s));
			}
			sc.close();
		}
		catch(FileNotFoundException e)
		{
			System.out.println(e);
			e.printStackTrace();
		} 
		catch (IOException e)
		{
			System.out.println(e);
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Tokenizer t = new Tokenizer("lexique.txt");
		t.tokenize("train.txt");
		//System.out.println(t.tokenizeString("j'achète à bas prix tout compte fait, c'est bien moins cher!!\0"));
	}

}
