package jp.ac.maslab.ando.aiwolf.client.player.base;

/**
 * 何日の何ターン目かを保持します。
 * @author keisuke
 */
public class Time {
	/**
	 * 日付です。
	 */
	private int day;
	/**
	 * ターンです。
	 */
	private int turn;

	/**
	 * 新しく日付とターンを作成します。
	 * @param day 日付
	 * @param turn ターン
	 */
	public Time(int day, int turn) {
		this.day = day;
		this.turn = turn;
	}

	/**
	 * 日付を返します。
	 * @return 日付
	 */
	public int getDay() {
		return day;
	}

	/**
	 * ターンを返します。
	 * @return ターン
	 */
	public int getTurn() {
		return turn;
	}

	@Override
	public String toString() {
		return String.format("Day%02dTurn%02d", day, turn);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		return ((Time) obj).getDay() == this.day && ((Time) obj).getTurn() == this.turn;
	}
}
