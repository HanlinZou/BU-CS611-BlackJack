/**
 * @author Zhaoyu Yin
 * An abstract class with implement on player interface.
 * Which can be further designed. For example, gamblers
 * 		and dealers in blackjack game.
 * Provided with some supplement method to make player
 * 		functional.
 */
public abstract class Player implements Player_Interface{
	
	protected String playerName;
	protected int playerId;
	protected int teamId;
	protected String teamName;
	
	public Player(String pName, String tName, int tId) {
		setPlayerName(pName);
		setTeam(tId, tName);
	}
	
	public Player(String pName) {
		this(pName, "No team", 0);
	}
	
	public Player() {
		this("No name");
	}
	
	public void setPlayerName(String name) {
		this.playerName = name;
	}
	
	public void setPlayerId(int Id) {
		this.playerId = Id;
	}
	
	public void setTeam(int id, String s) {
		this.teamId = id;
		this.teamName = s;
	}
	
	public String getPlayerName() {
		return this.playerName;
	}
	
	public String getTeamName() {
		return this.teamName;
	}
	
	public int getPlayerId() {
		return this.playerId;
	}
	
	public int getTeamId() {
		return this.teamId;
	}
	
	public String toString() {
		String res = "\nPlayer Name: " + playerName + "\nPlayer ID: " + playerId;
		if(this.teamId != 0) {
			res += "\nPlayer's Team: " + teamName + "\nTeam ID: " + teamId;
		}
		return res;
	}
	
	public abstract boolean isNatural();
	public abstract void handReset();
}
