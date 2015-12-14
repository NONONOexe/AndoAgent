package maslab.aiwolf.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.aiwolf.client.lib.Topic;
import org.aiwolf.client.lib.Utterance;
import org.aiwolf.common.data.Talk;

/**
 * 入力された会話情報を各Topicごとに仕分けします。
 */
public final class TalkDistributer {

	private Map<Topic, List<Talk>> topicTalkMap;

	/**
	 * 新規Distributerを構築します。
	 */
	public TalkDistributer() {
		topicTalkMap = new HashMap<>();
		for (Topic topic : Topic.values()) {
			topicTalkMap.put(topic, new ArrayList<>());
		}
	}

	/**
	 * 指定されたTalkを追加します。
	 *
	 * @param talk
	 *            追加するTalk
	 */
	public void addTalk(Talk talk) {
		Utterance utterance = new Utterance(talk.getContent());

		topicTalkMap.get(utterance.getTopic()).add(talk);
	}

	/**
	 * 指定されたTopicの会話のリストを返します。
	 * @param topic 会話のTopic
	 * @return topicの会話リスト
	 */
	public List<Talk> getTalkList(Topic topic) {
		return new ArrayList<>(topicTalkMap.get(topic));
	}

	/**
	 * 指定された複数のTopicの会話のリストを連結して返します。
	 * @param topics 会話のtopic
	 * @return topicの会話リスト
	 */
	public List<Talk> getTalkList(Topic... topics) {
		List<Talk> talkList = new ArrayList<>();

		for (Topic topic : topics) {
			talkList.addAll(getTalkList(topic));
		}
		return talkList;
	}

}
