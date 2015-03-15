package test.java;

import main.java.card.Card;
import main.java.card.value.SimpleValue;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by jon on 1/26/15.
 */
public class TestCard {

    @Test
    public void testCardCopyEquality() {
        Card c1 = Card.initializeVictoryCard("Test Card", 0, 1, new SimpleValue(1));
        Card c2 = Card.initializeCard(c1);

        assertTrue(c1.equals(c2));
    }
}
