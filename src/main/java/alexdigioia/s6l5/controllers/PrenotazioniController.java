package alexdigioia.s6l5.controllers;

import alexdigioia.s6l5.services.PrenotazioniService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/prenotazioni")
public class PrenotazioniController {

    private PrenotazioniService prenotazioniService;
}
