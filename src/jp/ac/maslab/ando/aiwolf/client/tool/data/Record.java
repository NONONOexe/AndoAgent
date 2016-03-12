package jp.ac.maslab.ando.aiwolf.client.tool.data;

import static jp.ac.maslab.ando.aiwolf.client.data.definition.Field.*;

import java.util.EnumMap;
import java.util.Map;

import jp.ac.maslab.ando.aiwolf.client.data.definition.DataIDIssuer;
import jp.ac.maslab.ando.aiwolf.client.data.definition.Field;

/**
 * データを整理するためのテーブルのレコードのクラスです。
 * @author keisuke
 */
public class Record {

	private Map<Field, Object> data;

	/**
	 * 新規レコードを構築します。
	 */
	public Record() {
		data = new EnumMap<>(Field.class);
		data.put(DATA_ID, DataIDIssuer.getID());
	}

	/**
	 * このレコードの指定されたフィールドの値を返します。
	 * @param field 取得したい値のフィールド
	 * @return 指定されたフィールドの値
	 */
	public Object getValue(Field field) {
		return data.get(field);
	}

	/**
	 * 指定されたフィールドに関連付けて値を設定します。
	 * @param field 値を関連付けるフィールド
	 * @param value フィールドに設定する値
	 */
	public void setValue(Field field, Object value) {
		data.put(field, value);
	}

	@Override
	public String toString() {
		String str = "";
		for (int i = 0; i < Field.values().length; i++) {
			Object value = data.get(Field.values()[i]);
			if (value != null) {
				str += value.toString();
			}
			if (i != Field.values().length - 1) {
				str += ",";
			}
		}
		return str;
	}
}
