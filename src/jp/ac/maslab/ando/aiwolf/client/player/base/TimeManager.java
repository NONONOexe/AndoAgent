package jp.ac.maslab.ando.aiwolf.client.player.base;

/**
 * 時間の管理を行います。
 * @author ando
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
	}

	/**
	 * 時間を次のターンに進めます。
	 */
	public void moveNextTurn(int day) {
		if (current == null || current.getDay() != day) {
			current = new Time(day, 0);
			return;
		}
		current = new Time(day, current.getTurn() + 1);
	}

	/**
	 * 現在の時間を返します。
	 * @return 現在の時間
	 */
	public Time getCurrent() {
		return current;
	}
}
