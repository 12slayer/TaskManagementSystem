package com.nepdroid.taskmanagement.login.controller;

import com.nepdroid.taskmanagement.common.model.ApplicationMessage;
import com.nepdroid.taskmanagement.login.entity.AppUser;
import com.nepdroid.taskmanagement.task.entity.TaskAnswer;
import com.nepdroid.taskmanagement.login.service.AppUserService;
import com.nepdroid.taskmanagement.task.service.TaskAnswerService;
import com.nepdroid.taskmanagement.task.service.TaskService;
import com.nepdroid.taskmanagement.task.entity.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping(value="/user")
public class UserController {
    @Autowired
    private AppUserService appUserService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private TaskAnswerService taskAnswerService;

    @GetMapping(value="")
    public ModelAndView getUserHomePage(Principal principal){
        ModelAndView modelAndView=new ModelAndView();
        String email=principal.getName();
        AppUser appUser= appUserService.fetchedAppUserByEmail(email);
        List<Task> taskList=taskService.getUserTask(appUser.getUserId());
        modelAndView.setViewName("user-landingpage");
        modelAndView.addObject("taskuser",taskList);
        modelAndView.addObject("email",email);
        return modelAndView;
    }

    @GetMapping(value="/task-view/{taskId}")
    public ModelAndView UserTaskView(@PathVariable int taskId,Principal principal){
        ModelAndView modelAndView=new ModelAndView();
        Task task =taskService.findTask(taskId);

        modelAndView.setViewName("task-view");
        modelAndView.addObject("task",task);
        modelAndView.addObject("email",principal.getName());
        return  modelAndView;



    }
    @PostMapping(value="/submit-task")
    @ResponseBody
    public ApplicationMessage processSubmitTask(@RequestBody TaskAnswer taskAnswer){
        ApplicationMessage applicationMessage=new ApplicationMessage();
        TaskAnswer saveTask=taskAnswerService.createTask(taskAnswer);
        applicationMessage.setSetSuccess(true);

        if(saveTask==null){
            applicationMessage.setSetSuccess(false);
            applicationMessage.setSetErrorMessage("cannot save answer");
        }
        return applicationMessage;

    }

}
