package rv007602.robocode;

import java.util.ArrayList;
import java.util.Collections;

class Population {
	private final ArrayList<Individual> individuals;

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
		return str;
	}

	private void sort() {
		(this.individuals).sort((Individual o1, Individual o2) -> o2.getFitness() - o1.getFitness());
	}

	public Individual getFittest() {
		this.sort();
		return this.individuals.get(0);
	}

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

	public Population crossover() {
		ArrayList<Individual[]> pairs = this.getPairs();
		ArrayList<Individual> children = new ArrayList<>();

		for (Individual[] pair : pairs) {
			Individual parent1 = pair[0];
			Individual parent2 = pair[1];

			Individual[] offspring = Individual.crossover(parent1, parent2);
			Collections.addAll(children, offspring);
		}

		Population pop = new Population(0);

		for (Individual child : children) {
			pop.add(child);
		}

		return pop;
	}

	private ArrayList<Individual[]> getPairs() {
		this.sort();

		ArrayList<Individual[]> pairs = new ArrayList<>();
		ArrayList<Individual> pair = new ArrayList<>();

		Individual parent;

		while (this.individuals.size() > 0) {
			int index = (int) ((Math.random() * this.individuals.size()));
			parent = individuals.get(index);
			individuals.remove(index);
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

	public void mutate(float mutationRate) {
		for (Individual individual : this.individuals) {
			if (Math.random() <= mutationRate) {
				individual.mutate();
			}
		}
	}

	public void add(Population population) {
		for (Individual individual : population.getIndividuals()) {
			this.add(individual);
		}
	}

	public ArrayList<Individual> getIndividuals() {
		return this.individuals;
	}

	public void cullTo(int populationSize) {
		this.sort();

		while (this.individuals.size() > populationSize) {
			this.individuals.remove(this.individuals.size() - 1);
		}
	}
}
