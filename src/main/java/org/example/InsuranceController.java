package org.example;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class InsuranceController {

    @GetMapping("/")
    public String showHomePage() {
        return "index";
    }

    @PostMapping("/add-insured")
    public String addInsured(
            @RequestParam int id,
            @RequestParam String name,
            @RequestParam String surname,
            @RequestParam String birthDate,
            @RequestParam String address,
            @RequestParam String bankAccountNumber,
            Model model
    ) {
        return "redirect:/";
    }
}
