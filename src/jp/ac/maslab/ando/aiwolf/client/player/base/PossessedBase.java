package jp.ac.maslab.ando.aiwolf.client.player.base;

import org.aiwolf.common.data.Agent;

/**
 * 狂人の基本行動を定義する抽象クラスです。
 * @author keisuke
 */
public abstract class PossessedBase extends WerewolfSide {
	@Override
	public final Agent attack() {
		return null;
	}

	@Override
	public final String whisper() {
		return null;
	}
}
