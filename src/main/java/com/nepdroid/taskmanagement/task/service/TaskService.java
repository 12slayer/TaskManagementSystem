package com.nepdroid.taskmanagement.task.service;

import com.nepdroid.taskmanagement.task.repo.Taskrepositery;
import com.nepdroid.taskmanagement.task.entity.Task;
import com.nepdroid.taskmanagement.task.entity.TaskProjection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    @Autowired
    private Taskrepositery taskrepositery;

    public Task createTask(Task task){
        return taskrepositery.save(task);
    }
    public List<Task> createTask(){
        return taskrepositery.findAll();
    }

    public List<TaskProjection> getAllTask(){
        return taskrepositery.getAllTaskWithInfo();
    }
    public List<Task> getUserTask(int userid){
        return taskrepositery.getUserTask(userid);
    }

    public Task findTask(int id){
        return taskrepositery.getOne(id);
    }

    public void deleteTask(int id){
        taskrepositery.deleteById(id);

    }
}
