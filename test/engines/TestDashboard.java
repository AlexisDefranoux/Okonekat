package engines;

import bots.Marcel;
import bots.MotherBot;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class TestDashboard{

        @Test
        public void testDash() {
            MotherBot[] botList = {new Marcel(), new Marcel()};
            Dashboard dashboard = new Dashboard(false, false, false,1, botList);
            dashboard.startSimulation();
            assertEquals(2, botList.length);

        }
}
