package alexdigioia.s6l5.controllers;

import alexdigioia.s6l5.entities.Prenotazione;
import alexdigioia.s6l5.exceptions.BadRequestException;
import alexdigioia.s6l5.payloads.prenotazioni.NewPrenotazioneDTO;
import alexdigioia.s6l5.payloads.prenotazioni.NewPrenotazioneResponseDTO;
import alexdigioia.s6l5.services.PrenotazioniService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/prenotazioni")
public class PrenotazioniController {

    @Autowired
    private PrenotazioniService prenotazioniService;

    // GET http://localhost:3001/prenotazioni
    @GetMapping
    public Page<Prenotazione> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "dataPrenotazione") String sortBy) {
        return this.prenotazioniService.findAll(page, size, sortBy);
    }

    // GET http://localhost:3001/prenotazioni/{idPrenotazione}
    @GetMapping("/{idPrenotazione}")
    public Prenotazione findById(@RequestParam UUID idPrenotazione) {
        return this.prenotazioniService.findById(idPrenotazione);
    }


    // POST http://localhost:3001/prenotazioni + BODY
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NewPrenotazioneResponseDTO save(@RequestBody @Validated NewPrenotazioneDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            String messages = validationResult.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.joining(". "));
            throw new BadRequestException(messages);
        } else {
            return new NewPrenotazioneResponseDTO(this.prenotazioniService.save(body).getIdPrenotazione());
        }
    }

    // DELETE http://localhost:3001/prenotazioni/{idPrenotazione}
    @DeleteMapping("/{idPrenotazione}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findByIdAndDelete(@PathVariable UUID idPrenotazione) {
        this.prenotazioniService.findByIdAndDelete(idPrenotazione);
    }


    // PUT http://localhost:3001/prenotazioni/{idPrenotazione} + BODY
    @PutMapping("/{idPrenotazione}")
    public Prenotazione findByIdAndUpdate(@PathVariable UUID idPrenotazione, @RequestBody NewPrenotazioneDTO body) {
        return this.prenotazioniService.findByIdAndUpdate(idPrenotazione, body);
    }
}
