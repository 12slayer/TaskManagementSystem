package com.nepdroid.taskmanagement.login.service;

import com.nepdroid.taskmanagement.login.entity.AppUser;
import com.nepdroid.taskmanagement.login.repo.Apprepositery;
import com.nepdroid.taskmanagement.task.repo.Taskrepositery;
import com.nepdroid.taskmanagement.task.entity.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class AppUserService {
    @Autowired
    private Apprepositery apprepositery;

    @Autowired
    private Taskrepositery taskrepositery;
    public AppUser addUser(AppUser appUser){
        AppUser savedUser=apprepositery.save(appUser);
        return savedUser;
    }
    public List<AppUser> getAllUser()
    {
        return apprepositery.fetchAllUser();
    }
    public void deleteUser(int userId){
        apprepositery.deleteById(userId);

    }

    public AppUser fetchedAppUserByEmail(String email){
        return apprepositery.findByEmail(email);
    }

    public List<Task> getAllTask()
    {
        return taskrepositery.findAll();
    }



}
