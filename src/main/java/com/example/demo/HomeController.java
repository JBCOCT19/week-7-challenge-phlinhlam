package com.example.demo;

import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.util.Map;
import java.util.Set;

@Controller
public class HomeController {

    @Autowired
    private UserService userService;

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    CloudinaryConfig cloudc;

    @GetMapping("/register")
    public String showRegistrationPage(Model model)
    {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String processRegistrationPage(@Valid @ModelAttribute("user") User user, BindingResult result, Model model)
    {
        model.addAttribute("user", user);
        if(result.hasErrors()){
            return "register";
        }
        else
        {
            //use this line to save new user as user
            //userService.saveUser(user);
            //save new user as admin and allows it to have full access
            userService.saveAdmin(user);
            model.addAttribute("message","Admin Account Created");
        }
        return "login";
    }

    //index page to display the list of messages with the author
    @RequestMapping("/")
    public String index(Model model){
        model.addAttribute("messages",messageRepository.findAll());
        model.addAttribute("users", userRepository.findAll());
        return "index";
    }

    @RequestMapping("/login")
    public String login()
    {
        return "login";
    }

    @Autowired
    UserRepository userRepository;
    @RequestMapping("/secure")
    public String secure(Principal principal, Model model)
    {
        String username = principal.getName();
        model.addAttribute("user",userRepository.findByUsername(username));
        model.addAttribute("messages", messageRepository.findAll());
        return "secure";
    }


    @RequestMapping("/listMessage")
    public String listMess(Model model) {
        model.addAttribute("messages", messageRepository.findAll());
        return "secure";
    }

    @GetMapping("/addMessage")
    public String messageForm(Model model) {
        model.addAttribute("message", new Message());
        model.addAttribute("users", userRepository.findAll());
        return "addMessage";
    }
    @PostMapping("/processMessage")
    public String processUser(@Valid @ModelAttribute Message message,
                              @RequestParam("userid") long id,
                              @RequestParam("file") MultipartFile file, BindingResult result) {
        message.setUser(userRepository.findById(id).get());
        messageRepository.save(message);
        User user = userRepository.findById(id).get();
        Set<Message> messages = user.getMessages();
        if(!file.isEmpty())
        {
            try{
                Map uploadResult = cloudc.upload(file.getBytes(), ObjectUtils.asMap("resourcetype", "auto"));
                message.setPhoto(uploadResult.get("url").toString());
                userRepository.save(user);
            }catch (IOException ex){
                ex.printStackTrace();
                return "redirect:/addMessage";
            }
        }
        else
        {
            messageRepository.save(message);
        }
        messages.add(message);
        user.setMessages(messages);
        userRepository.save(user);
        return "redirect:/secure";
    }

    //show details of a specific message
    @RequestMapping("/detail/{id}")
    public String detailMessage(@PathVariable("id") long id, Model model)
    {
        model.addAttribute("messages", messageRepository.findById(id));
        return "messageDetail";
    }

    @RequestMapping("/update/{id}")
    public String updateMessage(@PathVariable("id") long messid, Model model)
    {
        model.addAttribute("messages", messageRepository.findById(messid));
        return "addMessage";
    }
}