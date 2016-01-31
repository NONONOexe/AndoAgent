package jp.ac.maslab.ando.aiwolf.client.data.action;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import jp.ac.maslab.ando.aiwolf.client.data.definition.Field;
import jp.ac.maslab.ando.aiwolf.client.tool.ClientSystem;
import jp.ac.maslab.ando.aiwolf.client.tool.RunMode;

/**
 * 行動のデータを処理するクラスです。
 * @author keisuke
 *
 */
public class ActiondataProcesser {
	private ArrayList<Actiondata> cache;

	/**
	 * 新規プロセッサを構築します。
	 */
	public ActiondataProcesser() {
		cache = new ArrayList<>();
	}

	/**
	 * 指定された行動データを追加します。
	 * @param actionData 追加する行動データ
	 */
	public void addActionData(Actiondata actionData) {
		cache.add(actionData);
	}

	/**
	 * 指定されたインデックスの行動データを返します。<br>
	 * ここで使用するインデックスは行動IDではなく、何番目に追加されたかで決まっています。
	 * @param index 行動データのインデックス
	 * @return 指定されたインデックスの行動データ
	 */
	public Actiondata getActionData(int index) {
		return cache.get(index);
	}

	// TODO 出力用のクラスは別で作るべき
	/**
	 * 指定されたディレクトリパスに新規csvファイルを作成し行動データを書き込みます。<br>
	 * 大会モードの場合は出力しません。
	 * @param directory csvファイルを出力するディレクトリ
	 * @throws IOException 書き込みに失敗した時に、スローされます。
	 */
	public void exportCSVFile(String directory) throws IOException {
		if (ClientSystem.getInstance().getMode().equals(RunMode.CONTEST)) {
			return;
		}
		String csvDirectoryPath = directory.concat("/csv");
		File csvDirectory = new File(csvDirectoryPath);
		if (!csvDirectory.exists()) {
			csvDirectory.mkdir();
		}
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String fileName = String.format("actiondata%s.csv", sdf.format(date));
		String filePath = csvDirectoryPath.concat(String.format("/%s", fileName));
		File csvFile = new File(filePath);
		if (!csvFile.exists()) {
			csvFile.createNewFile();
		} else {
			System.err.println("同じ名前のcsvファイルが存在しているため、ファイルを作成できませんでした");
			return;
		}
		FileWriter fileWriter = new FileWriter(csvFile);
		BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
		for (int i = 0; i < Field.values().length; i++) {
			bufferedWriter.write(Field.values()[i].toString());
			if (i < Field.values().length - 1) {
				bufferedWriter.write(",");
			} else {
				bufferedWriter.newLine();
			}
		}
		for (Actiondata actionData : cache) {
			bufferedWriter.write(actionData.convertRecord().toString());
			bufferedWriter.newLine();
		}
		bufferedWriter.close();
	}
}
