package maslab.aiwolf.client;

import java.util.List;
import java.util.Map;

import org.aiwolf.client.lib.Topic;
import org.aiwolf.common.data.Talk;

/**
 * ゲーム情報からの会話情報(Talkオブジェクトのリスト)を受け取ります。受け取った会話情報を仕分けします。
 */
public class ConversationInput {

	int lastTalkDay;
	int lastTalkID;
	TalkDistributer talkDistributer;

	/**
	 * 新しくConversationInputを構築します。
	 */
	public ConversationInput() {
		lastTalkDay = 0;
		lastTalkID = 0;
		talkDistributer = new TalkDistributer();
	}

	/**
	 * 指定された会話情報を受け取ます。
	 *
	 * @param talkList
	 *            会話情報
	 */
	public void listen(List<Talk> talkList) {
		if (lastTalkDay != talkList.get(0).getDay()) {
			lastTalkDay = talkList.get(0).getDay();
			lastTalkID = 0;
		}
		for (int i = lastTalkID; i < talkList.size(); i++) {
			Talk talk = talkList.get(i);
			talkDistributer.addTalk(talk);
		}
		lastTalkID = talkList.size();
	}

	/**
	 * 仕分けした会話情報のマップを返します。
	 *
	 * @return 仕分けした会話情報のマップ
	 */
	public Map<Topic, List<Talk>> getTalkDistributer() {
		return talkDistributer.getTopicTalkMap();
	}
}
