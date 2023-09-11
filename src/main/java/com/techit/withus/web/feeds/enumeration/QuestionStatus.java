package com.techit.withus.web.feeds.enumeration;

public enum QuestionStatus {
    RESOLVING(1),
    RESOLVED(2);

    private int order;

    QuestionStatus(int order) {
        this.order = order;
    }

    public int getOrder() {
        return order;
    }
}
