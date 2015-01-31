package test.java;

import main.java.card.Card;
import main.java.card.value.SimpleValue;
import main.java.core.Supply;
import org.junit.*;
import static org.junit.Assert.*;

/**
 * Created by jon on 1/26/15.
 */
public class TestSupply {

    /*
    @Test
    public void testAddingCard() {
        Supply supply = new Supply();
        Card card = Card.initializeVictoryCard("Test Card", 1, new SimpleValue(1));

        supply.add(card, 1);
        assertTrue(supply.getAvailableCards().contains(card));
    }

    @Test
    public void testTakingCardUsingCopiedCard() {
        Supply supply = new Supply();
        Card originalCard = Card.initializeVictoryCard("Test Card", 1, new SimpleValue(1));
        Card copyCard = Card.initializeCard(originalCard);

        supply.add(originalCard, 1);
        assertNotNull(supply.take(copyCard));
    }

    @Test
    public void testSupplyAccessFromCopiedCards() {
        Supply supply = new Supply();
        Card originalCard = Card.initializeVictoryCard("Test Card", 1, new SimpleValue(1));
        Card copyCard = Card.initializeCard(originalCard);

        supply.add(originalCard, 1);
        assertTrue(supply.getAvailableCards().contains(copyCard));
    }

    @Test
    public void testEmptySupplyPile() {
        Supply supply = new Supply();
        Card card = Card.initializeVictoryCard("Test Card", 1, new SimpleValue(1));

        supply.add(card, 1);
        supply.take(card); // Take last card
        assertNull(supply.take(card));
    }
    */
}
