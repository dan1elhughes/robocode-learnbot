package rv007602.robocode;

import java.util.ArrayList;

class Trigger {

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

	public Trigger(On event) {
		this.event = event;
		this.name = event.toString();
	}

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

	public boolean registerAction(Action action) {
		for (Action a : actions) {
			if (a.getAction() == action.getAction()) {
				return false;
			}
		}

		this.actions.add(action);
		return true;
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
