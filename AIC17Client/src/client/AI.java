package client;

import java.util.HashMap;
import java.util.Map;

import moreda.Processor;
import moreda.processors.*;

/**
 * AI class. You should fill body of the method {@link #doTurn}. Do not change
 * name or modifiers of the methods or fields and do not add constructor for
 * this class. You can add as many methods or fields as you want! Use world
 * parameter to access and modify game's world! See World interface for more
 * details.
 */

public class AI {
	private Map<String, Processor> processors;

	public AI() {
		processors = new HashMap<>();
		// add any Processors HERE:
		/*
		 * pro1 as first processor
		 */
		Processor processor;
		processor = new Processor_random("pro0");
		processors.put(processor.getName(), processor);
		processor = new Processor_base("pro1");
		processors.put(processor.getName(), processor);

	}

	public void doTurn(World world) {
		processors.get("pro1").doTurn(world);
	}

}
