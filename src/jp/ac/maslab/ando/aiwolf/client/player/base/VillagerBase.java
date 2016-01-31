package jp.ac.maslab.ando.aiwolf.client.player.base;

import org.aiwolf.common.data.Agent;

/**
 * 村人の基本行動を定義する抽象クラスです。
 * @author keisuke
 */
public abstract class VillagerBase extends VillagerSide {
	@Override
	public final Agent divine() {
		return null;
	}

	@Override
	public final Agent guard() {
		return null;
	}
}
