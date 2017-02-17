package rv007602.robocode;

import java.lang.reflect.Method;
import java.util.ArrayList;

class Behaviour {

	static void apply(LearnBot bot, ArrayList<Action> actions) {
		for (Action action : actions) {
			Behaviour.apply(bot, action);
		}
	}

	static void apply(LearnBot bot, Action action) {
		try {

			System.out.println("Applying behaviour: " + action.getName());

			switch (action.getAction()) {
				case Action.SIT:
					Behaviour.sit(bot);
					break;
				case Action.FIRE:
					Behaviour.fire(bot);
					break;
				case Action.SNIPE:
					Behaviour.snipe(bot);
					break;
				case Action.BEAR_LEFT:
					Behaviour.bearLeft(bot);
					break;
				case Action.BEAR_RIGHT:
					Behaviour.bearRight(bot);
					break;
				case Action.TURN_LEFT:
					Behaviour.turnLeft(bot);
					break;
				case Action.TURN_RIGHT:
					Behaviour.turnRight(bot);
					break;
				case Action.AHEAD:
					Behaviour.ahead(bot);
					break;
				case Action.BACK:
					Behaviour.back(bot);
					break;
				case Action.SPIN:
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

	private static void sit(LearnBot bot) throws Exception {
		Method method = bot.getClass().getSuperclass().getDeclaredMethod("doNothing");
		System.out.println("Acting: doNothing");
		method.invoke(bot);
	}

	private static void fire(LearnBot bot) throws Exception {
		Method method = bot.getClass().getSuperclass().getDeclaredMethod("fire", double.class);
		System.out.println("Acting: fire");
		method.invoke(bot, 1d);
	}

	private static void snipe(LearnBot bot) throws Exception {
		Method method = bot.getClass().getSuperclass().getDeclaredMethod("fire", double.class);
		System.out.println("Acting: fire");
		method.invoke(bot, 3d);
	}

	private static void bearLeft(LearnBot bot) throws Exception {
		Method method = bot.getClass().getSuperclass().getDeclaredMethod("turnLeft", double.class);
		System.out.println("Acting: turnLeft");
		method.invoke(bot, 20d);
	}

	private static void bearRight(LearnBot bot) throws Exception {
		Method method = bot.getClass().getSuperclass().getDeclaredMethod("turnRight", double.class);
		System.out.println("Acting: turnRight");
		method.invoke(bot, 20d);
	}

	private static void turnLeft(LearnBot bot) throws Exception {
		Method method = bot.getClass().getSuperclass().getDeclaredMethod("turnLeft", double.class);
		System.out.println("Acting: turnLeft");
		method.invoke(bot, 80d);
	}

	private static void turnRight(LearnBot bot) throws Exception {
		Method method = bot.getClass().getSuperclass().getDeclaredMethod("turnRight", double.class);
		System.out.println("Acting: turnRight");
		method.invoke(bot, 80d);
	}

	private static void ahead(LearnBot bot) throws Exception {
		Method method = bot.getClass().getSuperclass().getDeclaredMethod("ahead", double.class);
		System.out.println("Acting: ahead");
		method.invoke(bot, 100d);
	}

	private static void back(LearnBot bot) throws Exception {
		Method method = bot.getClass().getSuperclass().getDeclaredMethod("back", double.class);
		System.out.println("Acting: back");
		method.invoke(bot, 100d);
	}

	private static void spin(LearnBot bot) throws Exception {
		Method method = bot.getClass().getSuperclass().getDeclaredMethod("turnLeft", double.class);
		System.out.println("Acting: turnLeft");
		method.invoke(bot, 180d);
	}
}
