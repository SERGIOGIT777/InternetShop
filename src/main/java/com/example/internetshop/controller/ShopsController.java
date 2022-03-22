package com.example.internetshop.controller;

import com.example.internetshop.Entities.Prices;
import com.example.internetshop.Entities.Shops;
import com.example.internetshop.Entities.Users;
import com.example.internetshop.repository.PriceRepository;
import com.example.internetshop.repository.ShopsRepository;
import com.example.internetshop.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/shops")
public class ShopsController {

    @Autowired
    private ShopsRepository shopsRepository;

    @Autowired
    private PriceRepository priceRepository;

    @Autowired
    private UsersRepository usersRepository;


    @GetMapping("/myPage")
    public ModelAndView viewAll() {
        ModelAndView mav = new ModelAndView("users/prices");
        mav.addObject("finder", new Prices());
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String users = user.getUsername();
        List<Users> users1 = usersRepository.findByLogin(users);
        mav.addObject("listPrice", priceRepository.findAll());
        mav.addObject("userUser", users1.get(0).getInformation());
        return mav;
    }

    @GetMapping("/findName")
    public ModelAndView findName(@RequestParam String name) {
        ModelAndView mav = new ModelAndView("users/prices");
        mav.addObject("finder", new Prices());
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String users = user.getUsername();
        List<Users> users1 = usersRepository.findByLogin(users);
        mav.addObject("listPrice", priceRepository.findByName(name));
        mav.addObject("userUser", users1.get(0).getInformation());
        return mav;
    }

    @GetMapping("/findAbout")
    public ModelAndView findAbout(@RequestParam String about) {
        ModelAndView mav = new ModelAndView("users/prices");
        mav.addObject("finder", new Prices());
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String users = user.getUsername();
        List<Users> users1 = usersRepository.findByLogin(users);
        mav.addObject("listPrice", priceRepository.findByAbout(about));
        mav.addObject("userUser", users1.get(0).getInformation());
        return mav;
    }

    //-------------------------------------------Модули для осуществления покупки------------------------------------

    @Transactional
    @GetMapping("/saveBucketShop")
    public ModelAndView addBucketForm(@RequestParam long id) {
        ModelAndView mav = new ModelAndView("price/priceShop");
        Prices prices = priceRepository.getId(id);
        User userUser = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String users = userUser.getUsername();
        Users login = usersRepository.getId(usersRepository.findByLogin(users).get(0).getId());
        Shops shops = new Shops();
        shops.setPrices(prices);
        shops.setUsers(login);
        shops.setStatus("ожидает сборки");
        mav.addObject("bucketUser", shops);
        return mav;
    }

    @Transactional
    @PostMapping("/saveBucketShop")
    public String saveBucket(@ModelAttribute("bucketUser") @Valid Shops shops,
                             BindingResult result, Model model,
                             @RequestParam("id_price") Prices prices,
                             @RequestParam("id_logins") Users users,
                             @RequestParam("dates")
                             @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate localDate) {

        if (result.hasErrors()) {
            return "price/priceShop";
        }
        double count = Math.round((prices.getPrice() * shops.getCounts()) * 100.0) / 100.0;
        shops.setDates(localDate);
        shops.setPrices(prices);
        shops.setUsers(users);
        shops.setPrice(count);
        if (prices.getCount() < shops.getCounts() || shops.getCounts() < 1) {
            return "price/priceShop";
        } else {
            prices.setCount(prices.getCount()-shops.getCounts());
            shopsRepository.save(shops);
            model.addAttribute("bucketUsers", shopsRepository.findAll());
        }
        return "redirect:/shops/myPage";
    }

    //--------------------------Купленные товары------------------------------

    @GetMapping("/bucketUsers")
    public ModelAndView viewBuckets() {
        ModelAndView mav = new ModelAndView("users/bucket");
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String name = user.getUsername();
        mav.addObject("bucketUser", new Shops());
        mav.addObject("listBucketUsers", shopsRepository.findByName(name));
        mav.addObject("sum", (long) shopsRepository.findByName(name).size());
        return mav;
    }

    //-------------------------Поиск в корзине-------------------------------

    @GetMapping("/findBucket")
    public ModelAndView findBucket(@RequestParam String status) {
        ModelAndView mav = new ModelAndView("users/bucket");
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String name = user.getUsername();
        mav.addObject("bucketUser", new Shops());
        mav.addObject("listBucketUsers", shopsRepository.findByStatusLogin(status, name));
        mav.addObject("sum", (long) shopsRepository.findByName(name).size());
        mav.addObject("sum", shopsRepository.findByStatusLogin(status, name).stream().count());
        return mav;
    }

}

