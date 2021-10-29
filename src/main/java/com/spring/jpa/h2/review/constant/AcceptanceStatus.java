package com.spring.jpa.h2.review.constant;

public enum AcceptanceStatus {
    PENDING(0),
    ACCEPTED(1),
    REJECTED(2);

    private int status;

    AcceptanceStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
