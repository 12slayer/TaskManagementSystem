package com.nepdroid.taskmanagement.task.entity;

public interface TaskProjection {
    String getEmail();
    int getTaskId();
    String getTaskName();
    String getCreatedAt();
    String getUpdateAt();
}
