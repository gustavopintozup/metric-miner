package br.com.zupedu.metric.git;

import org.eclipse.jgit.lib.PersonIdent;

public class GitCommit {
    
    private final String hash;
    private final String message;
    private final PersonIdent author;
    
    public GitCommit(String hash, String message, PersonIdent author) {
        this.hash = hash;
        this.message = message;
        this.author = author;
    }

    public String getHash() {
        return this.hash;
    }

    public String getAuthorEmail() {
        return author.getEmailAddress();
    }

    public String getAuthorName() {
        return author.getName();
    }


    @Override
    public String toString() {
        return "["+ hash + ", " + author.getEmailAddress() + ", " + message + "]";
    }
}
