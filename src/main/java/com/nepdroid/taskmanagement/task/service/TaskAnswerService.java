package com.nepdroid.taskmanagement.task.service;

import com.nepdroid.taskmanagement.task.entity.Task;
import com.nepdroid.taskmanagement.task.entity.TaskAnswer;
import com.nepdroid.taskmanagement.task.entity.TaskAnswerProjection;
import com.nepdroid.taskmanagement.task.entity.TaskProjection;
import com.nepdroid.taskmanagement.task.repo.TaskAnswerRepositery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskAnswerService {
    @Autowired
    private TaskAnswerRepositery taskAnswerRepositery;

    public TaskAnswer createTask(TaskAnswer taskAnswer){
        return taskAnswerRepositery.save(taskAnswer);
    }


    public TaskAnswer findByTaskId(int taskId) {
        return taskAnswerRepositery.findByTaskId(taskId);
    }

    public TaskAnswerProjection getTaskAnswerWithInfo(int taskId){
        return taskAnswerRepositery.selectTaskAnswerWithAdditionalInfo(taskId);
    }



}
