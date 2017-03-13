package rv007602.robocode;

import robocode.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class LearnBot extends AdvancedRobot {

	private ArrayList<Trigger> triggers;
	public boolean finished = false;
	private final String override = "";

	/**
	 * Reads in a genotype and executes the main action loop of the bot.
	 */
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
//				e.printStackTrace();
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

	private String readFile() throws Exception {
		if (!this.override.isEmpty()) {
			return this.override;
		}

		File f = getDataFile("_bot_data.txt");
		BufferedReader br = new BufferedReader(new FileReader(f));
		return br.readLine();
	}

	private ArrayList<Trigger> readIn() throws Exception {
		return Individual.parse(this.readFile());
	}

	private void idle() {
		Behaviour.enqueue(this, Trigger.where(Trigger.On.IDLE, this.triggers));
		this.execute();
	}

	public void onBulletHit(BulletHitEvent event) {
		Behaviour.enqueue(this, Trigger.where(Trigger.On.BULLET_HIT, this.triggers));
		this.execute();
	}

	public void onBulletMissed(BulletMissedEvent event) {
		Behaviour.enqueue(this, Trigger.where(Trigger.On.BULLET_MISSED, this.triggers));
		this.execute();
	}

	public void onHitByBullet(HitByBulletEvent event) {
		Behaviour.enqueue(this, Trigger.where(Trigger.On.HIT_BY_BULLET, this.triggers));
		this.execute();
	}

	public void onHitRobot(HitRobotEvent event) {
		Behaviour.enqueue(this, Trigger.where(Trigger.On.HIT_ROBOT, this.triggers));
		this.execute();
	}

	public void onHitWall(HitWallEvent event) {
		Behaviour.enqueue(this, Trigger.where(Trigger.On.HIT_WALL, this.triggers));
		this.execute();
	}

	public void onScannedRobot(ScannedRobotEvent event) {
		Behaviour.enqueue(this, Trigger.where(Trigger.On.SCANNED_ROBOT, this.triggers));
		this.execute();
	}
}
