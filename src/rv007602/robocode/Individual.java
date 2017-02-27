package rv007602.robocode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.ThreadLocalRandom;

class Individual {
	private int fitness;
	private ArrayList<Trigger> phenotype = new ArrayList<>();

	private Trigger getRandomTrigger() {
		return this.phenotype.get(ThreadLocalRandom.current().nextInt(0, phenotype.size()));
	}

	public Individual() {
		ArrayList<Trigger> phenotype = new ArrayList<>();

		Trigger idle = new Trigger(Trigger.IDLE);
		Trigger bullet_hit = new Trigger(Trigger.BULLET_HIT);
		Trigger bullet_missed = new Trigger(Trigger.BULLET_MISSED);
		Trigger hit_by_bullet = new Trigger(Trigger.HIT_BY_BULLET);
		Trigger hit_robot = new Trigger(Trigger.HIT_ROBOT);
		Trigger hit_wall = new Trigger(Trigger.HIT_WALL);
		Trigger scanned_robot = new Trigger(Trigger.SCANNED_ROBOT);

		phenotype.add(idle);
		phenotype.add(bullet_hit);
		phenotype.add(bullet_missed);
		phenotype.add(hit_by_bullet);
		phenotype.add(hit_robot);
		phenotype.add(hit_wall);
		phenotype.add(scanned_robot);

		this.phenotype = phenotype;
	}

	public static Individual[] crossover(Individual parent1, Individual parent2, float crossoverRate) {
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
		float half = length * crossoverRate;
		while (half-- > 0) {
			childTriggers1.add(triggers.remove(0));
		}
		while (triggers.size() > 0) {
			childTriggers2.add(triggers.remove(0));
		}

		childTriggers1.sort(Comparator.comparingInt(Trigger::getEvent));
		childTriggers2.sort(Comparator.comparingInt(Trigger::getEvent));

		Individual child1 = new Individual();
		child1.setPhenotype(childTriggers1);

		Individual child2 = new Individual();
		child2.setPhenotype(childTriggers2);

		return new Individual[]{parent1, parent2, child1, child2};
	}

	private ArrayList<Trigger> getPhenotype() {
		return phenotype;
	}

	private void setPhenotype(ArrayList<Trigger> phenotype) {
		this.phenotype = phenotype;
	}

	public String getBehaviour() {
		String behaviour = "";

		for (Trigger trigger : this.phenotype) {
			behaviour += trigger.getName() + "(";

			for (Action action : trigger.getActions()) {
				behaviour += action.getName() + ",";
			}

			behaviour += "), ";
		}

		return behaviour;
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
		Trigger x = this.getRandomTrigger();

		int chance = ThreadLocalRandom.current().nextInt(100);

		if (chance > 90) {
			x.removeRandomAction();
		} else {
			x.registerAction(new Action());
		}
	}

}
