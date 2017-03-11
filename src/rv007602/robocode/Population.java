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
		return str.substring(1);
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
