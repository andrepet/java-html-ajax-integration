package se.academy.javahtmlajaxintegration.controller;

import org.springframework.ui.Model;
import se.academy.javahtmlajaxintegration.domain.Message;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.concurrent.ThreadLocalRandom;


@Controller
public class MixedController {
    @GetMapping("/myform")
    public String myForm() {
        return "myform";
    }

    @PostMapping("/myform")
    public String myFormPost(HttpSession session, @RequestParam String textData) {
        session.setAttribute("textData", textData);
        return "redirect:/result";
    }

    @GetMapping("/result")
    public String result(HttpSession session, Model model) {
        if (session.getAttribute("textData") != null) {
            String textData = (String) session.getAttribute("textData");
            model.addAttribute("textData", textData);
            return "result";
        } else {
            // if you try to access "localhost:8080/result" without filling out the HTML-form, redirect to index-page
            return "redirect:/";
        }
    }


    @ResponseBody
    @PostMapping("/myformAsync")
    public Message myFormPostAsync(@RequestBody Message message) {
        System.out.println("Received object: " + message);
        String msg = message.getMsg();
        message.setMsg(msg.toUpperCase());
        return message;
    }

    @ResponseBody
    @GetMapping("/myformAsync")
    public Message myFormGetAsync() {
        int number = ThreadLocalRandom.current().nextInt(1, 11); //random number: [1,10]
        Message message = new Message();
        message.setMsg(Integer.toString(number));
        System.out.println("Send object: " + message);
        return message;
    }
}
