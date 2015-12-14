package maslab.aiwolf.client;

import org.aiwolf.client.lib.Topic;
import org.aiwolf.client.lib.Utterance;
import org.aiwolf.common.data.Talk;

/**
 * Talkオブジェクトを様々な型に変換します。<br>
 * Utterance、Comingoutに変換できます。
 */
public class Parser {

	private Parser() {
	}

	/**
	 * 指定されたTalkオブジェクトをUtterance型に変換します。
	 *
	 * @return 変換したオブジェクト
	 * @throws IllegalArgumentException Talkオブジェクトがカミングアウトに出来ない時にスローされます。
	 */
	public static Utterance parseUtterance(Talk talk) {
		return new Utterance(talk.getContent());
	}

	public static Comingout parseComingout(Talk talk) {
		Utterance utterance = new Utterance(talk.getContent());
		if (talk.getAgent().equals(utterance.getTarget())) {
			throw new IllegalArgumentException("そのTalkオブジェクトは発言者と対象が異なるためカミングアウトにできません");
		}
		if (!utterance.getTopic().equals(Topic.COMINGOUT)) {
			throw new IllegalArgumentException("そのTalkオブジェクトはカミングアウトではありません");
		}
		return new Comingout(utterance.getTalkDay(), utterance.getTalkID(), utterance.getTarget(), utterance.getRole());
	}
}
