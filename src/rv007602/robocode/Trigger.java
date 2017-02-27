package rv007602.robocode;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

class Trigger {

	public static final int IDLE = 0;
	public static final int BULLET_HIT = 1;
	public static final int BULLET_MISSED = 2;
	public static final int HIT_BY_BULLET = 3;
	public static final int HIT_ROBOT = 4;
	public static final int HIT_WALL = 5;
	public static final int SCANNED_ROBOT = 6;

	private final int event;

	private final ArrayList<Action> actions = new ArrayList<>();

	private String name;

	public Trigger() {
		this(ThreadLocalRandom.current().nextInt(0, 7));
	}

	public Trigger(int event) {
		this.event = event;

		switch (event) {
			case 0:
				this.name = "IDLE";
				break;
			case 1:
				this.name = "BULLET_HIT";
				break;
			case 2:
				this.name = "BULLET_MISSED";
				break;
			case 3:
				this.name = "HIT_BY_BULLET";
				break;
			case 4:
				this.name = "HIT_ROBOT";
				break;
			case 5:
				this.name = "HIT_WALL";
				break;
			case 6:
				this.name = "SCANNED_ROBOT";
				break;
		}
	}

	public static ArrayList<Action> where(int event, ArrayList<Trigger> triggers) {
		for (Trigger trigger : triggers) {
			if (trigger.getEvent() == event) {
				System.out.println("Triggered: " + trigger.getName());
				return trigger.getActions();
			}
		}

		return new ArrayList<>();
	}

	public void registerAction(Action action) {
		for (Action a : actions) {
			if (a.getAction() == action.getAction()) {
				return;
			}
		}

		this.actions.add(action);
	}

	public ArrayList<Action> getActions() {
		return this.actions;
	}

	public void clearActions() {
		this.actions.clear();
	}

	public int getEvent() {
		return event;
	}

	public String getName() {
		return name;
	}

	public void removeRandomAction() {
		ArrayList<Action> actions = this.getActions();

		int size = actions.size();

		if (size > 0) {
			int index = ThreadLocalRandom.current().nextInt(actions.size());
			Action a = actions.remove(index);

			System.out.println("Removed action: " + a.getName());

			actions.trimToSize();
		}

	}
}
