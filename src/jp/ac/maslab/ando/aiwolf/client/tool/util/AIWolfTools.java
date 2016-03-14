package jp.ac.maslab.ando.aiwolf.client.tool.util;

import java.util.List;
import java.util.Random;

import org.aiwolf.client.lib.Utterance;
import org.aiwolf.common.data.Agent;
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

	/**
	 * 指定されたエージェントのリストからランダムに選択したエージェントを返します。
	 * @param agentList エージェントのリスト
	 * @return ランダムに選択されたエージェント
	 */
	public static Agent getRandomAgent(List<Agent> agentList) {
		Random rand = new Random();
		return agentList.get(rand.nextInt(agentList.size()));
	}
}
