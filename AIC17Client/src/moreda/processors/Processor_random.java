package moreda.processors;

import client.model.BeetleType;
import client.model.CellState;
import client.model.Move;
import moreda.Order;
import moreda.Processor;

public class Processor_random extends Processor {

	public Processor_random(String name) {
		super(name);
	}

	@Override
	protected void alwaysMove() {
		// TODO Auto-generated method stub
	}

	@Override
	protected void initialMove() {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 2; j++) {
				for (int k = 0; k < 3; k++) {
					orders.add(new Order(BeetleType.values()[0], CellState.values()[i], CellState.values()[j],
							CellState.values()[k], Move.values()[Move.stepForward.getValue()]));
					orders.add(new Order(BeetleType.values()[1], CellState.values()[i], CellState.values()[j],
							CellState.values()[k], Move.values()[Move.stepForward.getValue()]));
				}
			}
		}
	}

}
