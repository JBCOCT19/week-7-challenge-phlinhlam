package com.example.demo;

import org.aspectj.bridge.MessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DataLoader implements CommandLineRunner {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    MessageRepository messageRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public void run(String... strings) throws Exception {

        roleRepository.save(new Role("USER"));
        roleRepository.save(new Role("ADMIN"));
         Role adminRole = roleRepository.findByRole("ADMIN");
         Role userRole = roleRepository.findByRole("USER");

        User user1 = new User("linhlinh@email.com", "password", "Linh", "Lam", true, "linhlinh");
        user1.setRoles(Arrays.asList(adminRole));
        userRepository.save(user1);


        User user2 = new User("admin@admin.com", "password", "Joseph", "Joe", true, "admin");
        user2.setRoles(Arrays.asList(adminRole));
        userRepository.save(user2);

        //preload message 1, and set this message belong to user 1
        Message mess1 = new Message("Beautiful Day", "Today is a cold day. Temperature is below 50 degree", "12/04/2019");
        mess1.setUser(user1);
        mess1.setPhoto("https://images.pexels.com/photos/912110/pexels-photo-912110.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260");
        messageRepository.save(mess1);

        Message mess2 = new Message("Foggy Day", "It is cold and foggy today","12/10/2019");
        mess2.setPhoto("https://static.themarthablog.com/2019/01/DSC_0125-2.jpg");
        mess2.setUser(user2);
        messageRepository.save(mess2);

        Message mess3 = new Message("Challenge #7", "This is so much work!!!","12/10/2019");
        mess3.setUser(user1);
        mess3.setPhoto("https://icon-library.net/images/crying-face-icon/crying-face-icon-28.jpg");
        messageRepository.save(mess3);

        userRepository.save(user1);
        userRepository.save(user2);

    }

}
