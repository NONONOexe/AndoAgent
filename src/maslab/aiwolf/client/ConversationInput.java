package maslab.aiwolf.client;

import java.util.List;

import org.aiwolf.common.data.Talk;

/**
 * ゲーム情報からの会話情報(Talkオブジェクトのリスト)を受け取ります。受け取った会話情報を仕分けします。
 */
public class ConversationInput {

	private int lastTalkDay;
	private int lastTalkID;
	private TalkDistributer talkDistributer;

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
	 * 会話情報を仕分けして保持しているTalkDistributerオブジェクトを返します。
	 *
	 * @return 会話情報を仕分けして保持しているオブジェクト
	 */
	public TalkDistributer getTalkDistributer() {
		return talkDistributer;
	}

}
