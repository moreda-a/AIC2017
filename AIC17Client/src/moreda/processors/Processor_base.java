package moreda.processors;

import java.util.Map;

import moreda.Performance;
import moreda.Processor;
import moreda.Strategy;
import moreda.performances.Performance_base;
import moreda.strategies.Strategy_base;

public class Processor_base extends Processor {

	public Processor_base(String name) {
		super(name);
		// add any Performance HERE:
		/*
		 * per1 as first performance
		 */
		Performance performance = new Performance_base("per1");
		performances.put(performance.getName(), performance);

		// add any Strategies HERE:
		/*
		 * str1 as first strategy
		 */
		Strategy strategy = new Strategy_base("str1");
		strategies.put(strategy.getName(), strategy);
	}

	@Override
	protected void alwaysMove() {
		Strategy strategy;
		Strategy bestStrategy = strategies.get("str1");
		long le = 0;
		long maxEfficient = Long.MIN_VALUE;
		// handle some thing about delete strategy or add strategy if it needed
		for (Map.Entry<String, Strategy> mapEntry : strategies.entrySet()) {
			strategy = mapEntry.getValue();
			strategy.run(plat);
			le = performances.get("per1").evaluate(plat, strategy.getEndPlat());
			if (maxEfficient <= le) {
				maxEfficient = le;
				bestStrategy = strategy;
			}
		}
		orders = bestStrategy.getOrders();
	}

	@Override
	protected void initialMove() {
		Strategy bestStrategy;
		// handle some thing about delete strategy or add strategy if it needed
		bestStrategy = strategies.get("str1");
		bestStrategy.run(plat);
		orders = bestStrategy.getOrders();
	}

}
