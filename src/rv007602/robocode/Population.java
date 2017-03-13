package rv007602.robocode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;

class Population {
	private final ArrayList<Individual> individuals;

	/**
	 * Creates a new random population of the given size.
	 *
	 * @param populationSize How many members in the population.
	 */
	public Population(int populationSize) {
		this.individuals = new ArrayList<>();

		while (populationSize-- > 0) {
			Individual i = new Individual();
			this.individuals.add(i);
		}
	}

	public String toString() {
		this.sort();

		String str = "";
		for (Individual individual : individuals) {
			str += "," + individual.getFitness();
		}
		return str.substring(1);
	}

	/**
	 * Reorders the population by fitness.
	 */
	private void sort() {
		(this.individuals).sort((Individual o1, Individual o2) -> o2.getFitness() - o1.getFitness());
	}

	/**
	 * Finds the fittest individual in the population.
	 *
	 * @return The fittest population member.
	 */
	public Individual getFittest() {
		this.sort();
		return this.individuals.get(0);
	}

	/**
	 * Returns a new population containing only the fittest individuals.
	 *
	 * @param survivors The number of individuals to return
	 * @return
	 */
	public Population select(int survivors) {
		this.sort();

		Population selected = new Population(0);

		while (survivors-- > 0) {
			Individual survived = this.individuals.remove(0);
			selected.add(survived);
		}

		return selected;
	}

	private void add(Individual individual) {
		this.individuals.add(individual);
	}

	/**
	 * Performs crossover on pairs of individuals
	 * @param crossoverRate The crossover rate passed to Individual.crossover
	 * @return All offspring for the current population
	 */
	public Population crossover(float crossoverRate) {
		ArrayList<Individual[]> pairs = this.getPairs();
		ArrayList<Individual> children = new ArrayList<>();

		for (Individual[] pair : pairs) {
			Individual parent1 = pair[0];
			Individual parent2 = pair[1];

			Individual[] offspring = Individual.crossover(parent1, parent2, crossoverRate);
			Collections.addAll(children, offspring);
		}

		Population pop = new Population(0);

		for (Individual child : children) {
			pop.add(child);
		}

		return pop;
	}

	/**
	 * Pairs up all individuals by fitness. First pair is the two fittest individuals.
	 * @return Pairs of individuals
	 */
	private ArrayList<Individual[]> getPairs() {

		ArrayList<Integer> indexes = new ArrayList<>();
		for (int i = 0; i < this.individuals.size(); i++) {
			indexes.add(i);
		}
		Collections.shuffle(indexes);

		Individual parent;
		ArrayList<Individual[]> pairs = new ArrayList<>();
		ArrayList<Individual> pair = new ArrayList<>();

		while (indexes.size() > 0) {
			parent = this.individuals.get(indexes.remove(0));
			pair.add(parent);

			if (pair.size() == 2) {
				Individual[] parents = new Individual[2];
				parents[0] = pair.get(0);
				parents[1] = pair.get(1);
				pairs.add(parents);
				pair.clear();
			}
		}

		return pairs;
	}

	/**
	 * Triggers mutation on a certain percentage of the population.
	 * @param mutationRate Proportion of the population to mutate (0 to 1).
	 */
	public void mutate(float mutationRate) {
		for (Individual individual : this.individuals) {
			if (Math.random() <= mutationRate) {
				individual.mutate();
			}
		}
	}

	/**
	 * Adds all members of another population to this one.
	 * @param population The population to add.
	 */
	public void add(Population population) {
		for (Individual individual : population.getIndividuals()) {
			this.add(individual);
		}
	}

	public ArrayList<Individual> getIndividuals() {
		return this.individuals;
	}

	/**
	 * Executes fitness proportionate selection to reduce the size of the population.
	 * @param populationSize The final size of the population.
	 */
	public void cullTo(int populationSize) {
		this.sort();

		ArrayList<Integer> indexes = new ArrayList<>();

		int index = 0;

		for (Individual individual : this.individuals) {
			int fitness = individual.getFitness();
			while (fitness-- > 0) {
				indexes.add(index);
			}

			index++;
		}

		ArrayList<Individual> selected = new ArrayList<>();

		while (selected.size() < populationSize) {
			int nextIndex = ThreadLocalRandom.current().nextInt(indexes.size());

			selected.add(this.individuals.get(indexes.get(nextIndex)));
		}

		this.individuals.clear();
		this.individuals.addAll(selected);
	}

	public String getBehaviour() {
		String x = "";

		for (Individual i : this.individuals) {
			x += i.getGenotype().replace(',', '|') + ",";
		}

		return x;
	}

	public String getSummary() {
		this.sort();

		int totalFitness = 0;
		for (Individual individual : individuals) {
			totalFitness += individual.getFitness();
		}

		return this.getFittest().getFitness() + "," + totalFitness / this.individuals.size();
	}
}
