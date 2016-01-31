package jp.ac.maslab.ando.aiwolf.client.data.action;

import java.util.ArrayList;
import java.util.List;

import org.aiwolf.client.lib.Topic;
import org.aiwolf.client.lib.Utterance;
import org.aiwolf.common.data.Agent;
import org.aiwolf.common.data.Talk;
import org.aiwolf.common.data.Team;

import jp.ac.maslab.ando.aiwolf.client.data.definition.Field;
import jp.ac.maslab.ando.aiwolf.client.data.definition.TalkTag;

/**
 * 会話のデータのクラスです。
 * @author keisuke
 */
public class TalkActiondata extends Talk implements Actiondata {
	/**
	 * 新しく会話のデータを構築します。
	 * @param idx 発言ID
	 * @param day 発言した日
	 * @param agent 発言したエージェント
	 * @param content 発言の内容
	 */
	public TalkActiondata(int idx, int day, Agent agent, String content) {
		super(idx, day, agent, content);
	}

	/**
	 * 指定されたTalkオブジェクトを元に新規会話データを構築します。
	 * @param talk 元にするTalkオブジェクト
	 */
	public TalkActiondata(Talk talk) {
		super(talk.getIdx(), talk.getDay(), talk.getAgent(), talk.getContent());
	}

	@Override
	public Record convertRecord() {
		Utterance utterance = new Utterance(getContent());
		Record record = new Record();
		record.setValue(Field.DAY, getDay());
		record.setValue(Field.AGENT, getAgent());
		record.setValue(Field.DATA_TYPE, getClass().getSuperclass().getSimpleName());
		record.setValue(Field.TALK_ID, getIdx());
		record.setValue(Field.CONTENT, getContent());
		record.setValue(Field.TARGET, utterance.getTarget());
		record.setValue(Field.TALK_ROLE, utterance.getRole());
		record.setValue(Field.TOPIC, utterance.getTopic());
		record.setValue(Field.TALK_TYPE, utterance.getTalkType());
		List<TalkTag> tags = new ArrayList<>();
		tags.add(TalkTag.valueOf(utterance.getTopic() + "_TALK"));
		// TODO Util的なクラスに実装し分割すべき
		if (utterance.getTopic().equals(Topic.ESTIMATE) && utterance.getRole().getTeam().equals(Team.WEREWOLF)) {
			tags.add(TalkTag.DOUBT);
		}
		if (utterance.getTopic().equals(Topic.VOTE) && utterance.getTarget().equals(getAgent())) {
			tags.add(TalkTag.OWN_VOTE);
		}
		record.setValue(Field.ACTION_TAGS, tags);
		return record;
	}
}
