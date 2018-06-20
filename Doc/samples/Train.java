import java.util.LinkedList;

public abstract class Train implements Cleanable {
	private Locomotive locomotive;
	private LinkedList<? extends Car> cars;

	public Train(Locomotive locomotive, LinkedList<? extends car> cars) {
		this.locomotive = locomotive;
		this.car = cars;
	}

	public Locomotive getLocomotive() {
		return locomotive;
	}

	public LinkedList<? extends Bogie> getBogies() {
		return bogies;
	}

	@Override
	public void clean() {
		for (Car car : cars) {
			car.clean();
		}
		locomotive.clean();
	}
}
