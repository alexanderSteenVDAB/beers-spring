package be.vdab.beers.controllers;

import be.vdab.beers.services.BrouwerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("brouwer")
public class BrouwerController {
    private final BrouwerService service;

    public BrouwerController(BrouwerService service) {
        this.service = service;
    }

    @GetMapping
    public ModelAndView alleBrouwers() {
        return new ModelAndView("brouwers", "brouwers", service.findAll());
    }
}
