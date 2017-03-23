package rv007602.danHughes.robocode;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

class Output {

	private BufferedWriter writer;

	public static final int SUMMARY = 0;
	public static final int BULK = 1;
	private int mode;

	/**
	 * Create a new output object pointing at the given filename.
	 * @param filename Where to write.
	 */
	public Output(String filename) {
		try {
			this.writer = new BufferedWriter(new FileWriter(filename));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Sets the mode of this outputter.
	 * @param mode Either Output.SUMMARY or Output.BULK.
	 */
	public void setMode(int mode) {
		this.mode = mode;
	}

	private void write(String str) {
		try {
			this.writer.write(str + "\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Creates CSV headings in the file.
	 * @param populationSize The size of the population being operated on.
	 */
	public void addHeadings(int populationSize) {
		switch (this.mode) {
			case Output.SUMMARY:
				this.write("Peak,Average");
				break;
			case Output.BULK:
				String str = "";
				for (int i = 0; i < populationSize; i++) {
					str += "FitnessOf" + i + ",";
				}
				for (int i = 0; i < populationSize; i++) {
					str += "GenotypeOf" + i + ",";
				}
				this.write(str);
				break;
		}
	}

	/**
	 * Adds an output row with the data from the given population.
	 * @param population The population being operated on.
	 */
	public void addRow(Population population) {
		switch (this.mode) {
			case Output.SUMMARY:
				this.write(population.getSummary());
				break;
			case Output.BULK:
				this.write(population.toString() + "," + population.getBehaviour());
				break;
		}
	}

	/**
	 * Safely close the output object.
	 */
	public void finish() {
		try {
			this.writer.flush();
			this.writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
