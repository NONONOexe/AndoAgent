package maslab.aiwolf.client.act;

/**
 * 行動のIDを生成して発行します。
 */
public final class ActIDPublisher {

	private static int actID;

	private ActIDPublisher() {
		actID = -1;
	}

	/**
	 * 行動IDを発行します。
	 * @return 行動ID
	 */
	public static int getNextActID() {
		actID++;
		return actID;
	}
}
