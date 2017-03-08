package gol.jnlpserver;

import gol.JaxbPersistenceService;
import gol.persistence.MarshallingService;
import gol.persistence.XmlBoardType;
import gol.persistence.XmlCell;
import gol.persistence.XmlGameOfLifeState;
import org.junit.Assert;
import org.junit.Test;

/**
 * This Class test if the own marshalling mechanism produces identical result, as the
 *  JAXB Marshaller / Unmarshaller.
 *
 * Created by Tino on 26.01.2017.
 */
public class MarshallingTest {

    private final JaxbPersistenceService jaxbPersistenceService = new JaxbPersistenceService();
    private final MarshallingService marshallingService = new MarshallingService();

    @Test public void marshallingWorks() {

        final XmlGameOfLifeState gameState = new XmlGameOfLifeState();
        gameState.setBoardType(XmlBoardType.TORUS);
        gameState.setBoardWidth(50);
        gameState.setBoardHeight(30);
        gameState.setGeneration(1234);
        final XmlCell cell = new XmlCell();
        cell.setX(1);
        cell.setY(2);
        gameState.getCells().add(cell);

        final String result = marshallingService.marshall(gameState);
        final XmlGameOfLifeState unmarshalledGameState = marshallingService.unmarshall(result);

        Assert.assertEquals(gameState, unmarshalledGameState);
    }

    @Test public void marshallingProducesSameResult() {

        final XmlGameOfLifeState gameState = new XmlGameOfLifeState();
        gameState.setBoardType(XmlBoardType.FIXED);
        gameState.setBoardWidth(40);
        gameState.setBoardHeight(20);
        gameState.setGeneration(4321);
        final XmlCell cell1 = new XmlCell();
        cell1.setX(10);
        cell1.setY(10);
        gameState.getCells().add(cell1);
        final XmlCell cell2 = new XmlCell();
        cell2.setX(11);
        cell2.setY(10);
        gameState.getCells().add(cell2);
        final XmlCell cell3 = new XmlCell();
        cell3.setX(12);
        cell3.setY(10);
        gameState.getCells().add(cell3);

        final String ownMarshallerResult = marshallingService.marshall(gameState);
        final XmlGameOfLifeState ownMarshallerGameState = marshallingService.unmarshall(ownMarshallerResult);

        final String jaxbMarshallerResult = jaxbPersistenceService.asString(gameState);
        final XmlGameOfLifeState jaxbMarshallerGameState = jaxbPersistenceService.fromString(jaxbMarshallerResult);

        Assert.assertEquals(ownMarshallerGameState, jaxbMarshallerGameState);
        Assert.assertEquals(gameState, ownMarshallerGameState);
        Assert.assertEquals(gameState, jaxbMarshallerGameState);
    }
}
