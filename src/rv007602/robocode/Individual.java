package rv007602.robocode;

import java.util.ArrayList;
import java.util.Collections;

public class Individual {
	private int fitness;
	private ArrayList<Trigger> phenotype = new ArrayList<>();

	public Individual() {
		ArrayList<Trigger> phenotype = new ArrayList<>();

		Trigger onIdle = new Trigger(Trigger.IDLE);
		onIdle.registerAction(new Action(Action.BEAR_LEFT));
		phenotype.add(onIdle);

		Trigger onScannedRobot = new Trigger(Trigger.SCANNED_ROBOT);
		onScannedRobot.registerAction(new Action(Action.FIRE));
		onScannedRobot.registerAction(new Action(Action.AHEAD));
		phenotype.add(onScannedRobot);

		this.phenotype = phenotype;
	}

	public static Individual[] crossover(Individual parent1, Individual parent2) {
		// TODO: This function doesn't do anything!
		// TODO: Create two blank children and pick triggers off each parent.

		ArrayList<Trigger> triggers1 = parent1.getPhenotype();
		ArrayList<Trigger> triggers2 = parent2.getPhenotype();

		ArrayList<Trigger> childTriggers1 = new ArrayList<>();
		ArrayList<Trigger> childTriggers2 = new ArrayList<>();

		// Build a list of all triggers from both parents in random order.
		ArrayList<Trigger> triggers = new ArrayList<>();
		triggers.addAll(triggers1);
		triggers.addAll(triggers2);
		Collections.shuffle(triggers);

		// Add half of the triggers to each child.
		int length = triggers.size();
		int half = length / 2;
		while (half-- > 0) {
			childTriggers1.add(triggers.remove(0));
		}
		while (triggers.size() > 0) {
			childTriggers2.add(triggers.remove(0));
		}

		Individual child1 = new Individual();
		child1.setPhenotype(childTriggers1);

		Individual child2 = new Individual();
		child2.setPhenotype(childTriggers2);

		return new Individual[]{parent1, parent2, child1, child2};
	}

	public ArrayList<Trigger> getPhenotype() {
		return phenotype;
	}

	public void setPhenotype(ArrayList<Trigger> phenotype) {
		this.phenotype = phenotype;
	}

	public String getGenotype() {
		String genotype = "";

		for (Trigger trigger : this.phenotype) {
			genotype += trigger.getEvent() + ":";

			for (Action action : trigger.getActions()) {
				genotype += action.getAction() + ",";
			}

			genotype += "\n";
		}

		return genotype;
	}

	public int getFitness() {
		return fitness;
	}

	public void setFitness(int fitness) {
		System.out.println("fitness -> " + fitness);
		this.fitness = fitness;
	}

	public void mutate() {
		ArrayList<Trigger> phenotype = this.getPhenotype();

		System.out.print(true);
	}

}
