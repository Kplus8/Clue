package clueGame;

/**
 * Represents a card in the game
 *
 * @author Jim DeBlock
 * @author Graham Kitchenka
 * @author Brandon Verkamp
 */
public class Card {
	private String cardName;
	private CardType ct;

	public Card(String cardName, CardType ct) {
		this.cardName = cardName;
		this.ct = ct;
	}

	/**
	 * Returns cardName
	 * 
	 * @return String
	 */
	public String getCardName() {
		return cardName;
	}

	/**
	 * Returns card type
	 * 
	 * @return CardType
	 */
	public CardType getCardType() {
		return ct;
	}

	@Override
	public boolean equals(Object obj) {
		Card c = (Card) obj;
		return c.getCardName().equals(this.getCardName())
				&& c.getCardType().equals(this.getCardType());
	}
}
