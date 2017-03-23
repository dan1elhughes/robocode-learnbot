package rv007602.danHughes.robocode;

class SingleViewer {

	public static void main(String[] args) throws Exception {

		String[] enemies = {"sample.SpinBot", "sample.SpinBot"};

		Population population = new Population(0);

		Individual individual = new Individual();
		individual.setGenotype("963,499,108,201,509,043,724");

		population.add(individual);

		Fitness.setEnemies(enemies);
		Fitness.setRoundsPerBattle(100);
		Fitness.setVisible(true);
		Fitness.initialize();

		Fitness.analyze(population, 1, 1);

		Fitness.cleanUp();

		System.exit(0);
	}
}
