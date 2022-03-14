package be.vdab.beers.controllers;

import be.vdab.beers.forms.BestelBonLijnForm;
import be.vdab.beers.services.BierService;
import be.vdab.beers.sessions.Mandje;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping("bieren")
public class BierenController {
    private final BierService service;
    private final Mandje mandje;

    public BierenController(BierService service, Mandje mandje) {
        this.service = service;
        this.mandje = mandje;
    }

    @GetMapping("brouwer/{id}")
    public ModelAndView bierenVanBrouwer(@PathVariable long id) {
        return new ModelAndView("bieren-brouwer", "bieren", service.findByBrouwer(id));
    }

    @GetMapping("{id}")
    public ModelAndView bierById(@PathVariable long id) {
        var modelAndView = new ModelAndView("bier");
        service.findById(id).ifPresent(bier -> modelAndView.addObject(bier)
                .addObject(new BestelBonLijnForm(bier.getId(), 1)));
        return modelAndView;
    }

    @PostMapping("toevoegen")
    public ModelAndView voegToe(@Valid BestelBonLijnForm bestelBonLijnForm, Errors errors) {
        if (errors.hasErrors()) {
            var modelAndView = new ModelAndView("bier");
            service.findById(bestelBonLijnForm.getBierId()).ifPresent(bier -> modelAndView.addObject(bier)
                    .addObject(new BestelBonLijnForm(bier.getId(), 1)));
            return modelAndView;
        }
        mandje.voegToe(bestelBonLijnForm.getBierId(), bestelBonLijnForm.getAantal());
        return new ModelAndView("redirect:/mandje");
    }
}
