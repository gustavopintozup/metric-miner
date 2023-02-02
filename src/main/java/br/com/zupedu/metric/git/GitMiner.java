package br.com.zupedu.metric.git;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.LogCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
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

    public List<GitCommit> getCommits() {
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
            throw new GitMinerException("Nâo foi possível listar os commits: " + e.getMessage()); 
        }

    }
}
