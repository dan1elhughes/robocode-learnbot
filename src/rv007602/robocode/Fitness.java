package rv007602.robocode;

import robocode.control.BattleSpecification;
import robocode.control.BattlefieldSpecification;
import robocode.control.RobocodeEngine;
import robocode.control.RobotSpecification;
import robocode.control.events.BattleAdaptor;
import robocode.control.events.BattleCompletedEvent;
import robocode.control.events.BattleErrorEvent;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

public class Fitness {

	private static String[] enemies;
	private static int roundsPerBattle;
	private static boolean visible = false;
	private static final ArrayList<Integer> score = new ArrayList<>();
	private static RobocodeEngine engine;
	private static RobotSpecification[] selectedRobots;
	private static BattleSpecification battleSpec;
	private static String bots;

	static void setEnemies(String[] enemies) {
		Fitness.enemies = enemies;
	}

	static void setRoundsPerBattle(int roundsPerBattle) {
		Fitness.roundsPerBattle = roundsPerBattle;
	}

	private static void prepare(Individual individual) throws Exception {
		String genotype = individual.getGenotype();

		File f = new File("out\\production\\Robocode\\rv007602\\robocode\\LearnBot.data\\_bot_data.txt");

		f.getParentFile().mkdirs();

		FileWriter output = new FileWriter(f);
		output.write(genotype);
		output.flush();
		output.close();
		Fitness.score.clear();
	}

	static void analyze(Population population, int generation) throws Exception {
		int i = 0;
		for (Individual individual : population.getIndividuals()) {
			System.out.println("Analyzing individual " + (++i) + " of " + generation);
			System.out.println(individual.getBehaviour());
			Fitness.analyze(individual);
		}
	}

	private static void analyze(Individual individual) throws Exception {
		Fitness.prepare(individual);
		int score = Fitness.getFitness();
		individual.setFitness(score);
	}

	static void initialize() {

		// Disable log messages from Robocode
//		RobocodeEngine.setLogMessagesEnabled(false);

		// Create the RobocodeEngine
		Fitness.engine = new RobocodeEngine(new File("c:\\robocode"));

		// Add our own battle listener to the RobocodeEngine
		Fitness.engine.addBattleListener(new BattleAdaptor() {
			// Called when the battle is completed successfully with battle results
			public void onBattleCompleted(BattleCompletedEvent e) {
				for (robocode.BattleResults battleResult : e.getSortedResults()) {
					if (battleResult.getTeamLeaderName().equals("rv007602.robocode.LearnBot*")) {
						Fitness.score.add(battleResult.getScore());
					}
				}
			}

			// Called when the game sends out an error message during the battle
			public void onBattleError(BattleErrorEvent e) {
				System.out.println("Err> " + e.getError());
			}
		});

		// Show/hide the battle
		Fitness.engine.setVisible(Fitness.visible);

		// Credit to Rhys Streefland for discovering that it needs
		// an asterisk after package name for some reason
		String bots = "rv007602.robocode.LearnBot*";

		for (String enemy : Fitness.enemies) {
			bots += "," + enemy;
		}

		Fitness.bots = bots;

	}

	static void cleanUp() {
		Fitness.engine.close();
	}

	private static int getFitness() {
		Fitness.engine.runBattle(new BattleSpecification(Fitness.roundsPerBattle, new BattlefieldSpecification(800, 600), Fitness.engine.getLocalRepository(bots)), true);

		return Fitness.score.get(0);
	}

	static void setVisible(boolean visible) {
		Fitness.visible = visible;
	}
}
