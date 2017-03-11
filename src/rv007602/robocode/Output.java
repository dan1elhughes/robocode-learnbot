package rv007602.robocode;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Output {

	private BufferedWriter writer;

	public static final int SUMMARY = 0;
	public static final int BULK = 1;
	private int mode;

	public Output(String filename) {
		try {
			this.writer = new BufferedWriter(new FileWriter(filename));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setMode(int mode) {
		this.mode = mode;
	}

	public void write(String str) {
		try {
			this.writer.write(str + "\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

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

	public void finish() {
		try {
			this.writer.flush();
			this.writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
