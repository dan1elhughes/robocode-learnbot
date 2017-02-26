package rv007602.robocode;

import robocode.control.BattleSpecification;
import robocode.control.BattlefieldSpecification;
import robocode.control.RobocodeEngine;
import robocode.control.RobotSpecification;
import robocode.control.events.BattleAdaptor;
import robocode.control.events.BattleCompletedEvent;
import robocode.control.events.BattleErrorEvent;
import robocode.control.events.BattleMessageEvent;

import java.io.FileWriter;
import java.util.ArrayList;

public class Fitness {

	private static String[] enemies;
	private static int roundsPerBattle;
	private static boolean visible = false;

	public static void setEnemies(String[] enemies) {
		Fitness.enemies = enemies;
	}

	public static void setRoundsPerBattle(int roundsPerBattle) {
		Fitness.roundsPerBattle = roundsPerBattle;
	}

	public static void prepare(Individual individual) throws Exception {
		String genotype = individual.getGenotype();
		FileWriter output = new FileWriter("_bot_data.txt");
		output.write(genotype);
		output.close();
	}

	public static void analyze(Population population) throws Exception {
		int i = 0;
		for (Individual individual : population.getIndividuals()) {
			System.out.println("Analyzing individual " + (++i));
			Fitness.analyze(individual);
		}
	}

	public static void analyze(Individual individual) throws Exception {
		Fitness.prepare(individual);
		int score = Fitness.getFitness();
		individual.setFitness(score);
	}

	public static int getFitness() {

		// Wrapped in an object to prevent "concurrent modification" warning
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

//			// Called when the game sends out an information message during the battle
//			public void onBattleMessage(BattleMessageEvent e) {
//				if (e.getMessage().contains("cleaning")) {
//					System.out.println("Msg> " + e.getMessage());
//				}
//			}

			// Called when the game sends out an error message during the battle
			public void onBattleError(BattleErrorEvent e) {
				System.out.println("Err> " + e.getError());
			}

		});

		// Show/hide the battle
		engine.setVisible(Fitness.visible);

		// Setup the battle
		BattlefieldSpecification battlefield = new BattlefieldSpecification(800, 600);

		// Credit to Rhys Streefland for discovering that it needs
		// an asterisk after package name for some reason
		String bots = "rv007602.robocode.LearnBot*";

		for (String enemy : Fitness.enemies) {
			bots += "," + enemy;
		}

		RobotSpecification[] selectedRobots = engine.getLocalRepository(bots);

		BattleSpecification battleSpec = new BattleSpecification(Fitness.roundsPerBattle, battlefield, selectedRobots);

		// Run the battle and wait
		engine.runBattle(battleSpec, true);

		// Cleanup
		engine.close();

		return scores.get(0);
	}

	public static void setVisible(boolean visible) {
		Fitness.visible = visible;
	}
}
