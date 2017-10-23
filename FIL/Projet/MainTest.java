public class MainTest {

	public static void main(String[] args) {
		Code2mot c2m = new Code2mot("lexique.txt");
		c2m.runOnFile("train.code.txt");
		
	}

}
