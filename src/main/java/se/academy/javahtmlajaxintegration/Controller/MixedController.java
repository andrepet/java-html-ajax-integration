package se.academy.javahtmlajaxintegration.Controller;

import se.academy.javahtmlajaxintegration.Data.Message;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;


@Controller
public class MixedController {
    @GetMapping("/myform")
    public ModelAndView myForm() {
        return new ModelAndView("myform");
    }

    @PostMapping("/myform")
    public String myFormPost(HttpSession session, @RequestParam String textData) {
        session.setAttribute("textData", textData);
        return "redirect:/result";
    }

    @GetMapping("/result")
    public ModelAndView result(HttpSession session) {
        if (session.getAttribute("textData") != null) {
            String textData = (String) session.getAttribute("textData");
            return new ModelAndView("result").addObject("textData", textData);
        } else {
            // if you try to access "localhost:8080/result" without filling out the HTML-form, redirect to index-page
            return new ModelAndView("redirect:/");
        }
    }


    @ResponseBody
    @PostMapping("/myformAsync")
    //the uncomment method signature (get message as a String) can be used for debugging what the JSON object contains!
//    public String myFormPostAsync(HttpSession session, @RequestBody String textData){
    public void myFormPostAsync(HttpSession session, @RequestBody Message message) {
        System.out.println("Received object: " + message);
        session.setAttribute("message", message);
    }

    @ResponseBody
    @GetMapping("/myformAsync")
    public Message myFormGetAsync(HttpSession session) {
        Message message = null;
        if (session.getAttribute("message") != null) {
            message = (Message) session.getAttribute("message");
            String msg = message.getMsg();
            message.setMsg(msg.toUpperCase());
        }
        System.out.println("Send object: " + message);
        return message;
    }
}
