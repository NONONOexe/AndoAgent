package maslab.aiwolf.client;

import java.util.ArrayList;
import java.util.List;

import org.aiwolf.client.lib.Topic;
import org.aiwolf.common.data.Talk;

/**
 * TalkDistributerオブジェクトを元に会話に関する情報を情報を提供します。
 */
public class ConversationOutput {

	private TalkDistributer talkDistributer;

	/**
	 * 指定されたTalkDistributerを元に会話に関する情報を提供します。
	 *
	 * @param talkDistributer
	 *            提供する情報の元となるTalkDistributerオブジェクト
	 */
	public ConversationOutput(TalkDistributer talkDistributer) {
		this.talkDistributer = talkDistributer;
	}

	/**
	 * カミングアウトをリストにして返します。
	 *
	 * @return カミングアウトのリスト
	 */
	public List<Comingout> getComingoutList() {
		List<Comingout> coList = new ArrayList<>();

		talkDistributer.getTalkList(Topic.COMINGOUT).forEach(talk -> {
			if (TalkParser.canParseComingout(talk)) {
				coList.add(TalkParser.parseComingout(talk));
			}
		});
		return coList;
	}

	/**
	 * 疑いをリストにして返します。
	 *
	 * @return 疑いのリスト
	 */
	public List<Doubt> getDoubtList() {
		List<Doubt> doubtList = new ArrayList<>();

		talkDistributer.getTalkList(Topic.ESTIMATE).forEach(talk -> {
			if (TalkParser.canParseDoubt(talk)) {
				doubtList.add(TalkParser.parseDoubt(talk));
			}
		});
		return doubtList;
	}

	// ====TalkDistributer委譲メソッド====
	public List<Talk> getTalkList(Topic topic) {
		return talkDistributer.getTalkList(topic);
	}

	public List<Talk> getTalkList(Topic... topics) {
		return talkDistributer.getTalkList(topics);
	}

}
