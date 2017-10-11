import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ArbrePrefixe {
	
	private Noeud root;
	
	public ArbrePrefixe() {
		root = new Noeud('0', -2);
	}
	
	public ArbrePrefixe(String filename)
	{
		root = new Noeud('0', -2);
		this.buildFromFile(filename);
	}
	
	public Noeud getRoot() { return root; }
	
	/*
	 * Construit l'arbre à partir d'un fichier de type :
	 * code1 mot1
	 * code2 mot2
	 * .....
	 * coden motn
	 */
	public void buildFromFile(String filename)
	{
		try
		{
			Scanner scanner = new Scanner(new File(filename));
			int code;
			String mot;

			while(scanner.hasNextLine())
			{
				code = scanner.nextInt();
				mot = scanner.next();
				
				add(code, mot);
			}
			
			scanner.close();
		}
		catch(FileNotFoundException e)
		{
			System.out.println(e);
		}
	}
	

	/*
	 * Ajoute un mot et son code dans l'arbre
	 */
	public void add(int code, String mot)
	{
		char c;
		Noeud noeud_courant = root;
		Noeud fils = null;
		
		for(int i = 0; i < mot.length(); i++)
		{
			c = mot.charAt(i);
			fils = noeud_courant.getNoeud(c); // On cherche si le noeud a un fils qui contient le caractère c
			
			if(fils == null) // Pas de fils correspondant, on va le créer
			{
				fils = new Noeud(c, -1);
				noeud_courant.ajouteFils(fils); // On ajoute le fils
				noeud_courant = fils; // On se déplace sur le nouveau noeud
			}
			else
				noeud_courant = fils; // On se déplace sur le nouveau noeud
		}
		
		fils.setCode(code);
	}
	
	/*
	 * Renvoi le code d'un mot dans l'arbre
	 * -1 si le mot n'est pas dans l'arbre
	 */
	public int getCode(String mot)
	{
		if(mot.length() == 0) //mot vide
			return root.getCode();
		
		char c;
		Noeud noeud_courant = root;
		Noeud fils = null;
		
		for(int i = 0; i < mot.length(); i++)
		{
			c = mot.charAt(i);
			fils = noeud_courant.getNoeud(c);
			if(fils == null)
				return -1;
			noeud_courant = fils;
		}
		
		return fils.getCode();
	}
	
	/*
	 * Renvoi true si le mot est dans l'arbre
	 * sinon false
	 */
	public boolean exists(String mot)
	{
		int r = getCode(mot);
		if(r < 0)
			return false;
		else
			return true;
	}
	
	/*
	 * Vérifie si l'arbre est correctement construit
	 */
	private boolean test(String filename)
	{
		try
		{
			Scanner scanner = new Scanner(new File(filename));
			int code;
			String mot;
			int r;
			
			while(scanner.hasNextLine())
			{
				code = scanner.nextInt();
				mot = scanner.next();
				
				r = getCode(mot);
				if(r != code)
				{
					scanner.close();
					return false;
				}
					
			}
			
			scanner.close();
			return true;
		}
		catch(FileNotFoundException e)
		{
			System.out.println(e);
			return false;
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String filename = "D:\\eclipse-workspace\\FIL\\" + args[0];
		ArbrePrefixe T = new ArbrePrefixe("lexique.txt");
		//System.out.println(T.test("lexique.txt"));
		System.out.println(T.exists("_"));
	}

}
