import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

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
			case '\0': return true;
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
			RandomAccessFile reader = new RandomAccessFile(filename, "r");
			long pos = 0;
			int codemot = 0;
			int bytelu;
			char c ;
			Noeud node = lexique.getRoot();
			Noeud prec = null;
			boolean racine = true;
			boolean litmot = false;
			boolean litmotseparator = false;
			boolean consomme = false;
			boolean ok = true;
			
			while(ok)
			{
				bytelu = reader.read();
				c = (char) bytelu;
				if(bytelu == -1)
				{
					c = '\0';
					ok = false;
				}
				
				if(racine)
				{
					if(!is_space(c))
					{
						racine = false;
						litmot = true;
					}
				}
				
				if(litmot)
				{					
					prec = node;
					node = node.getNoeud(c);
					if(node == null)
					{
						if(is_separator(c))// fin du prefixe
						{
							if(is_space(c))
							{
								node = prec.getNoeud('_');
								if(node == null)//fin du préfixe, on regarde si le mot appartient au lexique
								{
									int code = prec.getCode();
									if(code < 0)
										code = 0;
									System.out.print(code + " ");
									node = lexique.getRoot();
									racine = true;
									litmot = false;									
								}
								else//prefixe dont il existe un mot ou plus, plus long
								{
									codemot = prec.getCode();
									pos = reader.getFilePointer() - 1; // pos = position de l'espace, qui sera relu
									litmotseparator = true;
									litmot = false;
								}
							}
							else // c'est l'heure de reconnaitre le mot
							{
								int code = prec.getCode();
								if(code < 0)
									code = 0;
								System.out.print(code + " ");
								node = lexique.getRoot();
								racine = true;
								litmot = false;	
								reader.seek(reader.getFilePointer() - 1); //on relit le separator pour commencer une nouvelle séquence
							}
						}
						else//pas un mot du lexique, on va le consommer
						{
							consomme = true;
							litmot = false;
						}
					}
					else
					{
						if(is_separator(c))
						{
							codemot = node.getCode();
							pos = reader.getFilePointer(); // On cherche à sauvegarder la fin du mot avec le separator ( qui ne peut pas etre un espace )
							litmotseparator = true;
							litmot = false;
						}
					}
				}
				
				else if(litmotseparator)//on lit un préfixe qui contient un séparateur
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
								if(node == null)//fin du préfixe, on regarde si le mot appartient au lexique
								{
									int code = prec.getCode();
									if(code < 0)// finalement on ne reconnait pas le mot, on va revenir au separator
									{
										System.out.print(0 + " ");
										reader.seek(pos);
										node = lexique.getRoot();
										litmotseparator = false;
										racine = true;
									}
									else//oui le mot est dans le lexique
									{
										System.out.print(code + " ");
										node = lexique.getRoot();
										litmotseparator = false;
										racine = true;
										reader.seek(reader.getFilePointer() - 1); //on relit le separator pour commencer une nouvelle séquence
									}
								}
								else
								{
									int code = prec.getCode();//code du préfixe sans le nouveau separator
									if(code > 0) // On peut reconnaitre tout le préfixe
									{
										codemot = code;
										pos = reader.getFilePointer() - 1; //pointeur sur le separator
									}
								}
							}
							else // Le séparateur n'est pas un espace et découpe un mot
							{
								int code = prec.getCode();
								if(code < 0)// finalement on ne reconnait pas le mot, on va revenir au separator
								{
									System.out.print(0 + " ");
									reader.seek(pos);
									node = lexique.getRoot();
									litmotseparator = false;
									racine = true;
								}
								else//oui le mot est dans le lexique
								{
									System.out.print(code + " ");
									node = lexique.getRoot();
									litmotseparator = false;
									racine = true;
									reader.seek(reader.getFilePointer() - 1); //on relit le separator pour commencer une nouvelle séquence
								}
							}
						}
						else//abandon, on revient au plus grand préfixe qui appartenait au lexique
						{
							System.out.print(codemot + " ");
							reader.seek(pos);
							node = lexique.getRoot();
							litmotseparator = false;
							racine = true;
						}
					}
					else
					{
						if(is_separator(c))//separator > 1
						{
							int code = prec.getCode();//code du préfixe sans le nouveau separator
							if(code > 0) // On peut reconnaitre tout le préfixe
							{
								codemot = code;
								pos = reader.getFilePointer() - 1; //pointeur sur le separator
							}
						}
					}
				}
				else if(consomme)
				{
					if(is_separator(c))//On a fini de consommer lorsqu'on lit un séparateur
					{
						System.out.print(0 + " ");
						consomme = false;
						racine = true;
						node = lexique.getRoot();
						reader.seek(reader.getFilePointer() - 1); //On relit le séparateur pour repartir sur une nouvelle séquence
					}
				}
				
			}//End while
			System.out.println();
			//FilePointer = indice de la prochaine lecture			
			reader.close();
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
		t.tokenize("exemple.txt");
	}

}
