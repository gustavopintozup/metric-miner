package br.com.zupedu.metric.git;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.LogCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;

public class GitMiner {

    private final Git git;

    public GitMiner(String repo) {
        this.git = createGitObject(repo);
    }

    private Git createGitObject(String repo) {
        File gitDirectory = new File(repo + File.separator + ".git");
        try {
            Repository repository = new FileRepositoryBuilder().setGitDir(gitDirectory).build();
            return new Git(repository);
        } catch (IOException e) {
            throw new GitMinerException("Diretório " + repo + " não encontrado!");
        }
    }

    public List<GitCommit> navigateCommits() {
        LogCommand logCommand = git.log();
        logCommand.setMaxCount(150);
        try {
            Iterable<RevCommit> logs = logCommand.call();
            List<GitCommit> commits = new ArrayList<>();

            for (RevCommit rev : logs) {

                var commit = new GitCommit(rev.getId().getName(),
                        rev.getFullMessage(),
                        rev.getAuthorIdent());
                commits.add(commit);
            }
            return commits;

        } catch (GitAPIException e) {
            throw new GitMinerException("We could not retrieve the commits information: " + e.getMessage());
        }
    }

    public Ref checkout(GitCommit commit) {
        try {
            return git.checkout()
                    .setStartPoint(commit.getHash())
                    .setCreateBranch(true)
                    .setName(commit.getHash())
                    .call();
        } catch (GitAPIException e) {
            e.printStackTrace();
            throw new GitMinerException("We were unable to change revision! " + e.getMessage());
        }
    }

    private void checkingOutToMain() throws GitAPIException {
        git.checkout().setName("main").call();
    }

    private void removingAllBranchesButMain() throws GitAPIException {

        List<Ref> branches = git.branchList().call();
        Ref main = null;
        for (Ref branch : branches) {
            if (branch.getName().endsWith("main")) {
                main = branch;
                break;
            }
        }

        for (Ref branch : branches) {
            if (!branch.equals(main)) {
                git.branchDelete().setBranchNames(branch.getName()).call();
            }
        }
    }

    public void cleaningStuff() {
        try {
            checkingOutToMain();
            removingAllBranchesButMain();
        } catch (GitAPIException e) {
            e.printStackTrace();
        }

    }

    public void cleanAndBacktoHead(GitCommit commit) {
        try {
            git.checkout().setName("main").call();
            git.branchDelete().setBranchNames(commit.getHash()).call();
        } catch (GitAPIException e) {
            throw new GitMinerException(
                    "We were unable to remove the branch [" + commit.getHash() + "] " + e.getMessage());
        }
    }
}
