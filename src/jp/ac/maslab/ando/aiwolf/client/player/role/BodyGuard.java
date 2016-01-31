package jp.ac.maslab.ando.aiwolf.client.player.role;

import org.aiwolf.common.data.Agent;

import jp.ac.maslab.ando.aiwolf.client.player.base.BodyGuardBase;

/**
 * 狩人の行動を定義するクラスです。
 * @author keisuke
 */
public final class BodyGuard extends BodyGuardBase {
	@Override
	public void dayStart() {
		super.dayStart();
	}

	@Override
	public void finish() {
		super.finish();
	}

	@Override
	public Agent guard() {
		return getLatestDayGameInfo().getAliveAgentList().get(0);
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
