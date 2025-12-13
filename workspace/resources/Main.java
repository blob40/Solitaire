package resources;

public class Main {

	public static void main(String[] args) {
		Solitaire game = new Solitaire();
		GUI gui = new GUI(game);
		System.out.println("Game ran");
	}
}