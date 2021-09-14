package com.assignmebt.techgig.Controller;


import com.assignmebt.techgig.Entity.User;
import com.assignmebt.techgig.Service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userService;


    @GetMapping("/")
    public String viewHomePage(Model model) {
//        model.addAttribute("listUsers", userService.getAllUsers());
//        return "index";
        return findPaginated(1, "firstName", "asc", model);
    }

    @GetMapping("/ShowNewUserForm")
    public String showNewEmployeeForm(Model model) {
        // create model attribute to bind form data
        User user = new User();
        model.addAttribute("user", user);
        return "new_user";
    }

    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute("user") User user) {
        // create model attribute to bind form data
        userService.addUser(user);
        return "redirect:/";
    }


    @GetMapping(value = "/ShowUpdateForm/{id}")
    public String updateUser(@PathVariable("id")Long id, Model model){
        User existingUser = userService.getUserById(id);

        model.addAttribute("user" , existingUser);

        return "update_user";
    }

    @GetMapping(value = "/deleteUser/{id}")
    public String deleteUser(@PathVariable Long id, Model model){
        userService.deleteUser(id);
        return "redirect:/";
    }


    @GetMapping("/page/{pageNo}")
    public String findPaginated(@PathVariable (value = "pageNo") int pageNo,
                                @RequestParam("sortField") String sortField,
                                @RequestParam("sortDir") String sortDir,
                                Model model) {
        int pageSize = 5;

        Page<User> page = userService.findPaginated(pageNo, pageSize, sortField, sortDir);
        List<User> userList = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("listUsers", userList);
        return "index";
    }


}
