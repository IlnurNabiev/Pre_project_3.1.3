package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.services.UserBusinesService;

import java.util.List;
import java.util.Set;


@Controller
@RequestMapping("/admin")
public class AdminController {
    private RoleRepository roleRepository;
    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    private UserBusinesService userBusinesService;
    @Autowired
    public void setUserBusinesService(UserBusinesService userBusinesService) {
        this.userBusinesService = userBusinesService;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("user", userBusinesService.index());
        return "index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") Long id, Model model) {
        model.addAttribute("person", userBusinesService.show(id));
        return "show";
    }

    @GetMapping("/new")
    public String addNewUser(Model model, @ModelAttribute("person") User user) {
        model.addAttribute("roles", roleRepository.findAll());
        return "new";
    }

    @PostMapping("/new")
    public String create(@ModelAttribute("person") User user) {
        userBusinesService.save(user);
        return "redirect:/admin/";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id) {
        userBusinesService.delete(id);
        return "redirect:/admin";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") Long id) {
        model.addAttribute("person", userBusinesService.show(id));
        return "edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute User user, @PathVariable("id") Long id) {
//        User user1 = userBusinesService.show(id);
        userBusinesService.update(user);
        return "redirect:/admin";
    }
}