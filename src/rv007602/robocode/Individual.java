package rv007602.robocode;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

class Individual {
	private int fitness;
	private String genotype;

	/**
	 * Create a new individual with a randomly generated trigger/action list.
	 */
	public Individual() {
		ArrayList<Trigger> phenotype = new ArrayList<>();

		for (int i = 0; i < Trigger.On.values().length; i++) {
			Trigger t = new Trigger(Trigger.On.values()[i]);
			for (int j = 0; j < 3; j++) {
				t.registerAction(new Action());
			}
			phenotype.add(t);
		}

		this.setPhenotype(phenotype);
	}

	/**
	 * Combines to individuals to form a pair of offspring, using uniform crossover.
	 * @param parent1 The first parent.
	 * @param parent2 The second parent.
	 * @param crossoverRate How much DNA to take from each parent. 50% gives precise 50/50 uniform crossover.
	 * @return A pair of offspring.
	 */
	public static Individual[] crossover(Individual parent1, Individual parent2, float crossoverRate) {
		String dna1 = parent1.getGenotype();
		String dna2 = parent2.getGenotype();

		for (int i = 0; i < dna1.length(); i++) {
			boolean swap = ThreadLocalRandom.current().nextFloat() >= crossoverRate;

			if (swap) {
				char oldDna1 = dna1.charAt(i);
				char oldDna2 = dna2.charAt(i);

				char[] cdna1 = dna1.toCharArray();
				cdna1[i] = oldDna2;
				dna1 = String.valueOf(cdna1);

				char[] cdna2 = dna2.toCharArray();
				cdna2[i] = oldDna1;
				dna2 = String.valueOf(cdna2);
			}
		}

		Individual child1 = new Individual();
		child1.setGenotype(dna1);

		Individual child2 = new Individual();
		child2.setGenotype(dna2);

		return new Individual[]{child1, child2};
	}

	/**
	 * Converts a genotype string into a phenotype representation.
	 * @param genotype A string of the format "XXX,XXX,XXX,XXX" where X is an action integer.
	 * @return A list of triggers with bound actions.
	 */
	public static ArrayList<Trigger> parse(String genotype) {
		ArrayList<Trigger> triggers = new ArrayList<>();

		String[] codons = genotype.split(",");
		int i = 0;
		for (String gene : codons) {
			char[] actions = gene.toCharArray();

			Trigger t = new Trigger(Trigger.On.values()[i++]);

			for (char action : actions) {
				t.registerAction(new Action(Action.Do.values()[Character.getNumericValue(action)]));
			}

			triggers.add(t);
		}

		return triggers;

	}

	/**
	 * Stores the given phenotype into the individual.
	 * @param phenotype The phenotype to store.
	 */
	private void setPhenotype(ArrayList<Trigger> phenotype) {
		String genotype = "";

		for (Trigger trigger : phenotype) {
			for (Action action : trigger.getActions()) {
				genotype += action.getAction().ordinal();
			}
			genotype += ",";
		}

		this.genotype = genotype;
	}

	public String getGenotype() {
		return this.genotype;
	}

	public int getFitness() {
		return fitness;
	}

	public void setFitness(int fitness) {
		System.out.println("fitness -> " + fitness);
		this.fitness = fitness;
	}

	/**
	 * Performs bit-string mutation of the genotype string.
	 * Swaps a random index in the genotype for a new random action.
	 */
	public void mutate() {
		String genotype = this.genotype;

		int index = ThreadLocalRandom.current().nextInt(genotype.length());

		while (genotype.toCharArray()[index] == ',') {
			index = ThreadLocalRandom.current().nextInt(genotype.length());
		}

		Action.Do newAction = Action.Do.values()[ThreadLocalRandom.current().nextInt(Action.Do.values().length)];

		StringBuilder s = new StringBuilder(genotype);

		s.setCharAt(index, Character.forDigit(newAction.ordinal(), 10));

		this.setGenotype(s.toString());
	}

	public void setGenotype(String genotype) {
		this.genotype = genotype;
	}
}
