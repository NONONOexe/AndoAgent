package jp.ac.maslab.ando.aiwolf.client.player.role;

import org.aiwolf.common.data.Agent;

import jp.ac.maslab.ando.aiwolf.client.player.base.WerewolfSide;

/**
 * 人狼の行動を定義するクラスです。
 * @author keisuke
 */
public final class Werewolf extends WerewolfSide {

	@Override
	public void dayStart() {
		super.dayStart();
	}

	@Override
	public void finish() {
		super.finish();
		System.out.println(getWerewolfLog());
	}

	@Override
	public Agent attack() {
		return null;
	}

	@Override
	public String talk() {
		return null;
	}

	@Override
	public Agent vote() {
		return null;
	}

	@Override
	public String whisper() {
		return null;
	}
}
