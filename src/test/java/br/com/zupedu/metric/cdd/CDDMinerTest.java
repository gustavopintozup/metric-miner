package br.com.zupedu.metric.cdd;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import br.com.zupedu.metric.git.GitMiner;

public class CDDMinerTest {
    private GitMiner gitminer;
    @BeforeEach
    void beforeEach() {
        gitminer = new GitMiner("/home/gustavopinto/workspace/poc-plugin-cdd");
    }

    @AfterEach
    void afterEach() {
        gitminer.cleaningStuff();
    }
    
    @Test
    public void testName() throws Exception {
        var cdd = new CDDMiner("/home/gustavopinto/workspace/poc-plugin-cdd/src/main/java");
        assertNull("null", cdd.compute());
    }

    @Test
    public void testName2() throws Exception {
        var cdd = new CDDMiner("/home/gustavopinto/workspace/poc-plugin-cdd/src/main/java");
        assertNotNull("null", cdd.compute());

        var commits = gitminer.navigateCommits();
        var commit = commits.get(commits.size() - 10);

        gitminer.checkout(commit);
        assertNotNull("null", cdd.compute());
    }

    @Test
    @Disabled
    public void testComputingCDDForFirstAndLastCommits() throws Exception {
        var cdd = new CDDMiner("/home/gustavopinto/workspace/poc-plugin-cdd");
        

        var commits = gitminer.navigateCommits();
        
        var almostLastCommit = commits.get(1);
        var firstCommit = commits.get(commits.size() - 10);

        assertEquals("4ed1b7703abdf27b27495da2c9e11664458911d0", firstCommit.getHash());
        assertEquals("9835da431c4691ab9102663d9329350ffe1856c7", almostLastCommit.getHash());        
        
        gitminer.checkout(almostLastCommit);
        assertNull("null", cdd.compute());

        gitminer.cleaningStuff();

        gitminer.checkout(firstCommit);
        assertNotNull("null", cdd.compute());

    }
}
