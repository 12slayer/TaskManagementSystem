package com.nepdroid.taskmanagement.task.repo;

import com.nepdroid.taskmanagement.task.entity.Task;
import com.nepdroid.taskmanagement.task.entity.TaskAnswer;
import com.nepdroid.taskmanagement.task.entity.TaskAnswerProjection;
import com.nepdroid.taskmanagement.task.entity.TaskProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TaskAnswerRepositery extends JpaRepository<TaskAnswer, Integer> {

@Query(value="SELECT t.task_name as name,a.id as answerId,a.answer as answer, u.email as email,a.create_at as createtime,a.update_at as updatetime\n" +
        "FROM tbl_task_answer a\n" +
        "INNER JOIN\n" +
        "tbl_task t\n" +
        "ON a.fk_task_id=t.id\n" +
        "INNER JOIN\n" +
        "tbl_users u\n" +
        "ON t.fk_user_id = u.user_id\n"+
        "where a.fk_task_id=?1",nativeQuery=true)
TaskAnswerProjection selectTaskAnswerWithAdditionalInfo(int taskId);

    TaskAnswer findByTaskId(int taskId);
}
