package es.SecondFlow;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GreetingController {
 @GetMapping("/greeting")
 public String greeting(Model model) {
 model.addAttribute("name", "World");
 return "greeting_template";
 }
 @GetMapping("/")
 public String principal(Model modelo) {
  return ("Formulario");
 }
}

