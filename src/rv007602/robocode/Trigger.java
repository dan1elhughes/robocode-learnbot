package rv007602.robocode;

import java.util.ArrayList;

class Trigger {

	/**
	 * The list of possible triggers a robot can receive.
	 */
	public enum On {
		IDLE,
		BULLET_HIT,
		BULLET_MISSED,
		HIT_BY_BULLET,
		HIT_ROBOT,
		HIT_WALL,
		SCANNED_ROBOT
	}

	private final On event;

	private final ArrayList<Action> actions = new ArrayList<>();

	private String name;

	/**
	 * Creates a new trigger for the given event.
	 * @param event The event.
	 */
	public Trigger(On event) {
		this.event = event;
		this.name = event.toString();
	}

	/**
	 * Returns a list of actions which should execute when a trigger fires.
	 * @param event The event which has occurred.
	 * @param triggers The list of all registered triggers.
	 * @return A list of actions.
	 */
	public static ArrayList<Action> where(On event, ArrayList<Trigger> triggers) {

		ArrayList<Action> a = new ArrayList<>();

		for (Trigger trigger : triggers) {
			if (trigger.getEvent() == event) {
				a.addAll(trigger.getActions());
				return trigger.getActions();
			}
		}

		return a;
	}

	/**
	 * Binds an action onto this trigger.
	 * @param action
	 */
	public void registerAction(Action action) {
		this.actions.add(action);
	}

	public ArrayList<Action> getActions() {
		return this.actions;
	}

	public On getEvent() {
		return event;
	}

	public String getName() {
		return name;
	}
}
