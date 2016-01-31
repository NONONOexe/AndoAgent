package jp.ac.maslab.ando.aiwolf.client.player.role;

import org.aiwolf.client.base.player.AbstractRoleAssignPlayer;

/**
 * 役職を割り当てて実行するプレイヤークラスです。
 * @author keisuke
 */
public final class RoleAssignPlayer extends AbstractRoleAssignPlayer {

	private String name = "ando";

	/**
	 * 新規プレイヤーを構築します。
	 */
	public RoleAssignPlayer() {
		setWerewolfPlayer(new Werewolf());
		setVillagerPlayer(new Villager());
		setBodyguardPlayer(new BodyGuard());
		setMediumPlayer(new Medium());
		setPossessedPlayer(new Possessed());
		setSeerPlayer(new Seer());
	}

	/**
	 * プレイヤー名を返します。
	 */
	@Override
	public String getName() {
		return name;
	}

}
