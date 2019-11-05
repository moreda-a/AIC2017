package moreda.strategies;

import java.util.Random;

import moreda.Strategy;
import moreda.intVector;

public class Strategy_base extends Strategy {
	int countfl = 0, countff = 0, countfr = 0, countnl = 0, countnf = 0, countnr = 0;
	intVector acountfl = new intVector(), acountff = new intVector(), acountfr = new intVector(),
			acountnl = new intVector(), acountnf = new intVector(), acountnr = new intVector();

	private int myid;
	private int opid;
	boolean nem, ner, nel, bnem, bner, bnel;

	public Strategy_base() {
		super("str_base");
	}

	public Strategy_base(String name) {
		super(name);
	}

	@Override
	public void run() {
		runfirst();
	}

	@Override
	protected void runfirst() {
		myid = plat.myid;
		opid = 1 - myid;
		// first idea
		for (int i = 0; i < 18; ++i)
			if (plat.avision[myid][i].size() > 0) {
				int counta = 0, countb = 0, countc = 0, countd = 0;
				countfl = 0;
				countff = 0;
				countfr = 0;
				countnl = 0;
				countnf = 0;
				countnr = 0;
				acountfl.clear();
				acountff.clear();
				acountfr.clear();
				acountnl.clear();
				acountnf.clear();
				acountnr.clear();

				for (int j : plat.avision[myid][i])
					whattodo(j);
				counta = countfr + countfl;
				countb = countff;
				countc = countnr + countnl;
				countd = countnf;

				System.out.println("IS: " + i + ":" + counta + " " + countb + " " + countc + " " + countd);
				// -----------------------------------------------------------------------------
				// Both-------------------------------------------------------------------------
				// Turn-------------------------------------------------------------------------
				// -----------------------------------------------------------------------------
				if (countb == 0 && countd == 0) {
					// if We have turn right and turn left
					if (Math.min(countfl + countnl, countfr + countnr) != 0) {
						// HIGH cost On Changing plans
						// Almost 3
						System.out.println("BTURN: " + Math.min(countfl + countnr, countfr + countnl));
						if (Math.min(countfl + countnr, countfr + countnl) > 3
								|| plat.change <= Math.min(countfl + countnr, countfr + countnl)) {
							if (countfr >= countfl) {
								if (countfr != 0 && plat.update[plat.fighter[i]][i] != 0) {
									addOrder(plat.fighter[i], i, 0);
									plat.update[plat.fighter[i]][i] = 0;
								}
							} else {
								if (plat.update[plat.fighter[i]][i] != 2) {
									addOrder(plat.fighter[i], i, 2);
									plat.update[plat.fighter[i]][i] = 2;
								}
							}
							if (countnr >= countnl) {
								if (countnr != 0 && plat.update[1 - plat.fighter[i]][i] != 0) {
									addOrder(1 - plat.fighter[i], i, 0);
									plat.update[1 - plat.fighter[i]][i] = 0;
								}
							} else {
								if (plat.update[1 - plat.fighter[i]][i] != 2) {
									addOrder(1 - plat.fighter[i], i, 2);
									plat.update[1 - plat.fighter[i]][i] = 2;
								}
							}
						}
						// OK cost on Changing plans
						// tell again that we have both
						else {
							// if F: L, N: R
							if (countfl + countnr >= countfr + countnl) {
								if (plat.update[plat.fighter[i]][i] != 2) {
									addOrder(plat.fighter[i], i, 2);
									plat.update[plat.fighter[i]][i] = 2;
								}
								for (int j : acountnl) {
									addOrder(j, plat.fighter[i]);
									plat.change--;
								}

								if (plat.update[1 - plat.fighter[i]][i] != 0) {
									addOrder(1 - plat.fighter[i], i, 0);
									plat.update[1 - plat.fighter[i]][i] = 0;
								}
								for (int j : acountfr) {
									addOrder(j, 1 - plat.fighter[i]);
									plat.change--;
								}
							}
							// if F: R, N: L
							else {
								if (plat.update[plat.fighter[i]][i] != 0) {
									addOrder(plat.fighter[i], i, 0);
									plat.update[plat.fighter[i]][i] = 0;
								}
								for (int j : acountnr) {
									addOrder(j, plat.fighter[i]);
									plat.change--;
								}

								if (plat.update[1 - plat.fighter[i]][i] != 2) {
									addOrder(1 - plat.fighter[i], i, 2);
									plat.update[1 - plat.fighter[i]][i] = 2;
								}
								for (int j : acountfl) {
									addOrder(j, 1 - plat.fighter[i]);
									plat.change--;
								}
							}
						}
					}
					// here we have just one turn right or left
					else {
						// it is Right
						if (countfl + countnl == 0) {
							if ((countnr != 0) && plat.update[1 - plat.fighter[i]][i] != 0) {
								addOrder(1 - plat.fighter[i], i, 0);
								plat.update[1 - plat.fighter[i]][i] = 0;
							}
							// Don't do any fucking change color
							if (countfr != 0) {
								if (plat.update[plat.fighter[i]][i] != 0) {
									addOrder(plat.fighter[i], i, 0);
									plat.update[plat.fighter[i]][i] = 0;
								}
							}
							// else {
							// for (int j : acountfr)
							// addOrder(j, 1 - plat.fighter[i]);
							// }
						}
						// it is Left
						else {
							if ((countnl != 0) && plat.update[1 - plat.fighter[i]][i] != 2) {
								addOrder(1 - plat.fighter[i], i, 2);
								plat.update[1 - plat.fighter[i]][i] = 2;
							}
							// Don't do any fucking change color
							if (countfl != 0) {
								if (plat.update[plat.fighter[i]][i] != 2) {
									addOrder(plat.fighter[i], i, 2);
									plat.update[plat.fighter[i]][i] = 2;
								}
							}
							// else {
							// for (int j : acountfl)
							// addOrder(j, 1 - plat.fighter[i]);
							// }
						}
					}
				}
				// ------------------------------------------------------------------
				// go----------------------------------------------------------------
				// fight-------------------------------------------------------------
				// ------------------------------------------------------------------
				else if (counta == 0 && countc == 0) {
					// change no fighter needed
					// TODO SOME SHIT HERE TOO DONT CHANGE COLOR
					// 2
					if (countd >= 1) {
						if (plat.update[1 - plat.fighter[i]][i] != 1) {
							addOrder(1 - plat.fighter[i], i, 1);
							plat.update[1 - plat.fighter[i]][i] = 1;
						}
					}
					// just move an idiot fighter
					else {
						for (int j : acountnf) {
							addOrder(j, plat.fighter[i]);
							plat.change--;
						}
					}

					if (plat.update[plat.fighter[i]][i] != 1) {
						if (countb > 0 || countd == 1) {
							addOrder(plat.fighter[i], i, 1);
							plat.update[plat.fighter[i]][i] = 1;
						}
					}
				}
				// ------------------------------------------------------------------
				// FIGHT-------------------------------------------------------------
				// TURN--------------------------------------------------------------
				// ------------------------------------------------------------------
				else {
					int hazine = Math.min(countnf + counta, countff + countc);
					System.out.println("HAZINE: " + hazine);
					if (hazine < 6 && hazine <= plat.change) {
						if (countnf + counta < countff + countc) {
							if (plat.update[plat.fighter[i]][i] != 1) {
								if (countb + countd > 0) {
									addOrder(plat.fighter[i], i, 1);
									plat.update[plat.fighter[i]][i] = 1;
								}
							}
							for (int j : acountnf) {
								addOrder(j, plat.fighter[i]);
								plat.change--;
							}

							if (counta + countc > 0) {
								if (countfr + countnr >= countfl + countnl) {
									if (plat.update[1 - plat.fighter[i]][i] != 0) {
										addOrder(1 - plat.fighter[i], i, 0);
										plat.update[1 - plat.fighter[i]][i] = 0;
									}
									for (int j : acountfr) {
										addOrder(j, 1 - plat.fighter[i]);
										plat.change--;
									}
									for (int j : acountfl)
										if (plat.wm[j] < plat.wr[j]) {
											addOrder(j, 1 - plat.fighter[i]);
											plat.change--;
										}
								} else {
									if (plat.update[1 - plat.fighter[i]][i] != 2) {
										addOrder(1 - plat.fighter[i], i, 2);
										plat.update[1 - plat.fighter[i]][i] = 2;
									}
									for (int j : acountfl) {
										addOrder(j, 1 - plat.fighter[i]);
										plat.change--;
									}
									for (int j : acountfr)
										if (plat.wm[j] < plat.wl[j]) {
											addOrder(j, 1 - plat.fighter[i]);
											plat.change--;
										}
								}
							}
						} else {
							if (plat.update[1 - plat.fighter[i]][i] != 1) {
								if (countb + countd > 0) {
									addOrder(1 - plat.fighter[i], i, 1);
									plat.update[1 - plat.fighter[i]][i] = 1;
								}
							}
							for (int j : acountff) {
								addOrder(j, 1 - plat.fighter[i]);
								plat.change--;
							}

							if (counta + countc > 0) {
								if (countfr + countnr >= countfl + countnl) {
									if (plat.update[plat.fighter[i]][i] != 0) {
										addOrder(plat.fighter[i], i, 0);
										plat.update[plat.fighter[i]][i] = 0;
									}
									for (int j : acountnr) {
										addOrder(j, plat.fighter[i]);
										plat.change--;
									}
									for (int j : acountnl)
										if (plat.wm[j] < plat.wr[j]) {
											addOrder(j, plat.fighter[i]);
											plat.change--;
										}
								} else {
									if (plat.update[plat.fighter[i]][i] != 2) {
										addOrder(plat.fighter[i], i, 2);
										plat.update[plat.fighter[i]][i] = 2;
									}
									for (int j : acountnl) {
										addOrder(j, plat.fighter[i]);
										plat.change--;
									}
									for (int j : acountnr)
										if (plat.wm[j] < plat.wl[j]) {
											addOrder(j, plat.fighter[i]);
											plat.change--;
										}
								}
							}
						}
					} else {
						if (countff >= countfr && countff >= countfl) {
							if (plat.update[plat.fighter[i]][i] != 1) {
								if (countb > 0) {
									addOrder(plat.fighter[i], i, 1);
									plat.update[plat.fighter[i]][i] = 1;
								}
							}
						} else if (countfr > countff && countfr >= countfl) {
							if (plat.update[plat.fighter[i]][i] != 0) {
								if (countfr > 0) {
									addOrder(plat.fighter[i], i, 0);
									plat.update[plat.fighter[i]][i] = 0;
								}
							}
						} else {
							if (plat.update[plat.fighter[i]][i] != 2) {
								if (countfl > 0) {
									addOrder(plat.fighter[i], i, 2);
									plat.update[plat.fighter[i]][i] = 2;
								}
							}
						}

						if (countnf >= countnr && countnf >= countnl) {
							if (plat.update[1 - plat.fighter[i]][i] != 1) {
								if (countd > 0) {
									addOrder(1 - plat.fighter[i], i, 1);
									plat.update[1 - plat.fighter[i]][i] = 1;
								}
							}
						} else if (countnr > countnf && countnr >= countnl) {
							if (plat.update[1 - plat.fighter[i]][i] != 0) {
								if (countnr > 0) {
									addOrder(1 - plat.fighter[i], i, 0);
									plat.update[1 - plat.fighter[i]][i] = 0;
								}
							}
						} else {
							if (plat.update[1 - plat.fighter[i]][i] != 2) {
								if (countnl > 0) {
									addOrder(1 - plat.fighter[i], i, 2);
									plat.update[1 - plat.fighter[i]][i] = 2;
								}
							}
						}
					}
				}
			}

	}

	private void whattodo(int j) {
		globalcalc(j);
		if (!nem)
			plat.wm[j] -= 3;
		else if (!bnem)
			plat.wm[j] -= 1;
		if (!ner)
			plat.wr[j] -= 3;
		else if (!bner)
			plat.wr[j] -= 1;
		if (!nel)
			plat.wl[j] -= 3;
		else if (!bnel)
			plat.wl[j] -= 1;
		int i = plat.vision[j];
		int bb = plat.btype[j];
		if (bb == plat.fighter[i]) {
			// want turn right
			// calcWantright(j);return;
			// want turn left
			// calcWantleft(j);return;
			// want move
			// calcWantmove(j);return;
			if (plat.wm[j] >= plat.wr[j] && plat.wm[j] >= plat.wl[j]) {
				acountff.add(j);
				++countff;
			} else if (plat.wr[j] >= plat.wl[j]) {
				acountfr.add(j);
				++countfr;
			} else {
				acountfl.add(j);
				++countfl;
			}
		} else {
			// want turn right
			// calcWantright(j);return;
			// want turn left
			/// calcWantleft(j);return;
			// want move
			// calcWantmove(j);return;
			if (plat.wm[j] >= plat.wr[j] && plat.wm[j] >= plat.wl[j]) {
				acountnf.add(j);
				++countnf;
			} else if (plat.wr[j] >= plat.wl[j]) {
				acountnr.add(j);
				++countnr;
			} else {
				acountnl.add(j);
				++countnl;
			}
		}
		System.out.println("GG:" + plat.wm[j] + "-" + plat.wr[j] + "-" + plat.wl[j]);

	}

	private void globalcalc(int j) {
		int i = plat.vision[j];
		int bb = plat.btype[j];
		nel = true;
		nem = true;
		ner = true;
		bnel = true;
		bnem = true;
		bner = true;

		System.out.println("Move Status: " + j + "-");
		// Default is turn right
		// if trash front
		if (plat.item[plat.frontOf(j)] == 3) {
			// TODO handle sometimes go on trashes
			System.out.println("SEEN TRASH FRONT-");

			int enemyf = 0;
			for (int e = 2; e < 6; ++e)
				if (plat.color[plat.frontOf(j, e)] == opid)
					++enemyf;
			if ((plat.nodeS[opid].size() * 2 <= plat.nodeS[myid].size()
					&& (plat.opPower.size() != 0 && plat.power[j] < plat.opPower.get(plat.opPower.size() - 1) / 2)
					&& plat.turn > 25)
					|| ((!plat.hwing[j] || new Random().nextInt() % 2 == 0) && plat.turn < 8 && enemyf > 0
							&& plat.color[plat.frontOf(j, 2)] != opid && plat.nodeS[myid].size() > 4)
					|| (plat.hwing[j] && plat.myscore > plat.opscore + plat.qKillScore
							&& ((plat.opPower.size() == 0) || ((double) plat.opPower.get(plat.opPower.size() - 1)
									* (double) plat.pr >= plat.power[j])))) {
				move(j);
				return;
			} else
				nem = false;
			// go
			// right(j);
			// return;
		}
		if (plat.item[plat.rightOf(j)] == 3) {
			// TODO handle sometimes go on trashes
			System.out.println("SEEN TRASH RIGHT-");
			bner = false;
			// go
		}
		if (plat.item[plat.leftOf(j)] == 3) {
			// TODO handle sometimes go on trashes
			System.out.println("SEEN TRASH LEFT-");
			bnel = false;
			// go
		}

		// no trash front
		// else
		{
			// if fight in front
			if (nem && plat.opatt[plat.frontOf(j)].size() > 0) {
				if ((double) plat.myattpow[plat.frontOf(j)] > (double) plat.pr
						* (double) plat.opattpow[plat.frontOf(j)]) {
					move(j);
					return;
				} else if (plat.color[plat.frontOf(j)] == myid && (plat.myattpow[plat.frontOf(j)] != 0)
						&& ((double) (plat.myattpow[plat.frontOf(j)] - plat.power[j])
								* (double) plat.pr < (double) plat.opattpow[plat.frontOf(j)])
						&& ((double) (plat.myattpow[plat.frontOf(j)])
								* (double) plat.pr >= (double) plat.opattpow[plat.frontOf(j)])) {
					move(j);
					return;
				}
				// I CAN BLOCK
				else if ((double) plat.myattpow[plat.frontOf(j)]
						* (double) plat.pr >= (double) plat.opattpow[plat.frontOf(j)]) {
					// go
				} else {
					// there is bad fight brother don't move here!
					nem = false;
					// go
				}
			}
			// if fight right
			if (ner && plat.opatt[plat.rightOf(j)].size() > 0) {
				if ((double) plat.myattpow[plat.rightOf(j)] + plat.power[j] > (double) plat.pr
						* (double) (1 + plat.opattpow[plat.rightOf(j)])) {
					if (plat.activeopatt[plat.rightOf(j)]) {
						right(j);
						return;
					} else {
						// stalked
						if (plat.color[plat.rightOf(j)] == opid
								&& ((double) (plat.myattpow[plat.frontOf(plat.rightOf(j))])
										* (double) plat.pr >= (double) plat.opattpow[plat.frontOf(plat.rightOf(j))])
								&& ((double) (plat.myattpow[plat.frontOf(plat.rightOf(j))]) <= (double) plat.pr
										* (double) plat.opattpow[plat.frontOf(plat.rightOf(j))])) {
							right(j);
							return;
						} else {
							// go he will been destroyed
							bner = false;
						}
					}
				} else {
					// not good to turn right
					bner = false;
					// go
				}
			}
			// if fight left
			if (nel && plat.opatt[plat.leftOf(j)].size() > 0) {
				if ((double) plat.myattpow[plat.leftOf(j)] + plat.power[j] > (double) plat.pr
						* (double) (1 + plat.opattpow[plat.leftOf(j)])) {
					if (plat.activeopatt[plat.leftOf(j)]) {
						left(j);
						return;
					} else {
						// stalked
						if (plat.color[plat.leftOf(j)] == opid
								&& ((double) (plat.myattpow[plat.frontOf(plat.leftOf(j))])
										* (double) plat.pr >= (double) plat.opattpow[plat.frontOf(plat.leftOf(j))])
								&& ((double) (plat.myattpow[plat.frontOf(plat.leftOf(j))]) <= (double) plat.pr
										* (double) plat.opattpow[plat.frontOf(plat.leftOf(j))])) {
							left(j);
							return;
						} else {
							// go he will been destroyed
							bnel = false;
						}
					}
				} else {
					// not good to turn left
					bnel = false;
					// go
				}
			}
			{
				if (plat.item[plat.frontOf(j)] != 2) {
					// handle some times doesn't like food
					// if food is good for us
					if (plat.goodForFood > 0) {
						// TODO NEVER HAPPEN
						// QUEEN ?
						if (plat.hwing[j]) {
							// May I eat ?
							if (plat.qFoodScore - plat.qKillScore > 0) {
								// if food right or left
								if (plat.item[plat.rightOf(j)] == 2 || plat.item[plat.leftOf(j)] == 2) {
									// TODO handle and no body want it in our
									// team
									// and enemy
									// team
									// TODO handle which one is better
									if (plat.item[plat.rightOf(j)] == 2) {
										right(j);
										return;
									} else {
										left(j);
										return;
									}
								} else {
									move(j);
									return;
								}
							} else {
								// TODO expand here: nothing

								{
									if (plat.update[bb][i] == 1) {
										move(j);
										return;

									} else {
										// TODO shit 0!?
										if (plat.power[j] < 8 || (plat.opPower.size() != 0
												&& plat.power[j] >= (double) plat.pr * (double) plat.opPower.get(0))) {
											move(j);
											return;
										} else {
											if (plat.update[bb][i] == 0) {
												right(j);
												return;
											} else {
												left(j);
												return;
											}
										}
									}
								}
							}
						} else {
							if (plat.fFoodScore - plat.fKillScore > 0) {
								// if food right or left
								if (plat.item[plat.rightOf(j)] == 2 || plat.item[plat.leftOf(j)] == 2) {
									// TODO handle and no body want it in our
									// team
									// and enemy
									// team
									// TODO handle which one is better
									if (plat.item[plat.rightOf(j)] == 2) {
										right(j);
										return;
									} else {
										left(j);
										return;
									}
								} else {
									move(j);
									return;
								}
							} else {
								// TODO expand here: nothing
								// TODO some times eat shit
								{
									if (plat.update[bb][i] == 1) {
										move(j);
										return;

									} else {
										// TODO shit 0!?
										if (plat.power[j] < 8 || (plat.opPower.size() != 0
												&& plat.power[j] >= (double) plat.pr * (double) plat.opPower.get(0))) {
											move(j);
											return;
										} else {
											if (plat.update[bb][i] == 0) {
												right(j);
												return;
											} else {
												left(j);
												return;
											}
										}
									}
								}
							}
						}
					} else {
						// ALWAYS!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
						// HERE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
						// TODO expand here: nothing
						System.out.println("P WAS HERE-");
						{
							if (plat.item[plat.rightOf(j)] == 3 && plat.hwing[j]
									&& plat.myscore > plat.opscore + plat.qKillScore
									&& ((plat.opPower.size() == 0)
											|| ((double) plat.opPower.get(plat.opPower.size() - 1)
													* (double) plat.pr >= plat.power[j]))) {
								right(j);
								return;
							}
							if (plat.item[plat.leftOf(j)] == 3 && plat.hwing[j]
									&& plat.myscore > plat.opscore + plat.qKillScore
									&& ((plat.opPower.size() == 0)
											|| ((double) plat.opPower.get(plat.opPower.size() - 1)
													* (double) plat.pr >= plat.power[j]))) {
								left(j);
								return;
							}

							if (plat.update[bb][i] == 1) {

								System.out.println("--------STALKED: " + plat.stalk[j]);
								if (plat.color[plat.frontOf(j)] == myid && plat.stalk[j] > 2
										&& plat.stalk[plat.frontOf(j)] > 2 && plat.opattpow[plat.frontOf(j)] == 0) {
									System.out.println("SEX Vahshi");
									nem = false;
									bnem = false;
									if (plat.power[j] < 8
											|| (plat.opPower.size() != 0 && ((double) plat.power[j] >= (double) plat.pr
													* (double) plat.opPower.get(0)
													|| (double) plat.power[j]
															* (double) plat.pr <= (double) plat.opPower
																	.get(plat.opPower.size() - 1)))) {
										move(j);
										return;
									} else {
										if (plat.update[bb][i] == 0) {
											right(j);
											bnem = false;
											return;
										} else {
											left(j);
											bnem = false;
											return;
										}
									}
								}
								move(j);
								return;
							} else {
								if (plat.power[j] < 8 || (plat.opPower.size() != 0
										&& ((double) plat.power[j] >= (double) plat.pr * (double) plat.opPower.get(0)
												|| (double) plat.power[j] * (double) plat.pr <= (double) plat.opPower
														.get(plat.opPower.size() - 1)))) {
									move(j);
									return;
								} else {
									if (plat.update[bb][i] == 0) {
										right(j);
										bnem = false;
										return;
									} else {
										left(j);
										bnem = false;
										return;
									}
								}
							}
						}
					}
				}
				// food is front of us
				else {
					if (plat.goodForFood > 0) {
						// TODO NEVER HAPPEN
						// QUEEN ?
						if (plat.hwing[j]) {
							// May I eat ?
							if (plat.qFoodScore - plat.qKillScore > 0) {
								move(j);
								return;
							} else {
								// TODO some times eat shit
								if (plat.turn < 5) {
									move(j);
									return;
								} else { // if food right or left
									if (plat.item[plat.rightOf(j)] == 2 && plat.item[plat.leftOf(j)] == 2) {
										// TODO handle and no body want it in
										// our
										// team
										// and enemy
										// team
										// TODO handle which one is better
										// TODO in ... now
										move(j);
										return;
									} else if (plat.item[plat.rightOf(j)] == 2) {
										left(j);
										return;
									} else {
										right(j);
										return;
									}
								}
							}
						} else {
							if (plat.fFoodScore - plat.fKillScore > 0) {
								move(j);
								return;
							} else {
								// TODO some times eat shit
								if (plat.turn < 5) {
									move(j);
									return;
								} else {
									// if food right or left
									if (plat.item[plat.rightOf(j)] == 2 && plat.item[plat.leftOf(j)] == 2) {
										// TODO handle and no body want it in
										// our
										// team
										// and enemy
										// team
										// TODO handle which one is better
										// TODO in ... now
										move(j);
										return;
									} else if (plat.item[plat.rightOf(j)] == 2) {
										left(j);
										return;
									} else {
										right(j);
										return;
									}
								}
							}
						}
					} else {
						// ALWAYS!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
						// HERE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
						// TODO some times eat shit
						// if it was early and too much safeF
						// if it was not early and safe
						System.out.println("K WAS XXX-");
						int x0 = plat.getX(j), y0 = plat.getY(j);
						boolean flag2 = true, flag4 = true;
						for (int x1 = -2; x1 <= 2; ++x1) {
							for (int y1 = Math.abs(x1) - 2; y1 <= 2 - Math.abs(x1); ++y1)
								if (plat.color[plat.getIndex((x1 + x0 + plat.width) % plat.width,
										(y1 + y0 + plat.height) % plat.height)] == opid)
									flag2 = false;
						}
						for (int x1 = -4; x1 <= 4; ++x1) {
							for (int y1 = Math.abs(x1) - 4; y1 <= 4 - Math.abs(x1); ++y1)
								if (plat.color[plat.getIndex((x1 + x0 + plat.width) % plat.width,
										(y1 + y0 + plat.height) % plat.height)] == opid)
									flag4 = false;
						}
						// some times you need queen, some times you need grow
						// up so you must handle them
						if ((plat.turn < 1) || (plat.turn < 5 && flag2) || (plat.turn < 11 && flag4)) {
							move(j);
							return;
						} else {
							if (plat.hwing[j]) {
								nem = false;
								bnem = false;
							}
							// if food right or left
							if (plat.item[plat.rightOf(j)] == 2 && plat.item[plat.leftOf(j)] == 2) {
								// TODO handle and no body want it in our
								// team
								// and enemy
								// team
								// TODO handle which one is better
								// TODO in ... now
								move(j);
								return;
							} else if (plat.item[plat.rightOf(j)] == 2) {
								left(j);
								bnem = false;
								return;
							} else {
								right(j);
								bnem = false;
								return;
							}
						}
					}
				}
			}
		}
	}

	private void move(int j) {
		plat.wm[j] = 3;
		plat.wr[j] = 1;
		plat.wl[j] = 1;
	}

	private void right(int j) {
		plat.wm[j] = 1;
		plat.wr[j] = 3;
		plat.wl[j] = 1;
	}

	private void left(int j) {
		plat.wm[j] = 1;
		plat.wr[j] = 1;
		plat.wl[j] = 3;
	}

	@SuppressWarnings("unused")
	private void calcWantMove(int j) {
		int i = plat.vision[j];
		int bb = plat.btype[j];
		int changeCost = plat.colorCost;
		int cfront = 0;
		int fightfront = 0;
		int powup = 0;
		int runforFood = 0;
		int runforKill = 0;
		int topPower = 0;
		int botPower = 0;
		// some time we need fighter group add it
		// some time we need calc update changes add it
		if (plat.goodForFood > 0)
			runforFood += plat.goodForFood / 3;
		if (plat.opPower.size() != 0 && plat.power[j] >= plat.opPower.get(plat.opPower.size() / 2))
			runforKill = plat.fKillScore / 3;

		if (plat.opPower.size() != 0 && plat.power[j] >= plat.opPower.get(plat.opPower.size() - 1))
			topPower = plat.fKillScore / 3 + 1;
		if (plat.opPower.size() != 0 && plat.power[j] * 2 <= plat.opPower.get(plat.opPower.size() - 1))
			botPower = plat.fKillScore / 3;

		if (bb == plat.fighter[i])
			changeCost *= 1;
		else
			changeCost *= -1;

		// fight score
		if (plat.opatt[plat.frontOf(j)].size() > 0)
			if (plat.myattpow[plat.frontOf(j)] > 2 * plat.opattpow[plat.frontOf(j)])
				fightfront = plat.fKillScore;
			else if ((plat.myattpow[plat.frontOf(j)] != 0)
					&& (plat.myattpow[plat.frontOf(j)] - plat.power[j]) * 2 < plat.opattpow[plat.frontOf(j)]
					&& (plat.myattpow[plat.frontOf(j)] * 2 >= plat.opattpow[plat.frontOf(j)]))
				fightfront = plat.fKillScore / 2;

		if (plat.cellfront[j] == 2) {
			if (plat.hwing[j])
				cfront = 2 * (plat.qFoodScore - plat.qKillScore);
			else
				cfront = 2 * (plat.fFoodScore - plat.fKillScore);

		} else if (plat.cellfront[j] == 3)
			cfront = -300;

		if (plat.power[j] < 2)
			powup = 80;
		else if (plat.power[j] < 4)
			powup = 40;
		plat.wm[j] = botPower + topPower + runforFood + runforKill + fightfront + changeCost + cfront + powup;
		System.out.println("Move Status: " + j + "-" + (botPower + topPower) + "-" + (runforFood + runforKill) + "-"
				+ fightfront + "-" + changeCost + "-" + cfront + "-" + powup);
	}

	@SuppressWarnings("unused")
	private void calcWantRight(int j) {
		int i = plat.vision[j];
		int bb = plat.btype[j];
		int changeCost = plat.colorCost;
		int cfront = 0;
		int fightfront = 0;
		// some time we need fighter group add it
		// some time we need calc update changes add it
		// (plat.color[plat.rightOf(j)] != opid)
		if (bb != plat.fighter[i])
			changeCost *= 1;
		else
			changeCost *= -1;

		// fight score
		if (plat.opatt[plat.rightOf(j)].size() > 0)
			if (plat.myattpow[plat.rightOf(j)] + plat.power[j] > 2 * (1 + plat.opattpow[plat.rightOf(j)]))
				if (plat.activeopatt[plat.rightOf(j)])
					fightfront = plat.fKillScore;
				else
					fightfront = plat.fKillScore / 2;

		// turn for kill
		if (plat.color[plat.rightOf(j)] == opid)
			cfront = 70;
		// turn for food
		else if (plat.item[plat.rightOf(j)] == 2) {
			if (plat.hwing[j])
				cfront = 2 * (plat.qFoodScore - plat.qKillScore);
			else
				cfront = 2 * (plat.fFoodScore - plat.fKillScore);
		}
		plat.wr[j] = fightfront + changeCost + cfront;
	}

	@SuppressWarnings("unused")
	private void calcWantLeft(int j) {
		int i = plat.vision[j];
		int bb = plat.btype[j];
		int myid = plat.myid;
		int opid = 1 - myid;
		int changeCost = plat.colorCost;
		int cfront = 0;
		int fightfront = 0;
		// some time we need fighter group add it
		// some time we need calc update changes add it

		// fight score
		if (plat.opatt[plat.leftOf(j)].size() > 0)
			if (plat.myattpow[plat.leftOf(j)] > 2 * (1 + plat.opattpow[plat.leftOf(j)]))
				if (plat.activeopatt[plat.leftOf(j)])
					fightfront = plat.fKillScore;
				else
					fightfront = plat.fKillScore / 2;

		if (bb != plat.fighter[i])
			changeCost *= 1;
		else
			changeCost *= -1;
		if (plat.color[plat.leftOf(j)] == opid)
			cfront = 60;
		else if (plat.item[plat.leftOf(j)] == 2) {
			if (plat.hwing[j])
				cfront = 2 * (plat.qFoodScore - plat.qKillScore);
			else
				cfront = 2 * (plat.fFoodScore - plat.fKillScore);
		}
		plat.wl[j] = fightfront + changeCost + cfront - 5;
	}

}
