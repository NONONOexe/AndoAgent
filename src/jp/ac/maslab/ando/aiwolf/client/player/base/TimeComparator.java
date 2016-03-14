package jp.ac.maslab.ando.aiwolf.client.player.base;

import java.util.Comparator;

/**
 * TimeをソートするためのComparatorです。
 * @author keisuke
 */
public class TimeComparator implements Comparator<Time> {

	@Override
	public int compare(Time time1, Time time2) {
		if (time2.getDay() < time1.getDay()) {
			return 1;
		} else if (time2.getDay() == time1.getDay()) {
			if (time2.getTurn() < time1.getTurn()) {
				return 1;
			} else if (time2.getTurn() == time1.getTurn()) {
				return 0;
			} else {
				return -1;
			}
		} else {
			return -1;
		}
	}

}
