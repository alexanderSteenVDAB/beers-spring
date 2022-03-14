package be.vdab.beers.controllers;

import be.vdab.beers.sessions.Mandje;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class MandjeControllerAdvice {
    private final Mandje mandje;

    public MandjeControllerAdvice(Mandje mandje) {
        this.mandje = mandje;
    }

    @ModelAttribute
    void extraDataToevoegenAanModel(Model model) {
        model.addAttribute("isMandjeGevuld", mandje.isMandjeGevuld());
    }
}
