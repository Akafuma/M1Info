import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class LM {
	
	private String filename;
	private double lissageAlpha;
	private int V1;
	private int V2;
	private int N;
	
	public LM(String filename)
	{
		this.filename = filename;
		lissageAlpha = 0.1;
		init();
	}
	
	private void init()
	{
		try
		{
			Scanner sc = new Scanner(new File(filename));
			
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
	
	public void run(String output)
	{
		
	}
}
