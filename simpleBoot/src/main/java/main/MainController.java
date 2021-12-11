package main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @Autowired
    private MyService service;

    @RequestMapping
    @ResponseBody
    public String main() {
        System.out.println("start get data");
        MyCache m1 =service.getEmployeeByID("1");
        MyCache m2 =service.getEmployeeByID("1");
        MyCache m3 =service.getEmployeeByID("2");
        System.out.println("stop get data");
        return "hello!";
    }
}
