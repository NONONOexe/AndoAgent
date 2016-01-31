package jp.ac.maslab.ando.aiwolf.client.tool;

/**
 * 実行モードです。<br>
 * デバッグ用か大会用かの列挙です。標準出力やファイル出力などのON/OFFを設定する際に使用します。
 * @author keisuke
 *
 */
public enum RunMode {
	/**
	 * デバッグモードでの実行を示します。
	 */
	DEBUG,
	/**
	 * 大会モードでの実行を示します。
	 */
	CONTEST;
}
