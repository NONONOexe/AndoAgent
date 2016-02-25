/*
 * 2016/02/10 作成
 */
package jp.ac.maslab.ando.aiwolf.client.tool;
/**
 * デバッグ用の出力です。
 * @author keisuke
 *
 */
public class DebugPrinter {
	/**
	 * デバッグモードの時に指定されたオブジェクトを標準出力に表示します。
	 * @param string 出力したいオブジェクト
	 */
	public static void println(Object obj) {
		if (ClientSystem.getInstance().getMode().equals(RunMode.DEBUG)) {
			System.out.println(obj);
		}
	}
}
