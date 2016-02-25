package jp.ac.maslab.ando.aiwolf.client.data.action;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;

import jp.ac.maslab.ando.aiwolf.client.data.definition.Field;
import jp.ac.maslab.ando.aiwolf.client.tool.ClientSystem;
import jp.ac.maslab.ando.aiwolf.client.tool.RunMode;

/**
 * 行動のデータを処理するクラスです。
 * @author keisuke
 *
 */
public class ActionDataProcesser {
	private ArrayList<ActionData> cache;

	/**
	 * 新規プロセッサを構築します。
	 */
	public ActionDataProcesser() {
		cache = new ArrayList<>();
	}

	/**
	 * 指定された行動データを追加します。
	 * @param actionData 追加する行動データ
	 */
	public void addActionData(ActionData actionData) {
		cache.add(actionData);
	}

	/**
	 * 指定されたインデックスの行動データを返します。<br>
	 * ここで使用するインデックスは行動IDではなく、何番目に追加されたかで決まっています。
	 * @param index 行動データのインデックス
	 * @return 指定されたインデックスの行動データ
	 */
	public ActionData getActionData(int index) {
		return cache.get(index);
	}

	// TODO 出力用のクラスは別で作るべき
	/**
	 * 指定されたディレクトリパスに新規csvファイルを作成し行動データを書き込みます。<br>
	 * 大会モードの場合は出力しません。
	 * @param directory csvファイルを出力するディレクトリ
	 * @throws IOException 書き込みに失敗した時に、スローされます。
	 */
	public void exportCSVFile(String pathname) throws IOException {
		if (ClientSystem.getInstance().getMode().equals(RunMode.DEBUG)) {
			File csvDirectory = new File(pathname);
			if (!csvDirectory.exists()) {
				csvDirectory.mkdirs();
			}
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			String timeString = timestamp.toString().replaceAll("[\\s-/:.]", "");
			File csvFile = new File(String.format("%s/actiondata%s.csv", pathname, timeString));
			if (!csvFile.exists()) {
				csvFile.createNewFile();
				writeActiondata(csvFile);
			} else {
				System.err.println("同じ名前のcsvファイルが存在しているため、ファイルを作成できませんでした");
			}
		}
	}

	/**
	 * 指定されたファイルに行動データを書き込みます。
	 * @param csvFile 行動データを書き込むcsv形式ファイル
	 */
	private void writeActiondata(File csvFile) {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(csvFile))) {
			// 見出しの書き込み
			for (int i = 0; i < Field.values().length; i++) {
				bw.write(Field.values()[i].toString());
				if (i < Field.values().length - 1) {
					bw.write(",");
				} else {
					bw.newLine();
				}
			}
			// データの書き込み
			for (ActionData actionData : cache) {
				bw.write(actionData.convertRecord().toString());
				bw.newLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
