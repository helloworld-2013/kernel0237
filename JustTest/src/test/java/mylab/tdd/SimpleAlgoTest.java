package mylab.tdd;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * @author Indra Gunawan
 */
public class SimpleAlgoTest {

    @Test
    public void testAdd() {
        SimpleAlgo testObj = new SimpleAlgo();
        int result = testObj.add(1,3);
        assertEquals(result, 4);
    }

}
