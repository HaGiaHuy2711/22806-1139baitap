package fit.hutech.spring.controllers;

import fit.hutech.spring.models.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/")
public class HomeController {

    // Trang chủ
    @GetMapping
    public String home() {
        return "home/index";
    }

    // Danh sách môn học
    @GetMapping("/subjects")
    public String subjects(Model model) {
        List<Subject> list = List.of(
            new Subject("SE101", "Lập trình Java", "/images/java.jpg"),
            new Subject("SE102", "Lập trình c/c++", "/images/Lap trinh c.jpg"),
            new Subject("SE103", "Công nghệ phần mềm", "/images/Sach cong nghe phan mem.jpg")
        );
        model.addAttribute("subjects", list);
        return "home/subjects";
    }

    // Trang liên hệ
    @GetMapping("/contact")
    public String contact(Model model) {
        model.addAttribute("today", LocalDate.now());
        return "home/contact";
    }

    // Trang kết quả
    @PostMapping("/contact-result")
    public String contactResult(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String message,
            @RequestParam String date,
            Model model
    ) {
        model.addAttribute("name", name);
        model.addAttribute("email", email);
        model.addAttribute("message", message);
        model.addAttribute("date", date);

        return "home/result";
    }
}


