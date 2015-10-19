public class Greeter2 {
	public static void main(String[] args) {
		Greeter2 sample = new Greeter2("Hallo!");
		sample.greet();
	}
	private String message;
	public Greeter2(String message) {
		message = message;
	}
	private void greet() {
		System.out.println(message);
	}
}
