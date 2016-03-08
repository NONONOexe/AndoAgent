package jp.ac.maslab.ando.aiwolf.client.player.base;

import org.aiwolf.common.data.Agent;

/*
 * 実装戦略
 * 霊能ローラー
 */
/**
 * 村陣営の役職の行動を定義する抽象クラスです。
 * @author keisuke
 */
public abstract class VillagerSide extends RoleBase {

	/**
	 * 村陣営の役職の行動定義を作ります。
	 */
	public VillagerSide() {
	}

	@Override
	public Agent vote() {
		return null;
	}

	// ====禁止行動の定義====
	@Override
	public final Agent attack() {
		return null;
	}

	@Override
	public final String whisper() {
		return null;
	}
}
