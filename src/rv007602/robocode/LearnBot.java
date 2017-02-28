package rv007602.robocode;

import robocode.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

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
		File f = getDataFile("_bot_data.txt");
		BufferedReader br = new BufferedReader(new FileReader(f));
		return br.readLine();
	}

	private ArrayList<Trigger> readIn() throws Exception {
		return Individual.parse(this.readFile());
	}

	private void idle() {
		Behaviour.apply(this, Trigger.where(Trigger.On.IDLE, this.triggers));
	}

	public void onBulletHit(BulletHitEvent event) {
		Behaviour.apply(this, Trigger.where(Trigger.On.BULLET_HIT, this.triggers));
	}

	public void onBulletMissed(BulletMissedEvent event) {
		Behaviour.apply(this, Trigger.where(Trigger.On.BULLET_MISSED, this.triggers));
	}

	public void onHitByBullet(HitByBulletEvent event) {
		Behaviour.apply(this, Trigger.where(Trigger.On.HIT_BY_BULLET, this.triggers));
	}

	public void onHitRobot(HitRobotEvent event) {
		Behaviour.apply(this, Trigger.where(Trigger.On.HIT_ROBOT, this.triggers));
	}

	public void onHitWall(HitWallEvent event) {
		Behaviour.apply(this, Trigger.where(Trigger.On.HIT_WALL, this.triggers));
	}

	public void onScannedRobot(ScannedRobotEvent event) {
		Behaviour.apply(this, Trigger.where(Trigger.On.SCANNED_ROBOT, this.triggers));
	}
}
