package be.vdab.beers.controllers;

import be.vdab.beers.services.BierService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping
public class IndexController {
    private final BierService bierService;

    public IndexController(BierService bierService) {
        this.bierService = bierService;
    }

    @GetMapping
    public ModelAndView index() {
        return new ModelAndView("index", "aantal", bierService.findAantal());
    }
}
