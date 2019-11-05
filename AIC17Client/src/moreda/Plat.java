package moreda;

import java.util.Collections;

import client.World;
import client.model.Beetle;
import client.model.Cell;
import client.model.Teleport;

public class Plat {
	public World world;

	public int size;
	public int height;
	public int width;
	public int turn;
	public intVector[] adjacencyList;
	public intVector[] nodeS;
	public intVector[][] avision;
	public int[] cellfront;
	public int[][] update;
	public int[] fighter;
	public int[] vision;
	public int[] wm;
	public int[] wl;
	public int[] wr;
	public int[] dir;
	public int[] btype;
	public int[] color;
	public int[] item;
	public int[] power;
	public int[] stalk;
	public boolean[] isick;
	public boolean[] hwing;
	public int myid;
	public int opid;
	public int myscore;
	public int opscore;
	public int totalTurn;
	public int colorCost;
	public int fFoodScore;
	public int qFoodScore;
	public int fKillScore;
	public int qKillScore;
	public int updateCost;
	public intVector[] opatt;
	public intVector[] myatt;
	public int[] opattpow;
	public int[] myattpow;
	public boolean[] activeopatt;
	public int goodForFood;
	public intVector opPower;
	public double pr;
	public int change;

	int[] rowHeadDir = new int[] { 0, -1, 0, 1 };
	int[] colHeadDir = new int[] { 1, 0, -1, 0 };
	int[] rowFrontLeftDir = new int[] { -1, -1, 1, 1 };
	int[] colFrontLeftDir = new int[] { 1, -1, -1, 1 };
	int[] rowFrontRightDir = new int[] { 1, -1, -1, 1 };
	int[] colFrontRightDir = new int[] { 1, 1, -1, -1 };
	int[] rowLeftDir = new int[] { -1, 0, 1, 0 };
	int[] colLeftDir = new int[] { 0, -1, 0, 1 };
	int[] rowRightDir = new int[] { 1, 0, -1, 0 };
	int[] colRightDir = new int[] { 0, 1, 0, -1 };

	public Plat(World world) {
		height = world.getMap().getHeight();
		width = world.getMap().getWidth();
		size = height * width;
		adjacencyList = new intVector[size];
		opatt = new intVector[size];
		myatt = new intVector[size];
		nodeS = new intVector[] { new intVector(), new intVector() };
		avision = new intVector[][] { new intVector[18], new intVector[18] };
		for (int i = 0; i < 18; ++i) {
			avision[0][i] = new intVector();
			avision[1][i] = new intVector();
		}
		opPower = new intVector();
		update = new int[2][18];
		fighter = new int[18];
		vision = new int[size];
		wr = new int[size];
		wl = new int[size];
		wm = new int[size];
		stalk = new int[size];
		color = new int[size];
		power = new int[size];
		dir = new int[size];
		item = new int[size];
		cellfront = new int[size];
		btype = new int[size];
		isick = new boolean[size];
		hwing = new boolean[size];
		activeopatt = new boolean[size];
		myattpow = new int[size];
		opattpow = new int[size];
		startPlat(world);
	}

	private void startPlat(World world) {
		System.out.println(world.getConstants().getColorCost() + "-" + world.getConstants().getFoodValidTime() + "-"
				+ world.getConstants().getSickLifeTime() + "-" + world.getConstants().getTrashValidTime() + "-"
				+ world.getConstants().getUpdateCost() + "-" + world.getConstants().getDetMoveCost() + "-"
				+ world.getConstants().getFishFoodScore() + "-" + world.getConstants().getQueenFoodScore() + "-"
				+ world.getConstants().getKillFishScore() + "-" + world.getConstants().getKillQueenScore() + "-"
				+ world.getConstants().getSickCost() + "-" + world.getConstants().getSickLifeTime());
		myid = world.getTeamID();
		opid = 1 - myid;
		colorCost = world.getConstants().getColorCost();
		fFoodScore = world.getConstants().getFishFoodScore();
		qFoodScore = world.getConstants().getQueenFoodScore();
		fKillScore = world.getConstants().getKillFishScore();
		qKillScore = world.getConstants().getKillQueenScore();
		updateCost = world.getConstants().getUpdateCost();
		totalTurn = world.getTotalTurns();
		pr = world.getConstants().getPowerRatio();// TODO wTF
		goodForFood = fFoodScore - fKillScore;
		change = world.getConstants().getChangeTypeLimit();

		for (int i = 0; i < 18; ++i) {
			update[0][i] = 1;
			update[1][i] = 1;
			fighter[i] = 0;
		}
		System.out.println(world.getTotalTurns());
		for (int i = 0; i < size; ++i) {
			opatt[i] = new intVector();
			myatt[i] = new intVector();
			stalk[i] = 0;
			adjacencyList[i] = new intVector();
			int x = getX(i);
			int y = getY(i);
			for (int j = 0; j < 4; ++j) {
				Cell cell = world.getMap().getCell((x + colHeadDir[j] + width) % width,
						(y + rowHeadDir[j] + height) % height);
				if (cell.getTeleport() == null)
					adjacencyList[i].add(getIndex(cell));
				else
					adjacencyList[i].add(getIndex(((Teleport) cell.getTeleport()).getPair().getPosition()));
			}
		}
	}

	public int rightOf(int index) {
		return getIndex((getX(index) + colRightDir[dir[index]] + width) % width,
				(getY(index) + rowRightDir[dir[index]] + height) % height);
	}

	public int leftOf(int index) {
		return getIndex((getX(index) + colLeftDir[dir[index]] + width) % width,
				(getY(index) + rowLeftDir[dir[index]] + height) % height);
	}

	public int frontOf(int index) {
		return getIndex((getX(index) + colHeadDir[dir[index]] + width) % width,
				(getY(index) + rowHeadDir[dir[index]] + height) % height);
	}

	public int getIndex(Cell c) {
		return c.getX() + c.getY() * width;
	}

	public int getIndex(int x, int y) {
		return x + y * width;
	}

	public int getX(int index) {
		return index % width;
	}

	public int getY(int index) {
		return index / width;
	}

	public void update(World world) {
		myscore = world.getMyScore();
		opscore = world.getOppScore();
		nodeS[0].clear();
		nodeS[1].clear();
		for (int i = 0; i < size; ++i) {
			color[i] = -1;
			myatt[i].clear();
			opatt[i].clear();
			opPower.clear();
			myattpow[i] = 0;
			opattpow[i] = 0;
			activeopatt[i] = false;
		}
		for (int i = 0; i < 18; ++i) {
			avision[0][i].clear();
			avision[1][i].clear();
		}
		this.world = world;
		myscore = world.getMyScore();
		opscore = world.getOppScore();
		turn = world.getCurrentTurn();
		Cell[] cells = world.getMap().getMyCells();
		for (Cell cell : cells) {
			System.out.println(cell.getX() + "--" + cell.getY() + "--" + ((Beetle) cell.getBeetle()).getDirection());
			int i = getIndex(cell);
			Beetle b = (Beetle) cell.getBeetle();
			nodeS[myid].add(i);
			color[i] = myid;
			btype[i] = b.getBeetleType().getValue();
			isick[i] = b.is_sick();
			hwing[i] = b.has_winge();
			dir[i] = b.getDirection().getValue();
			if (power[i] == b.getPower() && dir[i] == b.getDirection().getValue())
				stalk[i]++;
			else
				stalk[i] = 0;
			dir[i] = b.getDirection().getValue();
			power[i] = b.getPower();
		}
		cells = world.getMap().getOppCells();
		for (Cell cell : cells) {
			int i = getIndex(cell);
			Beetle b = (Beetle) cell.getBeetle();
			nodeS[opid].add(i);
			color[i] = opid;
			btype[i] = b.getBeetleType().getValue();
			isick[i] = b.is_sick();
			hwing[i] = b.has_winge();
			dir[i] = b.getDirection().getValue();
			power[i] = b.getPower();
			opPower.add(power[i]);
		}
		cells = world.getMap().getMyCells();
		for (int i : nodeS[myid]) {
			int left, front = myid, right;
			if (color[getIndex((getX(i) + colFrontLeftDir[dir[i]] + width) % width,
					(getY(i) + rowFrontLeftDir[dir[i]] + height) % height)] == myid)
				left = 0;
			else if (color[getIndex((getX(i) + colFrontLeftDir[dir[i]] + width) % width,
					(getY(i) + rowFrontLeftDir[dir[i]] + height) % height)] == opid)
				left = 1;
			else
				left = 2;
			if (color[getIndex((getX(i) + colFrontRightDir[dir[i]] + width) % width,
					(getY(i) + rowFrontRightDir[dir[i]] + height) % height)] == myid)
				right = 0;
			else if (color[getIndex((getX(i) + colFrontRightDir[dir[i]] + width) % width,
					(getY(i) + rowFrontRightDir[dir[i]] + height) % height)] == opid)
				right = 1;
			else
				right = 2;
			int n, x, y;
			x = getX(i);
			y = getY(i);
			if (dir[i] == 0 || dir[i] == 2)
				n = width;
			else
				n = height;
			for (int j = 0; j < n; j++) {
				x = (x + colHeadDir[dir[i]] + width) % width;
				y = (y + rowHeadDir[dir[i]] + height) % height;
				if (color[getIndex(x, y)] == myid) {
					front = 0;
					break;
				} else if (color[getIndex(x, y)] == opid) {
					front = 1;
					break;
				}
			}
			x = (getX(i) + colHeadDir[dir[i]] + width) % width;
			y = (getY(i) + rowHeadDir[dir[i]] + height) % height;
			if (color[getIndex(x, y)] == myid)
				cellfront[i] = myid;
			else if (color[getIndex(x, y)] == opid)
				cellfront[i] = opid;
			else if (world.getMap().getCell(x, y).getItem() != null)
				// food and trash 2,3
				cellfront[i] = 1 + world.getMap().getCell(x, y).getItem().getType().getValue();
			else
				cellfront[i] = 4;
			vision[i] = 6 * left + 3 * front + right;
			avision[myid][6 * left + 3 * front + right].add(i);
			System.out.println(getX(i) + "++" + getY(i) + "++" + cellfront[i] + "++" + vision[i]);
			myatt[i].add(power[i]);
			myatt[frontOf(i)].add(power[i]);
			myattpow[i] += power[i];
			myattpow[frontOf(i)] += power[i];
		}
		for (int i : nodeS[opid]) {
			int left, front = opid, right;
			if (color[getIndex((getX(i) + colFrontLeftDir[dir[i]] + width) % width,
					(getY(i) + rowFrontLeftDir[dir[i]] + height) % height)] == myid)
				left = 1;
			else if (color[getIndex((getX(i) + colFrontLeftDir[dir[i]] + width) % width,
					(getY(i) + rowFrontLeftDir[dir[i]] + height) % height)] == opid)
				left = 0;
			else
				left = 2;
			if (color[getIndex((getX(i) + colFrontRightDir[dir[i]] + width) % width,
					(getY(i) + rowFrontRightDir[dir[i]] + height) % height)] == myid)
				right = 1;
			else if (color[getIndex((getX(i) + colFrontRightDir[dir[i]] + width) % width,
					(getY(i) + rowFrontRightDir[dir[i]] + height) % height)] == opid)
				right = 0;
			else
				right = 2;
			int n, x, y;
			x = getX(i);
			y = getY(i);
			if (dir[i] == 0 || dir[i] == 2)
				n = width;
			else
				n = height;
			for (int j = 0; j < n; j++) {
				x = (x + colHeadDir[dir[i]] + width) % width;
				y = (y + rowHeadDir[dir[i]] + height) % height;
				if (color[getIndex(x, y)] == myid) {
					front = 1;
					break;
				} else if (color[getIndex(x, y)] == opid) {
					front = 0;
					break;
				}
			}
			x = (getX(i) + colHeadDir[dir[i]] + width) % width;
			y = (getY(i) + rowHeadDir[dir[i]] + height) % height;
			if (color[getIndex(x, y)] == myid)
				cellfront[i] = myid;
			else if (color[getIndex(x, y)] == opid)
				cellfront[i] = opid;
			else if (world.getMap().getCell(x, y).getItem() != null)
				// food and trash 2,3
				cellfront[i] = 1 + world.getMap().getCell(x, y).getItem().getType().getValue();
			else
				cellfront[i] = 4;
			vision[i] = 6 * left + 3 * front + right;
			avision[opid][6 * left + 3 * front + right].add(i);
			opatt[i].add(power[i]);
			opatt[frontOf(i)].add(power[i]);
			opattpow[i] += power[i];
			opattpow[frontOf(i)] += power[i];
			activeopatt[frontOf(i)] = true;
		}
		for (int i = 0; i < size; ++i) {
			if (color[getIndex(getX(i), getY(i))] == myid)
				item[i] = myid;
			else if (color[getIndex(getX(i), getY(i))] == opid)
				item[i] = opid;
			else if (world.getMap().getCell(getX(i), getY(i)).getItem() != null)
				item[i] = world.getMap().getCell(getX(i), getY(i)).getItem().getType().getValue() + 1;
			else
				item[i] = -1;
		}
		Collections.sort(opPower);
	}

	public int frontOf(int index, int e) {
		return getIndex((getX(index) + e * colHeadDir[dir[index]] + width) % width,
				(getY(index) + e * rowHeadDir[dir[index]] + height) % height);
	}
}
