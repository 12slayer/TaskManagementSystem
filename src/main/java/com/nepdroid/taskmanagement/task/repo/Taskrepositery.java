package com.nepdroid.taskmanagement.task.repo;

import com.nepdroid.taskmanagement.login.entity.AppUser;
import com.nepdroid.taskmanagement.task.entity.Task;
import com.nepdroid.taskmanagement.task.entity.TaskProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface Taskrepositery extends JpaRepository<Task, Integer> {

@Query(value="SELECT u.email as email,t.id as taskId, t.task_name as taskName, t.create_at as createdAt, t.update_at as updateAt\n" +
        "FROM tbl_task t\n" +
        "INNER JOIN\n" +
        "tbl_users u\n" +
        "ON t.fk_user_id = u.user_id",nativeQuery = true)
List<TaskProjection> getAllTaskWithInfo();

@Query("SELECT t FROM Task t where t.assignedUserId=?1")
    List<Task> getUserTask(int userId);
}

