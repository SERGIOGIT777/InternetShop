package com.example.internetshop.controller;

import com.example.internetshop.Entities.Prices;
import com.example.internetshop.repository.PriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    private PriceRepository priceRepository;

    @GetMapping
    public ModelAndView getAll() {
        ModelAndView mav = new ModelAndView("index");
        return mav;
    }

    @GetMapping("/Page")
    public ModelAndView viewAll() {
        ModelAndView mav = new ModelAndView("default_prise");
        mav.addObject("default", new Prices());
        mav.addObject("listPrice", priceRepository.findAll());
        return mav;
    }

    @GetMapping("/findName")
    public ModelAndView findName(@RequestParam String name) {
        ModelAndView mav = new ModelAndView("default_prise");
        mav.addObject("default", new Prices());
        mav.addObject("listPrice", priceRepository.findByName(name));
        return mav;
    }
    @GetMapping("/findAbout")
    public ModelAndView findAbout(@RequestParam String about) {
        ModelAndView mav = new ModelAndView("default_prise");
        mav.addObject("default", new Prices());
        mav.addObject("listPrice", priceRepository.findByAbout(about));
        return mav;
    }
}
