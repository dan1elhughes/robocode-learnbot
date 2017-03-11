package rv007602.robocode;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

class Individual {
	private int fitness;
	private ArrayList<Trigger> phenotype = new ArrayList<>();
	private String genotype;

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

	public static Individual[] crossover(Individual parent1, Individual parent2, float crossoverRate) {
		String dna1 = parent1.getGenotype();
		String dna2 = parent2.getGenotype();

		for (int i = 0; i < dna1.length(); i++) {
			boolean swap = ThreadLocalRandom.current().nextBoolean();

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

	public ArrayList<Trigger> getPhenotype() {
		return Individual.parse(this.genotype);
	}

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
