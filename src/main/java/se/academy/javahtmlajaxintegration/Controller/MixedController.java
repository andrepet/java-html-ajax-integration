package se.academy.javahtmlajaxintegration.controller;

import org.springframework.ui.Model;
import org.springframework.web.client.RestTemplate;
import se.academy.javahtmlajaxintegration.domain.Message;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import se.academy.javahtmlajaxintegration.domain.RatesApiMessage;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
// if you want to 'see' the content of what you are receiving in the request-body, use a String like this:
//    public Message myFormPostAsync(@RequestBody String data) {
    public Message myFormPostAsync(@RequestBody Message message) {
        // make sure that the class 'Message' is mapped correctly with the json data in 'message'.
        // If they are mapped correct, Spring will automatically transform the json into a valid Java object
        System.out.println("Received object: ");
        System.out.println(message);

        // transform the text-message inside Message-object to upper case and
        // then return back the Message object as JSON to the caller
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


    @GetMapping("/callAjaxFromServer")
    public String callAjaxFromServer(Model model) {

        // The open API we want to call from the server-side
        String uri = "https://api.exchangeratesapi.io/latest";
        RestTemplate restTemplate = new RestTemplate();

        // To get the data as a pure String
        String jsonString = restTemplate.getForObject(uri, String.class);
        System.out.println(jsonString);

        // To map the data as a Map (if the root is a JSON Object
        Map<String, Object> jsonMap = restTemplate.getForObject(uri, HashMap.class);
        System.out.println(jsonMap);

        // To map the data as a java instance of a class.
        // If you want to ignore certain JSON object properties, do not create instance variables for them in the class!
        // For example: if you want to ignore the property 'date', remove it from the class RatesApiMessage.java
        RatesApiMessage jsonPOJO = restTemplate.getForObject(uri, RatesApiMessage.class);
        System.out.println(jsonPOJO);


        model.addAttribute("json", jsonPOJO);
        return "resultAjaxCallFromServer";
    }


    @GetMapping("/testObject")
    public String testObject() {

        String uri = "https://api.bf4stats.com/api/onlinePlayers";
        RestTemplate restTemplate = new RestTemplate();

        System.out.println();
        String jsonString = restTemplate.getForObject(uri, String.class);
        System.out.println(jsonString);

        System.out.println();
        Map<String, Object> jsonMap = restTemplate.getForObject(uri, HashMap.class);
        System.out.println(jsonMap);


        return "myform";
    }

    @GetMapping("/testArray")
    public String testArray() {

        String uri = "https://ghibliapi.herokuapp.com/films/";
        RestTemplate restTemplate = new RestTemplate();

        System.out.println();
        String jsonString = restTemplate.getForObject(uri, String.class);
        System.out.println(jsonString);

        System.out.println();
        List<Object> jsonMap = restTemplate.getForObject(uri, List.class);
        System.out.println(jsonMap);


        return "myform";
    }
}
