package br.com.zupedu.metric.git;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

public class GitMinerTest {

    @Test
    public void testName() throws Exception {
        GitMiner miner = new GitMiner("/home/gustavopinto/workspace/poc-plugin-cdd");
        assertNotNull(miner);

        var commit = miner.getCommits().get(0);
        assertNotNull(commit);

        assertEquals("gustavo.pinto@zup.com.br", commit.getAuthorEmail());
        assertEquals("Gustavo Pinto", commit.getAuthorName());
    }

}
