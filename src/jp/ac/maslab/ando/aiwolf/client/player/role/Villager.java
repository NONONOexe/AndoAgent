package jp.ac.maslab.ando.aiwolf.client.player.role;

import org.aiwolf.common.data.Agent;

import jp.ac.maslab.ando.aiwolf.client.player.base.VillagerBase;

/**
 * 村人の行動を定義するクラスです。
 * @author keisuke
 */
public final class Villager extends VillagerBase {
	@Override
	public void dayStart() {
		super.dayStart();
	}

	@Override
	public void finish() {
		super.finish();
	}

	@Override
	public String talk() {
		return null;
	}

	@Override
	public Agent vote() {
		return null;
	}
}
