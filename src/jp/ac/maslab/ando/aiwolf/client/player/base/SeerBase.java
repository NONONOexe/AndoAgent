package jp.ac.maslab.ando.aiwolf.client.player.base;

import org.aiwolf.common.data.Agent;

/**
 * 占い師の基本行動を定義する抽象クラスです。
 * @author keisuke
 */
public abstract class SeerBase extends VillagerSide {
	@Override
	public final Agent guard() {
		return null;
	}
}
