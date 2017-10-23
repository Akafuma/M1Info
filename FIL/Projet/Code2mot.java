import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Vector;

public class Code2mot {
	
	Vector<String> correspondance;
	
	private void init(String filename)//Initialise un lexique
	{
		try
		{
			Scanner sc = new Scanner(new File(filename));
			String mot;
			correspondance = new Vector<String>(10000, 10000);
			correspondance.add("");//element 0
			
			while(sc.hasNextLine())
			{
				sc.nextInt();
				mot = sc.next();
				
				correspondance.add(mot);
			}
			sc.close();
		}
		catch(FileNotFoundException e)
		{
			System.out.println(e);
		}
	}
	
	public Code2mot(String lexique)
	{
		init(lexique);
	}
	
	public String runOnString(String str)
	{
		Scanner sctoken = new Scanner(str);
		String output = "";
		int code;
		
		while(sctoken.hasNextInt())
		{
			code = sctoken.nextInt();
			output = output + correspondance.get(code) + " ";
		}
		sctoken.close();
		
		return output;
	}
	
	public void runOnFile(String filename)//Nom d'un fichier de tokens
	{
		try
		{
			Scanner scline = new Scanner(new File(filename));
			String line;
			while(scline.hasNextLine())
			{
				line = scline.nextLine();
				String output = runOnString(line);
				
				System.out.println(output);
			}
			scline.close();
			
		}
		catch(FileNotFoundException e)
		{
			System.out.println(e);
		}
	}
}
