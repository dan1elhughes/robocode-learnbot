package rv007602.robocode;

import java.util.concurrent.ThreadLocalRandom;

class Action {

	/**
	 * The list of possible actions a robot can take.
	 */
	public enum Do {
		SIT, FIRE, SNIPE, BEAR_LEFT, BEAR_RIGHT, TURN_LEFT, TURN_RIGHT, AHEAD, BACK, SPIN
	}

	private final Do action;
	private String name;

	/**
	 * Create the action handler with the given action.
	 * @param action
	 */
	public Action(Do action) {
		this.action = action;
		this.name = action.toString();
	}

	/**
	 * Randomly select an action if none given.
	 */
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
