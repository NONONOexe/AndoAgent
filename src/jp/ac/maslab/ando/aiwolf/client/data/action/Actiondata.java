package jp.ac.maslab.ando.aiwolf.client.data.action;

/**
 * 行動データのインターフェースです。
 * @author keisuke
 */
public interface ActionData {
	/**
	 * Recordオブジェクトに変換します。
	 * @return 変換したRecordオブジェクト
	 */
	public Record convertRecord();
}
