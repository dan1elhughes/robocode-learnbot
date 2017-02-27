package rv007602.robocode;

import java.io.FileWriter;

public class Controller {

	public static void main(String[] args) throws Exception {

		int generations = 10;
		int survivors = 6;
		int populationSize = 10;
		float mutationRate = 0.5f;
		float crossoverRate = 0.5f;

		String[] enemies = {"sample.SpinBot"};

		Fitness.setEnemies(enemies);

		Fitness.setRoundsPerBattle(2);

		Fitness.setVisible(true);

		Fitness.initialize();

		Population population = new Population(populationSize);
		Fitness.analyze(population);

		FileWriter output = new FileWriter("out.csv");

		String headings = "Generation";

		for (int i = 0; i < populationSize; i++) {
			headings += ",FitnessOf" + (i + 1);
		}

		output.write(headings + "\n");

		output.write(String.format("%d%s\n", 0, population));

		int i = 1;
		while (i++ <= generations) {
			System.out.println("== Generation " + i);
			Population nextGeneration = population.select(survivors);

			Population children = nextGeneration.crossover(crossoverRate);
			children.mutate(mutationRate);

			population = nextGeneration;
			population.add(children);

			population.cullTo(populationSize);

			Fitness.analyze(population);

			output.write(String.format("%d%s\n", i, population));
			System.out.println("Generation " + i + " fittest : " + population.getFittest().getFitness());
		}

		Fitness.cleanUp();

		output.flush();
		System.exit(0);
	}

}
