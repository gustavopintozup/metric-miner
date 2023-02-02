package br.com.zupedu.metric;

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
            System.out.println("Teste miner: " + path);
        } catch (RuntimeException e) {
            System.out.println("[ERROR] Bla bla");
        }
    }
}