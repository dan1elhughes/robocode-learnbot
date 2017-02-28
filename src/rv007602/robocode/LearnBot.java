package rv007602.robocode;

import robocode.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class LearnBot extends AdvancedRobot {

	private ArrayList<Trigger> triggers;
	public boolean finished = false;

	public void run() {

		int i = 0;

		try {
			this.triggers = this.readIn();
		} catch (Exception e) {
			e.printStackTrace();
		}

		while (!finished && i < 10000) {
			try {
				this.idle();
				System.out.println(++i);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void onDeath(DeathEvent e) {
		this.finished = true;
	}

	public void onWin(WinEvent e) {
		this.finished = true;
	}

	public void onRoundEnded(RoundEndedEvent e) {
		this.finished = true;
	}

	private ArrayList<Trigger> readIn() throws Exception {

		String dataFile = "_bot_data.txt";
		List<String> lines = new ArrayList<>();

		BufferedReader br = new BufferedReader(new FileReader(this.getDataFile(dataFile)));

		String line;

		while ((line = br.readLine()) != null) {
			if (!line.isEmpty()) {
				lines.add(line);
			}
		}

		ArrayList<Trigger> triggers = new ArrayList<>();

		for (String trig : lines) {
			String[] parts = trig.split(":");
			int trigger = Integer.parseInt(parts[0], 10);
			Trigger t = new Trigger(trigger);
			System.out.println("Got trigger : " + t.getName());

			if (trig.contains(",")) {
				String[] actions = parts[1].split(",");
				for (String action : actions) {
					if (action.length() > 0) {
						Action a = new Action(Integer.parseInt(action, 10));
						t.registerAction(a);
						System.out.println("\t : " + a.getName());
					}
				}
			}

			triggers.add(t);

		}

		return triggers;
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
