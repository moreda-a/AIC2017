package moreda;

import java.util.ArrayList;

import client.model.Beetle;
import client.model.BeetleType;
import client.model.CellState;
import client.model.Move;

public abstract class Strategy {
	private String name;
	protected Plat plat;
	protected Plat endPlat;
	protected ArrayList<Order> orders;

	public Strategy(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void run(Plat plat) {
		this.plat = plat;
		orders = new ArrayList<Order>();
		if (plat.turn == 0)
			runfirst();
		else
			run();
	}

	public void addOrder(BeetleType type, CellState right, CellState front, CellState left, Move move) {
		orders.add(new Order(type, right, front, left, move));
	}

	public void addOrder(int type, int right, int front, int left, int move) {
		addOrder(BeetleType.values()[type], CellState.values()[right], CellState.values()[front],
				CellState.values()[left], Move.values()[move]);
	}

	public void addOrder(int type, int visioni, int move) {
		addOrder(type, visioni % 3, (visioni / 3) % 2, visioni / 6, move);
	}

	public void addOrder(Beetle beetle, BeetleType nextType) {
		orders.add(new Order(beetle, nextType));
	}

	public void addOrder(int beetle, int nexttype) {
		addOrder((Beetle) plat.world.getMap().getCell(plat.getX(beetle), plat.getY(beetle)).getBeetle(),
				BeetleType.values()[nexttype]);
	}

	public ArrayList<Order> getOrders() {
		return orders;
	}

	protected abstract void run();

	protected abstract void runfirst();

	public Plat getEndPlat() {
		return endPlat;
	}
}
