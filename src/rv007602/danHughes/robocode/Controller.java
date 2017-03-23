package rv007602.danHughes.robocode;

class Controller {

	public static void main(String[] args) throws Exception {

		int generations = 50;
		int elites = 8;
		int populationSize = 30;
		int roundsPerBattle = 3;
		float mutationRate = 0.2f;
		float crossoverRate = 0.5f;
		String[] enemies = {"sample.SpinBot", "sample.SpinBot", "sample.SpinBot", "sample.SpinBot", "sample.SpinBot"};

		Fitness.setEnemies(enemies);
		Fitness.setRoundsPerBattle(roundsPerBattle);
		Fitness.setVisible(false);
		Fitness.initialize();

		Population population = new Population(populationSize);
		Fitness.analyze(population, 0, generations);

		Output summary = new Output("summary.csv");
		Output bulk = new Output("bulk.csv");

		summary.setMode(Output.SUMMARY);
		bulk.setMode(Output.BULK);

		summary.addHeadings(populationSize);
		bulk.addHeadings(populationSize);

		summary.addRow(population);
		bulk.addRow(population);

		int i = 0;
		while (i++ < generations) {
			System.out.println("== Generation " + i);

			Population offspring = population
				.select(populationSize - elites, "roulette")
				.crossover(crossoverRate)
				.mutate(mutationRate);

			population.cullTo(elites);
			population.add(offspring);

			Fitness.analyze(population, i, generations);

			summary.addRow(population);
			bulk.addRow(population);
		}

		Fitness.cleanUp();

		summary.finish();
		bulk.finish();

		System.exit(0);
	}

}
