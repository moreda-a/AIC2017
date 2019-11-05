package moreda;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import client.World;

public abstract class Processor {
	private String name;
	private boolean firstMove = true;
	private World world;
	private Simulator simulator;
	protected Map<String, Performance> performances;
	protected Map<String, Strategy> strategies;
	protected Plat plat;
	protected ArrayList<Order> orders = new ArrayList<Order>();

	public Processor(String name) {
		this.name = name;
		performances = new HashMap<String, Performance>();
		strategies = new HashMap<String, Strategy>();
		simulator = new Simulator();
	}

	public String getName() {
		return name;
	}

	/*
	 * 3part:update, first move, normal turn
	 */
	public void doTurn(World world) {
		System.out.println("Turn: " + world.getCurrentTurn());
		if (firstMove)
			plat = new Plat(world);
		updateWorld(world);
		if (firstMove) {
			firstMove = false;
			initialMove();
		} else
			alwaysMove();
		running();
	}

	public void running() {
		for (Order order : orders) {
			order.runOrder(world);
			System.out.println(order);
		}
	}

	protected Plat makeNewPlat(Plat plat, ArrayList<Order> orders, ArrayList<Order> opOrders) {
		return simulator.runOn(plat, orders, opOrders);
	}

	abstract protected void alwaysMove();

	abstract protected void initialMove();

	void updateWorld(World world) {
		this.world = world;
		plat.update(world);
		orders.clear();
	}
}
