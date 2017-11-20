import java.util.Vector;

public class MainTest {

	public static void main(String[] args) {
		/*Code2mot c2m = new Code2mot("lexique.txt");
		c2m.runOnFile("train.code.txt");
		*/
		Vector<Integer> v = new Vector<>(7);
		v.setSize(7);
		System.out.println(v.size());
		for(int i = 0; i < v.size(); i++)
			System.out.println(v.get(i));
	}

}
