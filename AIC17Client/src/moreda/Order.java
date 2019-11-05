package moreda;

import client.World;
import client.model.Beetle;
import client.model.BeetleType;
import client.model.CellState;
import client.model.Move;

public class Order {
	private Beetle beetle;
	private Move move;
	private BeetleType nextType;
	private BeetleType type;
	private CellState right;
	private CellState front;
	private CellState left;
	private String stype;

	public Order(Beetle beetle, BeetleType nextType) {
		this.beetle = beetle;
		this.nextType = nextType;
		stype = "changeType";
	}

	public Order(BeetleType type, CellState right, CellState front, CellState left, Move move) {
		this.type = type;
		this.right = right;
		this.front = front;
		this.left = left;
		this.move = move;
		stype = "changeStrategy";
	}

	public Move getMove() {
		return move;
	}

	public BeetleType getBeetleType() {
		return type;
	}

	public CellState getFrontRight() {
		return right;
	}

	public CellState getFrontLeft() {
		return left;
	}

	public CellState getFront() {
		return front;
	}

	public BeetleType getNextType() {
		return nextType;
	}

	public String getSType() {
		return stype;
	}

	public Beetle getBeetle() {
		return beetle;
	}

	public void runOrder(World world) {
		if (getSType() == "changeStrategy")
			world.changeStrategy(getBeetleType(), getFrontRight(), getFront(), getFrontLeft(), getMove());
		else
			world.changeType(getBeetle(), getNextType());
	}

	public String toString() {
		if (stype == "changeStrategy")
			return stype + ": " + type + ", " + right + ", " + front + ", " + left + ", " + move;
		return stype + ": " + beetle + ", " + nextType;
	}
}
