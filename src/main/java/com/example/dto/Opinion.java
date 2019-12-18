package com.example.dto;

public class Opinion {
    private String authorName;
    private String opinionContent;

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getOpinionContent() {
        return opinionContent;
    }

    public void setOpinionContent(String opinionContent) {
        this.opinionContent = opinionContent;
    }

    public Opinion(){}

    public Opinion(long gameId, String authorName, String opinionContent) {
        this.authorName = authorName;
        this.opinionContent = opinionContent;
    }
}
