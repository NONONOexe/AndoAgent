package maslab.aiwolf.client;

import java.util.List;

import org.aiwolf.client.lib.Topic;
import org.aiwolf.common.data.Talk;

public class ConversationIO {

	private ConversationInput conversationInput;
	private ConversationOutput conversationOutput;

	public ConversationIO() {
		conversationInput = new ConversationInput();
		conversationOutput = new ConversationOutput(conversationInput.getTalkDistributer());
	}

	// ====ConversationInput委譲メソッド====
	public void listen(List<Talk> talkList) {
		conversationInput.listen(talkList);
	}

	// ====ConversationOutput委譲メソッド====
	public List<Comingout> getComingoutList() {
		return conversationOutput.getComingoutList();
	}

	public List<Doubt> getDoubtList() {
		return conversationOutput.getDoubtList();
	}

	public List<Talk> getTalkList(Topic topic) {
		return conversationOutput.getTalkList(topic);
	}

	public List<Talk> getTalkList(Topic... topics) {
		return conversationOutput.getTalkList(topics);
	}

}
