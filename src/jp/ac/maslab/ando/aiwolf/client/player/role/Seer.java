package jp.ac.maslab.ando.aiwolf.client.player.role;

import org.aiwolf.common.data.Agent;

import jp.ac.maslab.ando.aiwolf.client.player.base.SeerBase;

/**
 * 占い師の行動を定義するクラスです。
 * @author keisuke
 */
public final class Seer extends SeerBase {

	@Override
	public void dayStart() {
	}

	@Override
	public Agent divine() {
		return null;
	}

	@Override
	public void finish() {
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