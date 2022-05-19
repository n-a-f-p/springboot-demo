package com.example.springbootdemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
/**
 *
 */
public class VedioController {

        @RequestMapping("test")
        public String testController(){
            System.out.println(this);
            return "index";
        }

}
