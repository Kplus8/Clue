package clueGame;

/**
 * The solution to the game - the person, room, and weapon that comprise the
 * answer
 *
 * @author Jim DeBlock
 * @author Graham Kitchenka
 * @author Brandon Verkamp
 */
public class Solution {
	private Card person, room, weapon;

	public Solution(Card person, Card room, Card weapon) {
		this.person = person;
		this.room = room;
		this.weapon = weapon;
	}

	public String getPerson() {
		return person.getCardName();
	}

	public String getRoom() {
		return room.getCardName();
	}

	public String getWeapon() {
		return weapon.getCardName();
	}

	public Card getPersonCard() {
		return person;
	}

	public Card getRoomCard() {
		return room;
	}

	public Card getWeaponCard() {
		return weapon;
	}

}
