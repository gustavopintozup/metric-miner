package br.com.zupedu.metric;

import java.util.List;

import br.com.zupedu.metric.git.GitCommit;
import br.com.zupedu.metric.git.GitMiner;
import br.com.zupedu.metric.git.GitMinerException;
import picocli.CommandLine;
import picocli.CommandLine.Option;

public class Miner implements Runnable {

    @Option(names = { "-p",
            "--path" }, required = true, description = "Path to the project to be analyzed.")
    private String path = new String();

    public static void main(String[] args) {
        int exitCode = new CommandLine(new Miner()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public void run() {
        try {
            var gitrepo = new GitMiner(path);
            List<GitCommit> commits = gitrepo.navigateCommits();
            for (GitCommit commit : commits) {
                System.out.println(commit);
            }

        } catch (GitMinerException e) {
            e.printStackTrace();
        }
    }
}