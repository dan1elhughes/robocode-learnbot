package rv007602.robocode;

import java.util.Random;

class Action {
	static final int SIT = 0;
	static final int FIRE = 1;
	static final int SNIPE = 2;
	static final int BEAR_LEFT = 3;
	static final int BEAR_RIGHT = 4;
	static final int TURN_LEFT = 5;
	static final int TURN_RIGHT = 6;
	static final int AHEAD = 7;
	static final int BACK = 8;
	static final int SPIN = 9;

	private static final Random randomSeed = new Random();
	private final int action;
	private String name;

	public Action(int action) {
		this.action = action;

		switch (action) {
			case 0:
				this.name = "SIT";
				break;
			case 1:
				this.name = "FIRE";
				break;
			case 2:
				this.name = "SNIPE";
				break;
			case 3:
				this.name = "BEAR_LEFT";
				break;
			case 4:
				this.name = "BEAR_RIGHT";
				break;
			case 5:
				this.name = "TURN_LEFT";
				break;
			case 6:
				this.name = "TURN_RIGHT";
				break;
			case 7:
				this.name = "AHEAD";
				break;
			case 8:
				this.name = "BACK";
				break;
			case 9:
				this.name = "SPIN";
				break;
		}
	}

	public Action() {
		this(Action.randomSeed.nextInt(10));
	}

	public int getAction() {
		return action;
	}

	public String getName() {
		return name;
	}
}
