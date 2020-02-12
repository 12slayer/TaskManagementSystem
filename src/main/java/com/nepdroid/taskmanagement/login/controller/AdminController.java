package com.nepdroid.taskmanagement.login.controller;

import com.nepdroid.taskmanagement.common.model.ApplicationMessage;
import com.nepdroid.taskmanagement.login.entity.AppUser;
import com.nepdroid.taskmanagement.login.service.AppUserService;
import com.nepdroid.taskmanagement.task.entity.TaskAnswerProjection;
import com.nepdroid.taskmanagement.task.service.TaskAnswerService;
import com.nepdroid.taskmanagement.task.service.TaskService;
import com.nepdroid.taskmanagement.task.entity.Task;
import com.nepdroid.taskmanagement.task.entity.TaskProjection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private AppUserService appUserService;
    @Autowired
    private TaskService taskService;

    @Autowired
    private TaskAnswerService taskAnswerService;


    @GetMapping(value = "")
    public ModelAndView getadminPage(Principal principal) {
        ModelAndView modelAndView = new ModelAndView();
        String adminEmail = principal.getName();
        List<AppUser> listofuser = appUserService.getAllUser();
        modelAndView.setViewName("admin");
        modelAndView.addObject("email", adminEmail);
        modelAndView.addObject("list", listofuser);
        return modelAndView;

    }

   /* @GetMapping(value="admin")
    public ModelAndView adminPage(Principal principal) {
        ModelAndView modelAndView = new ModelAndView();
        String adminEmail = principal.getName();

        modelAndView.setViewName("admin");
        modelAndView.addObject("email", adminEmail);

        return modelAndView;
    }*/

    @GetMapping(value = "/adminregister")
    public ModelAndView getRegisterPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("adminregister");
        modelAndView.addObject("user", new AppUser());
        return modelAndView;
    }

    @PostMapping(value = "/adminregister")
    public String addUser(@ModelAttribute AppUser appUser) {
        String hashPassword = bCryptPasswordEncoder.encode(appUser.getPassword());
        appUser.setPassword(hashPassword);
        appUser.setRole("ADMIN");

        AppUser reg = appUserService.addUser(appUser);
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(appUser.getEmail());

        msg.setSubject("you've Successfully registered in our system");
        msg.setText("cograt your id is registered" + reg.getUserId());
        msg.setText("you can visit login by visiting <a href='http://localhost:8080/login'>page</a>");


        javaMailSender.send(msg);

        return "redirect:/login";

    }

    @GetMapping(value = "/create-task")
    public ModelAndView showCreateTask(Principal principal) {
        ModelAndView modelAndView = new ModelAndView();
        String adminEmail = principal.getName();
        List<AppUser> listofuser = appUserService.getAllUser();
        List<Task> task = appUserService.getAllTask();
        modelAndView.setViewName("create-task");
        modelAndView.addObject("email", adminEmail);
        modelAndView.addObject("list", listofuser);
        modelAndView.addObject("tasklist", task);
        return modelAndView;
    }

    @PostMapping(value = "/create-task")
    @ResponseBody
    public ApplicationMessage givingtask(@RequestBody Task task) {
        ApplicationMessage applicationMessage = new ApplicationMessage();
        Task createdTask = taskService.createTask(task);

        if (createdTask == null) {
            applicationMessage.setSetSuccess(false);
            applicationMessage.setSetErrorMessage("couldn't create task");

        } else {
            applicationMessage.setSetSuccess(true);
        }
        return applicationMessage;
    }

    @GetMapping(value = "/delete-user/{userId}")
    public String deleteuser(@PathVariable int userId) {

        appUserService.deleteUser(userId);


        System.out.println(userId);

        return "redirect:/";

    }

    @GetMapping(value = "/task")
    public ModelAndView getallTask() {
        ModelAndView modelAndView = new ModelAndView();
        List<TaskProjection> listoftask = taskService.getAllTask();
        modelAndView.setViewName("task");
        modelAndView.addObject("tasks", listoftask);
        return modelAndView;
    }

    @GetMapping(value = "/edit-task/{taskId}")
    public ModelAndView showEditUser(@PathVariable int taskId) {
        ModelAndView modelAndView = new ModelAndView();

        Task fetchedUser = taskService.findTask(taskId);
        List<AppUser> listofuser = appUserService.getAllUser();
        modelAndView.addObject("task", fetchedUser);
        modelAndView.addObject("list", listofuser);
        modelAndView.setViewName("edit-task");
        return modelAndView;

    }

    @GetMapping(value = "/delete-task/{taskId}")
    public String deleteTask(@PathVariable int taskId) {


        taskService.deleteTask(taskId);


        return "redirect:/admin";

    }

    @GetMapping(value = "/user-answer/{taskid}")
    public ModelAndView getUserAnswer(@PathVariable int taskid) {

        ModelAndView modelAndView = new ModelAndView();
        TaskAnswerProjection taskAnswerProjection = taskAnswerService.getTaskAnswerWithInfo(taskid);
        modelAndView.setViewName("user-answer");
        modelAndView.addObject("taskAnswerDetail", taskAnswerProjection);
        return modelAndView;
    }


}
