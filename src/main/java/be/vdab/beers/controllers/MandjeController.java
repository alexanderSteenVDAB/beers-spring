package be.vdab.beers.controllers;

import be.vdab.beers.domain.BestelBon;
import be.vdab.beers.services.MandjeService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping("mandje")
public class MandjeController {
    private final MandjeService mandjeService;

    public MandjeController(MandjeService mandjeService) {
        this.mandjeService = mandjeService;
    }

    @GetMapping
    public ModelAndView toonMandje() {
        return new ModelAndView("mandje")
                .addObject("bestelbonLijnen", mandjeService.findBestelBonLijnen())
                .addObject("totaal", mandjeService.berekenTotaal())
                .addObject(new BestelBon(0, "", "", "", 1000, ""));
    }

    @PostMapping("bevestig")
    public ModelAndView bevestig(@Valid BestelBon bestelBon, Errors errors) {
        if (errors.hasErrors()) {
            return new ModelAndView("mandje")
                    .addObject("bestelbonLijnen", mandjeService.findBestelBonLijnen())
                    .addObject("totaal", mandjeService.berekenTotaal());
        }
        var modelAndView = new ModelAndView("bevestigd")
                .addObject("bestelBonId", mandjeService.bevestig(bestelBon));

        var mislukteBiernamen = mandjeService.getBierNamen();
        if (!mislukteBiernamen.isEmpty()) {
            modelAndView.addObject("nietBestaandeBieren", String.join(", ", mislukteBiernamen));
        }
        mandjeService.maakLeeg();
        return modelAndView;
    }
}
