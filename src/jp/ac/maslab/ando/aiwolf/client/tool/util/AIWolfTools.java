package jp.ac.maslab.ando.aiwolf.client.tool.util;

import java.util.List;
import java.util.Random;

import org.aiwolf.client.lib.Utterance;
import org.aiwolf.common.data.Agent;
import org.aiwolf.common.data.Talk;

import jp.ac.maslab.ando.aiwolf.client.data.Comingout;
import jp.ac.maslab.ando.aiwolf.client.data.Divined;

/**
 * 人狼知能で使用する便利な関数を含むクラスです。
 * @author ando
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

	/**
	 * {@code Talk}オブジェクトを{@code Divined}オブジェクトに変換します。
	 * @param talk 変換する{@code Talk}オブジェクト
	 * @return 変換した{@code Divined}オブジェクト
	 */
	public static Divined convertToDivined(Talk talk) {
		Utterance utterance = new Utterance(talk.getContent());
		Divined divined = new Divined(talk.getDay(), talk.getIdx(), talk.getAgent(), utterance.getTarget(),
				utterance.getResult());
		return divined;
	}

	/**
	 * 指定されたエージェントのリストからランダムに選択したエージェントを返します。
	 * @param agentList エージェントのリスト
	 * @return ランダムに選択されたエージェント
	 */
	public static Agent getRandomAgent(List<Agent> agentList) {
		if (agentList.isEmpty()) {
			return null;
		}
		Random rand = new Random();
		return agentList.get(rand.nextInt(agentList.size()));
	}
}
