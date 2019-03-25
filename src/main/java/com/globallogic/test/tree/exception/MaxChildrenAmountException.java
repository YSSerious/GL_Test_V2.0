package com.globallogic.test.tree.exception;

public class MaxChildrenAmountException extends RuntimeException {
    public MaxChildrenAmountException(String message){
        super(message);
    }
}
