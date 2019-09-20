package engines;
import org.junit.Test;

public class TestLogger {

    @Test
    public void testLogger() {
        Logger.setActivate(true);
        Logger.printLine("lol");
    }
}
