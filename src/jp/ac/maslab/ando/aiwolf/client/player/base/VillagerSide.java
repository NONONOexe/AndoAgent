package jp.ac.maslab.ando.aiwolf.client.player.base;

import org.aiwolf.common.data.Agent;

/**
 * 村陣営の役職の行動を定義する抽象クラスです。
 * @author keisuke
 */
public abstract class VillagerSide extends RoleBase {
	@Override
	public final Agent attack() {
		return null;
	}
	@Override
	public final String whisper() {
		return null;
	}
}
