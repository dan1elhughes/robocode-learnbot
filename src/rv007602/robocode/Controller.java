package rv007602.robocode;

public class Controller {

	public static void main(String[] args) throws Exception {

		int generations = 30;
		int survivors = 8;
		int populationSize = 14;
		int roundsPerBattle = 2;
		float mutationRate = 0.2f;
		float crossoverRate = 0.5f;
		String[] enemies = {"sample.SpinBot", "sample.SpinBot"};

		Fitness.setEnemies(enemies);

		Fitness.setRoundsPerBattle(roundsPerBattle);

		Fitness.setVisible(false);

		Fitness.initialize();

		Population population = new Population(populationSize);
		Fitness.analyze(population, 0, -1);

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
			Population parents = population.select(survivors);
			Population children = parents.crossover(crossoverRate);
			children.mutate(mutationRate);

			population = parents;
			population.add(children);

			Fitness.analyze(population, i, generations);

			summary.addRow(population);
			bulk.addRow(population);

			population.cullTo(populationSize);
		}

		Fitness.cleanUp();

		summary.finish();
		bulk.finish();

		System.exit(0);
	}

}
