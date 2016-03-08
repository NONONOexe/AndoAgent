package jp.ac.maslab.ando.aiwolf.client.player.base;

/**
 * 戦略に対する状態です。
 * @author keisuke
 */
public enum StrategyState {
	/**
	 * 実行可能状態を示します。
	 */
	READY,
	/**
	 * 実行状態を示します。
	 */
	RUNNING,
	/**
	 * 待ち状態を示します。
	 */
	WAITING,
	/**
	 * 終了状態を示します。
	 */
	TARMINATED
}
