package br.com.zupedu.metric.git;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.eclipse.jgit.lib.Ref;
import org.junit.jupiter.api.Test;

public class GitMinerTest {
    @Test
    public void testName() {
        GitMiner miner = new GitMiner("/home/gustavopinto/workspace/poc-plugin-cdd");

        assertNotNull(miner);

        List<GitCommit> commits = miner.navigateCommits();

        var commit = commits.get(0);
        assertNotNull(commit);

        assertEquals("gustavo.pinto@zup.com.br", commit.getAuthorEmail());
        assertEquals("Gustavo Pinto", commit.getAuthorName());

        assertEquals(71, commits.size());
    }

    @Test
    public void testCheckingIfItChangesRevision() {
        GitMiner miner = new GitMiner("/home/gustavopinto/workspace/poc-plugin-cdd");

        List<GitCommit> commits = miner.navigateCommits();
        GitCommit firstCommit = commits.get(commits.size() - 1);
        assertNotNull(firstCommit);
        assertNotNull(firstCommit.getHash());

        Ref ref = miner.checkout(firstCommit);
        assertNotNull(ref);

        miner.cleanAndBacktoHead(firstCommit);
    }
}
