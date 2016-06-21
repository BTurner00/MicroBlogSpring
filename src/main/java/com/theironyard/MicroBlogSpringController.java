package com.theironyard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;

/**
 * Created by Ben on 6/20/16.
 */
@Controller


public class MicroBlogSpringController {
    @Autowired
    MessageRepository messages;

    @Autowired
    UserRepository users;


    //GET route -- reads username from session and adds to model. adds messages arraylist to model
    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String home(Model model, HttpSession session) {


        String username = (String) session.getAttribute("username");
        User user = null;
        if (username != null) {
            user = new User(username);
        }
        Iterable<Message> msgs = messages.findAll();
        model.addAttribute("messages", messages);
        model.addAttribute("user", user);
        return "home";

    }


    //login route --  saves username to session
    @RequestMapping(path="/login", method = RequestMethod.POST)
    public String login (String username, String password, HttpSession session) throws Exception {
        User user = users.findByName(username);
        if (user == null) {
            user = new User(username, password);
            users.save(user);
        } else if (!user.password.equals(password)) {
            throw new Exception("Wrong password!");
        }

        session.setAttribute("username", username);
        return "redirect:/";
    }

    @RequestMapping(path="/logout", method = RequestMethod.POST)
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }


    //add message route -- takes input from form and saves to a new message object. adds message to arraylist
    @RequestMapping(path = "/add-message", method = RequestMethod.POST)
    public String addmessage(String text, HttpSession session) {

        Message message = new Message(text);
        messages.save(message);
        return "redirect:/";

    }


    //delete route -- removes message of given id from arraylist. re-orders all ids higher than one deleted to prevent index/id mismatches
    @RequestMapping(path = "/delete", method = RequestMethod.POST)
    public String delete(int id) {
        messages.delete(id);
        return "redirect:/";
    }
}