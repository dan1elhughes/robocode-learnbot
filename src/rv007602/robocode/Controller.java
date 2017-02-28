package rv007602.robocode;

import java.io.FileWriter;

public class Controller {

	public static void main(String[] args) throws Exception {

		int generations = 30;
		int survivors = 6;
		int populationSize = 10;
		float mutationRate = 0.1f;
		float crossoverRate = 0.5f;

		String[] enemies = {"sample.SittingDuck"};

		Fitness.setEnemies(enemies);

		Fitness.setRoundsPerBattle(2);

		Fitness.setVisible(false);

		Fitness.initialize();

		Population population = new Population(populationSize);
		Fitness.analyze(population, 0);

		FileWriter output = new FileWriter("out.csv");

		String headings = "Generation";

		for (int i = 0; i < populationSize; i++) {
			headings += ",FitnessOf" + (i + 1);
		}

		headings += ",Fittest";

		output.write(headings + "\n");

		output.write(String.format("%d%s,%d\n", 0, population, population.getFittest().getFitness()));

		int i = 0;
		while (i++ < generations) {
			System.out.println("== Generation " + i);
			Population nextGeneration = population.select(survivors);

			Population children = nextGeneration.crossover(crossoverRate);
			children.mutate(mutationRate);

			population = nextGeneration;
			population.add(children);

			population.cullTo(populationSize);

			Fitness.analyze(population, i);

			output.write(String.format("%d%s,%d\n", i, population, population.getFittest().getFitness()));
		}

		Fitness.cleanUp();

		output.flush();
		output.close();

		System.exit(0);
	}

}
