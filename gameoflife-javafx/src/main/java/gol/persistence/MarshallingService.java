package gol.persistence;

/**
 * Created by Tino on 26.01.2017.
 */
public class MarshallingService {

    public XmlGameOfLifeState unmarshall(final String data) {
        return new XmlGameOfLifeState();
    }

    public String marshall(final XmlGameOfLifeState gameState) {
        return String.format("%s, %d x %d", gameState.getBoardType(), gameState.getBoardWidth(), gameState.getBoardHeight());
    }
}
