package rv007602.robocode;

import java.lang.reflect.Method;
import java.util.ArrayList;

class Behaviour {

	/**
	 * Queues up a list of actions to be taken by the bot.
	 * @param bot The bot on which the actions will be applied.
	 * @param actions The list of actions to take.
	 */
	static void enqueue(LearnBot bot, ArrayList<Action> actions) {
		for (Action action : actions) {
			Behaviour.enqueue(bot, action);
		}
	}

	/**
	 * Queues up an action to be taken by the bot.
	 * @param bot The bot on which the action will be applied.
	 * @param action The action to take.
	 */
	private static void enqueue(LearnBot bot, Action action) {
		if (!bot.finished) {
			try {

				System.out.println("Applying behaviour: " + action.getName());

				switch (action.getAction()) {
					case SIT:
						Behaviour.sit(bot);
						break;
					case FIRE:
						Behaviour.fire(bot);
						break;
					case SNIPE:
						Behaviour.snipe(bot);
						break;
					case BEAR_LEFT:
						Behaviour.bearLeft(bot);
						break;
					case BEAR_RIGHT:
						Behaviour.bearRight(bot);
						break;
					case TURN_LEFT:
						Behaviour.turnLeft(bot);
						break;
					case TURN_RIGHT:
						Behaviour.turnRight(bot);
						break;
					case AHEAD:
						Behaviour.ahead(bot);
						break;
					case BACK:
						Behaviour.back(bot);
						break;
					case SPIN:
						Behaviour.spin(bot);
						break;
					default:
						Behaviour.sit(bot);
						break;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private static void sit(LearnBot bot) throws Exception {
		// Do nothing
	}

	private static void fire(LearnBot bot) throws Exception {
		Method method = bot.getClass().getMethod("setFire", double.class);
		method.invoke(bot, 1d);
	}

	private static void snipe(LearnBot bot) throws Exception {
		Method method = bot.getClass().getMethod("setFire", double.class);
		method.invoke(bot, 3d);
	}

	private static void bearLeft(LearnBot bot) throws Exception {
		Method method = bot.getClass().getMethod("setTurnLeft", double.class);
		method.invoke(bot, 20d);
	}

	private static void bearRight(LearnBot bot) throws Exception {
		Method method = bot.getClass().getMethod("setTurnRight", double.class);
		method.invoke(bot, 20d);
	}

	private static void turnLeft(LearnBot bot) throws Exception {
		Method method = bot.getClass().getMethod("setTurnLeft", double.class);
		method.invoke(bot, 80d);
	}

	private static void turnRight(LearnBot bot) throws Exception {
		Method method = bot.getClass().getMethod("setTurnRight", double.class);
		method.invoke(bot, 80d);
	}

	private static void ahead(LearnBot bot) throws Exception {
		Method method = bot.getClass().getMethod("setAhead", double.class);
		method.invoke(bot, 100d);
	}

	private static void back(LearnBot bot) throws Exception {
		Method method = bot.getClass().getMethod("setBack", double.class);
		method.invoke(bot, 100d);
	}

	private static void spin(LearnBot bot) throws Exception {
		Method method = bot.getClass().getMethod("setTurnLeft", double.class);
		method.invoke(bot, 180d);
	}
}
