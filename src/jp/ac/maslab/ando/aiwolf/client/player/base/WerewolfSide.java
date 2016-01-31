package jp.ac.maslab.ando.aiwolf.client.player.base;

import org.aiwolf.common.data.Agent;

/**
 * 人狼陣営の役職の行動を定義する抽象クラスです。
 * @author keisuke
 */
public abstract class WerewolfSide extends RoleBase {
	@Override
	public final Agent divine() {
		return null;
	}

	@Override
	public Agent guard() {
		return null;
	}
}
