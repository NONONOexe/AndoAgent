package maslab.aiwolf.client;

import jp.ac.maslab.aiwolf.client.lib.Mode;

/**
 * 人狼知能用のデバッグ出力をするクラスです。
 */
public class AIWolfDebug {

	private static Mode mode = Mode.DEBUG;

	private AIWolfDebug() {
	}

	/**
	 * 指定されたオブジェクトを標準出力で表示します。
	 *
	 * @param object
	 *            表示するオブジェクト
	 */
	public static void print(Object object) {
		if (mode.equals(Mode.DEBUG)) {
			System.out.print(object);
		}
	}

	/**
	 * 指定されたオブジェクトを標準出力で表示し、改行します。
	 *
	 * @param object
	 *            表示するオブジェクト
	 */
	public static void println(Object object) {
		if (mode.equals(Mode.DEBUG)) {
			System.out.println(object);
		}
	}

	/**
	 * 指定されたフォーマットに従いオブジェクトを標準出力で表示します。
	 *
	 * @param string
	 *            出力するフォーマット
	 * @param objects
	 *            出力するオブジェクト
	 */
	public static void printf(String string, Object... objects) {
		if (mode.equals(Mode.DEBUG)) {
			System.out.printf(string, objects);
		}
	}

	/**
	 * 指定された実行モードに設定します。<br>
	 *
	 * @param mode
	 *            変更する実行モード
	 */
	public static void setMode(Mode mode) {
		AIWolfDebug.mode = mode;
	}
}
