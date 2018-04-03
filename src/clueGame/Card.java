package clueGame;

/**
 * 
 * @author Jim DeBlock
 * @author Graham Kitchenka
 * @author Brandon Verkamp
 *
 */

public class Card {

	private String cardName;

	public Card(String cardName) {

		this.cardName = cardName;

	}

	/**
	 * Returns cardName
	 * 
	 * @return String
	 */

	public String getCardName() {

		return cardName;

	}

	@Override
	public boolean equals(Object obj) {

		Card c = (Card) obj;
		return c.getCardName().equals(this.getCardName());

	}

}
