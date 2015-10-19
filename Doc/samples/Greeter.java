public class Greeter {
	public static void main(String[] args) {
		Greeter sample = new Greeter("Hallo!");
		sample.greet();
	}
	
	public Greeter(String message) {
	}
	
	private void greet() {
		System.out.println(message);
	}
}
