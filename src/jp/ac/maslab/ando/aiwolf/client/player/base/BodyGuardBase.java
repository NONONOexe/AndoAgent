package jp.ac.maslab.ando.aiwolf.client.player.base;

import org.aiwolf.common.data.Agent;

/**
 * 狩人の基本行動を定義する抽象クラスです。
 * @author keisuke
 */
public abstract class BodyGuardBase extends VillagerSide {
	@Override
	public final Agent divine() {
		return null;
	}
}
