package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

    @GetMapping("hello")
    public String hello (Model model){
        model.addAttribute("data", "Hello~");
        return "hello";
    }
    @GetMapping("hello-mvc")
    public String helloMvc(@RequestParam("name") String name, Model model){
        model.addAttribute("name", name);
        return "hello-template";
    }
    @GetMapping("hello-string")
    @ResponseBody
    // ResponseBody : HTTP Body에 문자 내용을 직접 반환 → viewResolver 사용X
    public String helloString(@RequestParam(value = "name", required = false) String name, Model model){
        model.addAttribute("name", name);
        return "hello "+name;
    }


}


