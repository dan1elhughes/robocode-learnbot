package rv007602.robocode;

import java.util.concurrent.ThreadLocalRandom;

class Action {
	public enum Do {
		SIT, FIRE, SNIPE, BEAR_LEFT, BEAR_RIGHT, TURN_LEFT, TURN_RIGHT, AHEAD, BACK, SPIN
	}

	private final Do action;
	private String name;

	public Action(Do action) {
		this.action = action;
		this.name = action.toString();
	}

	public Action() {
		this(Do.values()[ThreadLocalRandom.current().nextInt(0, Do.values().length)]);
	}

	public Do getAction() {
		return action;
	}

	public String getName() {
		return name;
	}
}
