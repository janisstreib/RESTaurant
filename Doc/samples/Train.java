import java.util.LinkedList;

public abstract class Train implements Cleanable {
	private Locomotive locomotive;
	private LinkedList<? extends Bogie> bogies;

	public Train(Locomotive locomotive, LinkedList<? extends Bogie> bogies) {
		this.locomotive = locomotive;
		this.bogies = bogies;
	}

	public Locomotive getLocomotive() {
		return locomotive;
	}

	public LinkedList<? extends Bogie> getBogies() {
		return bogies;
	}

	@Override
	public void clean() {
		for (Bogie bogie : bogies) {
			bogie.clean();
		}
		locomotive.clean();
	}
}