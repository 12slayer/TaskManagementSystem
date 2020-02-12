package com.nepdroid.taskmanagement.login.controller;
import com.nepdroid.taskmanagement.common.model.ApplicationMessage;
import com.nepdroid.taskmanagement.login.entity.AppUser;
import com.nepdroid.taskmanagement.login.service.AppUserService;
import com.nepdroid.taskmanagement.task.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
@RequestMapping("/")
public class HomeController {
    @Autowired
    private TaskService taskService;
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
     private  BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private AppUserService appUserService;
    @GetMapping(value="")
    public String getHomePage(){
        return "index";
    }

    @GetMapping(value="register")
    public ModelAndView getRegisterPage(){
        ModelAndView modelAndView =new ModelAndView();
        modelAndView.setViewName("register");
        modelAndView.addObject("user",new AppUser());
        return modelAndView;
    }
    @PostMapping(value="register")
    public String addUser(@ModelAttribute AppUser appUser){
        String hashPassword=bCryptPasswordEncoder.encode(appUser.getPassword());
        appUser.setPassword(hashPassword);
        appUser.setRole("USER");

      AppUser reg=  appUserService.addUser(appUser);
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(appUser.getEmail());

        msg.setSubject("you've Successfully registered in our system");
        msg.setText("cograt your id is registered" +reg.getUserId());
        msg.setText("you can visit login by visiting <a href='http://localhost:8080/login'>page</a>");


        javaMailSender.send(msg);

        return "redirect:/login";
    }
    @GetMapping(value="viewprofile")
    public ModelAndView ViewProfile(Principal principal){
        ModelAndView modelAndView=new ModelAndView();
      String email= principal.getName();
      AppUser fetchedUser=appUserService.fetchedAppUserByEmail(email);
      modelAndView.setViewName("viewprofile");
      modelAndView.addObject("user",fetchedUser);
      modelAndView.addObject("email",email);
      return modelAndView;


    }

    @GetMapping(value="login")
    public String Gotologin(){
        return "login";
    }



@GetMapping(value="check-email")
@ResponseBody
    public ApplicationMessage checkEmail(@RequestParam("email") String email){
        ApplicationMessage applicationMessage=new ApplicationMessage();
        AppUser appUser=appUserService.fetchedAppUserByEmail(email);

        if(appUser==null){
            applicationMessage.setSetSuccess(true);
        }else{
            applicationMessage.setSetSuccess(false);
            applicationMessage.setSetErrorMessage("email has already been used");
        }
        return applicationMessage;
}

    ///////////////////// from admin ///////////////
    /*@GetMapping(value="admin")
    public ModelAndView getadminPage(Principal principal){
        ModelAndView modelAndView=new ModelAndView();
        String adminEmail=principal.getName();
        List<AppUser> listofuser=appUserService.getAllUser();
        modelAndView.addObject("list",listofuser);
        modelAndView.setViewName("admin");
        modelAndView.addObject("email",adminEmail);
        return modelAndView;

    }
    @GetMapping(value="adminregister")
    public ModelAndView getRegisterAdminPage(){
        ModelAndView modelAndView =new ModelAndView();
        modelAndView.setViewName("adminregister");
        modelAndView.addObject("user",new AppUser());
        return modelAndView;
    }
    @PostMapping(value="adminregister")
    public String addadmin(@ModelAttribute AppUser appUser) {
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
    @GetMapping(value="create-task")
    public ModelAndView showCreateTask(Principal principal){
        ModelAndView modelAndView=new ModelAndView();
        String adminEmail=principal.getName();
        List<AppUser> listofuser=appUserService.getAllUser();
        modelAndView.setViewName("create-task");
        modelAndView.addObject("email",adminEmail);
        modelAndView.addObject("list",listofuser);
        return modelAndView;
    }

    @PostMapping(value="create-task")
    @ResponseBody
    public ApplicationMessage givingtask(@RequestBody Task task){
        ApplicationMessage applicationMessage=new ApplicationMessage();
        Task createdTask= taskService.createTask(task);

        if(createdTask==null){
            applicationMessage.setSetSuccess(false);
            applicationMessage.setSetErrorMessage("couldn't create task");

        }else{
            applicationMessage.setSetSuccess(true);
        }
        return applicationMessage;
    }

    @GetMapping(value ="/delete-user/{userId}" )
    public String deleteuser(@PathVariable int userId){

        appUserService.deleteUser(userId);


        System.out.println(userId);

        return "redirect:/";

    }*/

}
