package com.clamer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by sungman.you on 2017. 4. 17..
 */

@Controller
public class ViewController {

//    뷰 등록 전용 컨트롤러

    @RequestMapping(value = {"/"}, method = RequestMethod.GET)
    public ModelAndView index() {
        return new ModelAndView("index");
    }
}
