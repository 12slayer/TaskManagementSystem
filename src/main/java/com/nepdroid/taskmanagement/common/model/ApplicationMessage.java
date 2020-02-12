package com.nepdroid.taskmanagement.common.model;

import com.nepdroid.taskmanagement.login.entity.AppUser;

public class ApplicationMessage {
    private boolean setSuccess;
    private String setErrorMessage;
    private AppUser data;

    public boolean isSetSuccess() {
        return setSuccess;
    }

    public void setSetSuccess(boolean setSuccess) {
        this.setSuccess = setSuccess;
    }

    public String getSetErrorMessage() {
        return setErrorMessage;
    }

    public void setSetErrorMessage(String setErrorMessage) {
        this.setErrorMessage = setErrorMessage;
    }

    public AppUser getData() {
        return data;
    }

    public void setData(AppUser data) {
        this.data = data;
    }
}
