package be.vdab.beers.controllers;

import be.vdab.beers.domain.BestelBon;
import be.vdab.beers.domain.BestelBonLijn;
import be.vdab.beers.services.MandjeService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.math.BigDecimal;

@Controller
@RequestMapping("mandje")
public class MandjeController {
    private final MandjeService mandjeService;

    public MandjeController(MandjeService mandjeService) {
        this.mandjeService = mandjeService;
    }

    @GetMapping
    public ModelAndView toonMandje() {
        var bestelBonLijnen = mandjeService.findBestelBonLijnen();
        return new ModelAndView("mandje")
                .addObject("bestelbonLijnen", bestelBonLijnen)
                .addObject("totaal", bestelBonLijnen.stream()
                        .map(BestelBonLijn::getPrijs)
                        .reduce(BigDecimal.ZERO, BigDecimal::add))
                .addObject(new BestelBon(0, "", "", "", 1000, ""));
    }

    @PostMapping("bevestig")
    public ModelAndView bevestig(@Valid BestelBon bestelBon, Errors errors) {
        if (errors.hasErrors()) {
            var bestelBonLijnen = mandjeService.findBestelBonLijnen();
            return new ModelAndView("mandje")
                    .addObject("bestelbonLijnen", bestelBonLijnen)
                    .addObject("totaal", bestelBonLijnen.stream()
                            .map(BestelBonLijn::getPrijs)
                            .reduce(BigDecimal.ZERO, BigDecimal::add));
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
