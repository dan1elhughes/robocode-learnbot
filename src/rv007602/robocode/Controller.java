package rv007602.robocode;

import robocode.control.BattleSpecification;
import robocode.control.BattlefieldSpecification;
import robocode.control.RobocodeEngine;
import robocode.control.RobotSpecification;
import robocode.control.events.BattleAdaptor;
import robocode.control.events.BattleCompletedEvent;
import robocode.control.events.BattleErrorEvent;
import robocode.control.events.BattleMessageEvent;

import java.util.ArrayList;

public class Controller {

	private final static String[] enemies = {"sample.SittingDuck", "sample.SittingDuck"};
	private final static int roundsPerBattle = 3;

	public static void main(String[] args) {
		for (int i = 0; i < 20; i++) {
			System.out.println(i + " : " + Controller.getFitness());
		}
	}

	private static int getFitness() {

		// Wrapped in an object to prevent "concurrent modification" error
		// from the battlelistener
		ArrayList<Integer> scores = new ArrayList<>();

		// Disable log messages from Robocode
		RobocodeEngine.setLogMessagesEnabled(false);

		// Create the RobocodeEngine
		RobocodeEngine engine = new RobocodeEngine(new java.io.File("c:/robocode"));

		// Add our own battle listener to the RobocodeEngine
		engine.addBattleListener(new BattleAdaptor() {
			// Called when the battle is completed successfully with battle results
			public void onBattleCompleted(BattleCompletedEvent e) {
				for (robocode.BattleResults battleResult : e.getSortedResults()) {
					if (battleResult.getTeamLeaderName().equals("rv007602.robocode.LearnBot*")) {
						scores.add(battleResult.getScore());
					}
				}
			}

			// Called when the game sends out an information message during the battle
			public void onBattleMessage(BattleMessageEvent e) {
				if (e.getMessage().contains("cleaning")) {
					System.out.println("Msg> " + e.getMessage());
				}
			}

			// Called when the game sends out an error message during the battle
			public void onBattleError(BattleErrorEvent e) {
				System.out.println("Err> " + e.getError());
			}

		});

		// Show/hide the battle
		engine.setVisible(false);

		// Setup the battle
		BattlefieldSpecification battlefield = new BattlefieldSpecification(800, 600);

		// Credit to Rhys Streefland for discovering that it needs
		// an asterisk after package name for some reason
		String bots = "rv007602.robocode.LearnBot*";

		for (String enemy : Controller.enemies) {
			bots += "," + enemy;
		}

		RobotSpecification[] selectedRobots = engine.getLocalRepository(bots);

		BattleSpecification battleSpec = new BattleSpecification(Controller.roundsPerBattle, battlefield, selectedRobots);

		// Run the battle and wait
		engine.runBattle(battleSpec, true);

		// Cleanup
		engine.close();

		return scores.get(0);
	}
}
