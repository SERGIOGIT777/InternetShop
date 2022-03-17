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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
@RequestMapping("/adminPage")
public class AdminController {


    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private ShopsRepository shopsRepository;

    @Autowired
    private PriceRepository priceRepository;

    @GetMapping("/myAdmin")
    public ModelAndView viewUsers() {
        ModelAndView mav = new ModelAndView("admin/admin");
        mav.addObject("listUsers", usersRepository.findAll());
        return mav;
    }

    //------------------------------------------------обновление ЮЗЕРОВ-----------------------------------------

    @GetMapping("/updateUsersForm")
    public ModelAndView updateUsersForm(@RequestParam long id) {
        ModelAndView mav = new ModelAndView("update/updateUsers");
        mav.addObject("update", usersRepository.getId(id));
        return mav;
    }

    @Transactional // транзакция для обновления методово delete/update
    @PostMapping("/updateUsers")
    public String updateUsers(Long id, String login, String password, String authority,
                              String information, String email) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String passwordNew = passwordEncoder.encode(password);
        usersRepository.updateUsers(id, login, passwordNew, authority, information, email);
        return "redirect:/adminPage/myAdmin";
    }

    //---------------------------------------------------УДАЛЕНИЕ ЮЗЕРОВ------------------------------------------

    @DeleteMapping("/deleteUsers")
    @Transactional
    public String deleteUsers(@RequestParam Long id) {
        usersRepository.deleteById(id);
        return "redirect:/adminPage/myAdmin";
    }

    //--------------------------Добавление, покупка, обновление и поиск товара под админом-------------------------------


    @GetMapping("/myPage")
    public ModelAndView viewShops() {
        ModelAndView mav = new ModelAndView("admin/pricesAdmin");
        mav.addObject("finder", new Prices());
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String users = user.getUsername();
        List<Users> users1 = usersRepository.findByLogin(users);
        mav.addObject("listPriceAdmin", priceRepository.findAll());
        mav.addObject("adminUser", users1.get(0).getInformation());
        return mav;
    }

    //--------------------------Сохранение товара-------------------------------

    @GetMapping("/savePrice")
    public ModelAndView addPriceForm() {
        ModelAndView mav = new ModelAndView("admin/addPrice");
        mav.addObject("addPrise", new Prices());
        return mav;
    }

    @PostMapping("/savePrice")
    public String savePrice(@ModelAttribute("addPrise") @Valid Prices prices,
                            BindingResult result, Model model) {

        if (result.hasErrors()) {
            return "admin/addPrice";
        }

        priceRepository.save(prices);
        model.addAttribute("getPrise", priceRepository.findAll());
        return "redirect:/adminPage/myPage";
    }

    //--------------------------Обновление товара-------------------------------

    @GetMapping("/updatePriceForm")
    public ModelAndView updatePriceForm(@RequestParam long id) {
        ModelAndView mav = new ModelAndView("update/updatePrice");
        mav.addObject("updatePrice", priceRepository.getId(id));
        return mav;
    }

    @Transactional
    @PostMapping("/updatePrice")
    public String updatePrice(Long id, String name, String about, Double price,
                              Integer count) {
        priceRepository.updatePrice(id, name, about, price, count);
        return "redirect:/adminPage/myPage";
    }

    //--------------------------Удаление товара-------------------------------

    @DeleteMapping("/deletePrice")
    @Transactional
    public String deletePrice(@RequestParam Long id) {
        priceRepository.deleteById(id);
        return "redirect:/adminPage/myPage";
    }

    //--------------------------Поиск товара-------------------------------

    @GetMapping("/findName")
    public ModelAndView findName(@RequestParam String name) {
        ModelAndView mav = new ModelAndView("admin/pricesAdmin");
        mav.addObject("finder", new Prices());
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String users = user.getUsername();
        List<Users> users1 = usersRepository.findByLogin(users);
        mav.addObject("listPriceAdmin", priceRepository.findByName(name));
        mav.addObject("adminUser", users1.get(0).getInformation());
        return mav;
    }

    @GetMapping("/findAbout")
    public ModelAndView findAbout(@RequestParam String about) {
        ModelAndView mav = new ModelAndView("admin/pricesAdmin");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String users = user.getUsername();
        List<Users> users1 = usersRepository.findByLogin(users);
        mav.addObject("finder", new Prices());
        mav.addObject("listPriceAdmin", priceRepository.findByAbout(about));
        mav.addObject("adminUser", users1.get(0).getInformation());
        return mav;
    }

    //-----------------------------модуль заказа товара-------------------------------------------------

    @Transactional
    @GetMapping("/saveBucket")
    public ModelAndView addBucketForm(@RequestParam long id) {
        ModelAndView mav = new ModelAndView("price/priceShopAdmin");
        Prices prices = priceRepository.getId(id);
        Shops shops = new Shops();
        shops.setPrices(prices);
        User userAdmin = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String users = userAdmin.getUsername();
        Users login = usersRepository.getId(usersRepository.findByLogin(users).get(0).getId());
        shops.setUsers(login);
        mav.addObject("bucket", shops);
        return mav;
    }

    @Transactional
    @PostMapping("/saveBucket")
    public String saveBucket(@ModelAttribute("bucket") @Valid Shops shops,
                             BindingResult result, Model model,
                             @RequestParam("id_price") Prices prices,
                             @RequestParam("id_logins") Users users,
                             @RequestParam("dates")
                             @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate localDate) {


        if (result.hasErrors()) {
            return "price/priceShopAdmin";
        }
        double count = Math.round((prices.getPrice() * shops.getCounts()) * 100.0) / 100.0;
        shops.setDates(localDate);
        shops.setPrices(prices);
        shops.setUsers(users);
        shops.setPrice(count);
        if (prices.getCount() < shops.getCounts() || shops.getCounts() < 1) {
            return "price/priceShopAdmin";
        } else {
            prices.setCount(prices.getCount() - shops.getCounts());
            shopsRepository.save(shops);
            model.addAttribute("buckets", shopsRepository.findAll());
        }
        return "redirect:/adminPage/myPage";
    }

    //--------------------------Купленные товары------------------------------

    @GetMapping("/bucketAdmin")
    public ModelAndView viewBuckets() {
        ModelAndView mav = new ModelAndView("admin/bucketAdmin");
        mav.addObject("bucket", new Shops());
        mav.addObject("listBucketAdmin", shopsRepository.findAll());
        mav.addObject("sumAdmin", shopsRepository.count());
        return mav;
    }


    //--------------------------Обновление корзины-------------------------------

    @GetMapping("/updateBucketForm")
    public ModelAndView updateBucketForm(@RequestParam long id) {
        ModelAndView mav = new ModelAndView("update/updateBucket");
        mav.addObject("updateBucket", shopsRepository.getById(id));
        return mav;
    }

    @Transactional
    @PostMapping("/updateBucket")
    public String updateBucket(Long id, String status) {
        shopsRepository.updateStatus(id, status);
        return "redirect:/adminPage/bucketAdmin";
    }

    //-------------------------Поиск в корзине-------------------------------

    @GetMapping("/findBucket")
    public ModelAndView findBucket(@RequestParam String status) {
        ModelAndView mav = new ModelAndView("admin/bucketAdmin");
        mav.addObject("bucket", new Shops());
        mav.addObject("listBucketAdmin", shopsRepository.findByStatus(status));
        return mav;
    }

}
