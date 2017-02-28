package rv007602.robocode;

import java.lang.reflect.Method;
import java.util.ArrayList;

class Behaviour {

	static void apply(LearnBot bot, ArrayList<Action> actions) {
		for (Action action : actions) {
			Behaviour.apply(bot, action);
		}
	}

	private static void apply(LearnBot bot, Action action) {
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
		Method method = bot.getClass().getSuperclass().getSuperclass().getSuperclass().getSuperclass().getDeclaredMethod("doNothing");
		method.invoke(bot);
	}

	private static void fire(LearnBot bot) throws Exception {
		Method method = bot.getClass().getSuperclass().getSuperclass().getSuperclass().getSuperclass().getDeclaredMethod("fire", double.class);
		method.invoke(bot, 1d);
	}

	private static void snipe(LearnBot bot) throws Exception {
		Method method = bot.getClass().getSuperclass().getSuperclass().getSuperclass().getSuperclass().getDeclaredMethod("fire", double.class);
		method.invoke(bot, 3d);
	}

	private static void bearLeft(LearnBot bot) throws Exception {
		Method method = bot.getClass().getSuperclass().getSuperclass().getSuperclass().getSuperclass().getDeclaredMethod("turnLeft", double.class);
		method.invoke(bot, 20d);
	}

	private static void bearRight(LearnBot bot) throws Exception {
		Method method = bot.getClass().getSuperclass().getSuperclass().getSuperclass().getSuperclass().getDeclaredMethod("turnRight", double.class);
		method.invoke(bot, 20d);
	}

	private static void turnLeft(LearnBot bot) throws Exception {
		Method method = bot.getClass().getSuperclass().getSuperclass().getSuperclass().getSuperclass().getDeclaredMethod("turnLeft", double.class);
		method.invoke(bot, 80d);
	}

	private static void turnRight(LearnBot bot) throws Exception {
		Method method = bot.getClass().getSuperclass().getSuperclass().getSuperclass().getSuperclass().getDeclaredMethod("turnRight", double.class);
		method.invoke(bot, 80d);
	}

	private static void ahead(LearnBot bot) throws Exception {
		Method method = bot.getClass().getSuperclass().getSuperclass().getSuperclass().getSuperclass().getDeclaredMethod("ahead", double.class);
		method.invoke(bot, 100d);
	}

	private static void back(LearnBot bot) throws Exception {
		Method method = bot.getClass().getSuperclass().getSuperclass().getSuperclass().getSuperclass().getDeclaredMethod("back", double.class);
		method.invoke(bot, 100d);
	}

	private static void spin(LearnBot bot) throws Exception {
		Method method = bot.getClass().getSuperclass().getSuperclass().getSuperclass().getSuperclass().getDeclaredMethod("turnLeft", double.class);
		method.invoke(bot, 180d);
	}
}
