package jp.ac.maslab.ando.aiwolf.client.tool.util;

import org.aiwolf.client.lib.Utterance;
import org.aiwolf.common.data.Talk;

import jp.ac.maslab.ando.aiwolf.client.data.Comingout;

/**
 * 人狼知能で使用する便利な関数を含むクラスです。
 * @author keisuke
 */
public class AIWolfTools {

	/**
	 * {@code Talk}オブジェクトを{@code Comingout}オブジェクトに変換します。
	 * @param talk 変換する{@code Talk}オブジェクト
	 * @return 変換した{@code Comingout}オブジェクト
	 */
	public static Comingout convertToComingout(Talk talk) {
		Utterance utterance = new Utterance(talk.getContent());
		Comingout co = new Comingout(talk.getDay(), talk.getIdx(), utterance.getTarget(), utterance.getRole());
		return co;
	}

}
