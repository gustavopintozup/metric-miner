package br.com.zupedu.metric.cdd;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

public class CDDMinerTest {
    
    @Test
    public void testName() throws Exception {
        var cdd = new CDDMiner("/home/gustavopinto/workspace/poc-plugin-cdd/src/main/java");
        assertNotNull("null", cdd.compute());
    }
}
