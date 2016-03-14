package jp.ac.maslab.ando.aiwolf.client.player.base;

/**
 * 時間の管理を行います。
 * @author keisuke
 */
public class TimeManager {
	/**
	 * 現在の時間です。
	 */
	private Time current;

	/**
	 * 新しく時間の管理を行うオブジェクトを構築します。
	 */
	public TimeManager() {
		current = new Time(-1, -1);
	}

	/**
	 * 時間を次の日に進めます。
	 */
	public void moveNextDay() {
		current = new Time(current.getDay() + 1, -1);
	}

	/**
	 * 時間を次のターンに進めます。
	 */
	public void moveNextTurn() {
		current = new Time(current.getDay(), current.getTurn() + 1);
	}

	/**
	 * 現在の時間を返します。
	 * @return 現在の時間
	 */
	public Time getCurrent() {
		return current;
	}
}
