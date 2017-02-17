package rv007602.robocode;

import robocode.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LearnBot extends Robot {

	private ArrayList<Trigger> triggers;

	public void run() {
		this.triggers = this.readIn();

		try {
			this.init();

			while (this.getOthers() > 0) {
				this.idle();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		this.writeOut();
	}

	private ArrayList<Trigger> readIn() {

		String dataFile = "_bot_data.txt";
		List<String> lines = new ArrayList<>();

		//read file into stream, try-with-resources
		try (Stream<String> stream = Files.lines(Paths.get(dataFile))) {
			lines = stream
					.filter(line -> line != null && !line.isEmpty())
					.collect(Collectors.toList());
		} catch (IOException e) {
			e.printStackTrace();
		}

		ArrayList<Trigger> triggers = new ArrayList<>();

		for (String line : lines ) {
			String[] parts = line.split(":");
			int trigger = Integer.parseInt(parts[0], 10);
			Trigger t = new Trigger(trigger);
			System.out.println("Got trigger : " + t.getName());

			String[] actions = parts[1].split(",");
			for (String action : actions) {
				Action a = new Action(Integer.parseInt(action, 10));
				t.registerAction(a);
				System.out.println("\t : " + a.getName());
			}

			triggers.add(t);

		}

		return triggers;
	}

	private void writeOut() {

	}

	private void init() {

	}

	private void idle() {
		Behaviour.apply(this, Trigger.where(Trigger.IDLE, this.triggers));
	}

	public void onBulletHit(BulletHitEvent event) {
		Behaviour.apply(this, Trigger.where(Trigger.BULLET_HIT, this.triggers));
	}

	public void onBulletMissed(BulletMissedEvent event) {
		Behaviour.apply(this, Trigger.where(Trigger.BULLET_MISSED, this.triggers));
	}

	public void onHitByBullet(HitByBulletEvent event) {
		Behaviour.apply(this, Trigger.where(Trigger.HIT_BY_BULLET, this.triggers));
	}

	public void onHitRobot(HitRobotEvent event) {
		Behaviour.apply(this, Trigger.where(Trigger.HIT_ROBOT, this.triggers));
	}

	public void onHitWall(HitWallEvent event) {
		Behaviour.apply(this, Trigger.where(Trigger.HIT_WALL, this.triggers));
	}

	public void onScannedRobot(ScannedRobotEvent event) {
		Behaviour.apply(this, Trigger.where(Trigger.SCANNED_ROBOT, this.triggers));
	}
}
