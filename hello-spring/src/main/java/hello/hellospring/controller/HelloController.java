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
    @GetMapping("hello-api")
    @ResponseBody
    // ResponseBody를 사용하며 객체를 반환하면 객체가 JSON 형태로 변환됨.
    public Hello helloApi(@RequestParam("name") String name, Model model){
        Hello hello = new Hello();

        hello.setName(name);
        return hello;
    }

    static class Hello{
        private String name;
        // private : 외부에서 접근 불가 → Getter, Setter로 데이터 조작

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}


