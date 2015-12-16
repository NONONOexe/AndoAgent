package maslab.aiwolf.client;

import org.aiwolf.client.lib.Topic;
import org.aiwolf.client.lib.Utterance;
import org.aiwolf.common.data.Role;
import org.aiwolf.common.data.Talk;

/**
 * Talkオブジェクトを様々な型に変換します。<br>
 * Utterance、Comingoutに変換できます。
 */
public class TalkParser {

	private TalkParser() {
	}

	/**
	 * 指定されたTalkオブジェクトをUtterance型に変換します。
	 *
	 * @param talk
	 *            変換するTalkオブジェクト
	 * @return 変換したUtteranceオブジェクト
	 */
	public static Utterance parseUtterance(Talk talk) {
		return new Utterance(talk.getContent());
	}

	/**
	 * 指定されたTalkオブジェクトをComingout型に変換します。
	 *
	 * @param talk
	 *            変換するTalkオブジェクト
	 * @return 変換したComingoutオブジェクト
	 * @throws IllegalArgumentException
	 *             TalkオブジェクトがComingoutに変換に出来ない時にスローされます。
	 */
	public static Comingout parseComingout(Talk talk) {
		String errMsg = "そのTalkオブジェクトはComingoutに変換できません:";
		Utterance utterance = new Utterance(talk.getContent());
		if (!talk.getAgent().equals(utterance.getTarget())) {
			throw new IllegalArgumentException(
					errMsg + "agent=" + talk.getAgent() + ", target=" + utterance.getTarget());
		}
		if (!utterance.getTopic().equals(Topic.COMINGOUT)) {
			throw new IllegalArgumentException(errMsg + "topic=" + utterance.getTopic());
		}
		return new Comingout(utterance.getTalkDay(), utterance.getTalkID(), utterance.getTarget(), utterance.getRole());
	}

	/**
	 * 指定されたTalkオブジェクトがComingoutオブジェクトに変換できるかどうかを返します。
	 *
	 * @param talk
	 *            変換できるかどうかを調べるTalkオブジェクト
	 * @return talkがComingoutオブジェクトに変換できるかどうか
	 */
	public static boolean canParseComingout(Talk talk) {
		Utterance utterance = new Utterance(talk.getContent());
		return utterance.getTopic().equals(Topic.COMINGOUT) && talk.getAgent().equals(utterance.getTarget());
	}

	/**
	 * 指定されたTalkオブジェクトをDoubtオブジェクトに変換します。
	 *
	 * @param talk
	 *            変換するTalkオブジェクト
	 * @return 変換したDoubtオブジェクト
	 * @throws IllegalArgumentException
	 *             TalkオブジェクトがDoubtに変換に出来ない時にスローされます。
	 */
	public static Doubt parseDoubt(Talk talk) {
		String errMsg = "そのTalkオブジェクトはDoubtに変換できません:";
		Utterance utterance = new Utterance(talk.getContent());
		if (!utterance.getTopic().equals(Topic.ESTIMATE)) {
			throw new IllegalArgumentException(errMsg + "topic=" + utterance.getTopic());
		}
		if (!utterance.getRole().equals(Role.WEREWOLF)) {
			throw new IllegalArgumentException(errMsg + "role=" + utterance.getRole());
		}
		return new Doubt(utterance.getTalkDay(), utterance.getTalkID(), talk.getAgent(), utterance.getTarget());
	}

	/**
	 * 指定されたTalkオブジェクトがDoubtオブジェクトに変換できるかどうかを返します。
	 *
	 * @param talk
	 *            変換できるかどうかを調べるTalkオブジェクト
	 * @return talkがDoubtオブジェクトに変換できるかどうか
	 */
	public static boolean canParseDoubt(Talk talk) {
		Utterance utterance = new Utterance(talk.getContent());
		return utterance.getTopic().equals(Topic.ESTIMATE) && utterance.getTarget().equals(Role.WEREWOLF);
	}
}
