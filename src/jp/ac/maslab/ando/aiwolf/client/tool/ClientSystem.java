package jp.ac.maslab.ando.aiwolf.client.tool;

/**
 * クライアントの実行モードを管理します。<br>
 * このクラスはSingletonパターンが適用されています。
 * @author keisuke
 *
 */
public class ClientSystem {
	private ClientSystem() {
	}

	private static class ClientSystemHolder {
		private static final ClientSystem instance = new ClientSystem();
	}

	/**
	 * ClientSystemのインスタンスを返します。
	 * @return ClientSystemのインスタンス
	 */
	public static synchronized ClientSystem getInstance() {
		return ClientSystemHolder.instance;
	}

	private RunMode mode = RunMode.DEBUG;

	/**
	 * 指定された実行モードに変更します。
	 * @param mode 実行モード
	 */
	public void setMode(RunMode mode) {
		this.mode = mode;
	}

	/**
	 * 現在の実行モードを返します。
	 * @return 実行モード
	 */
	public RunMode getMode() {
		return mode;
	}
}
