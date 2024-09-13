package alexdigioia.s6l5.controllers;

import alexdigioia.s6l5.entities.Dipendente;
import alexdigioia.s6l5.exceptions.BadRequestException;
import alexdigioia.s6l5.payloads.dipendenti.NewDipendenteDTO;
import alexdigioia.s6l5.payloads.dipendenti.NewDipendenteResponseDTO;
import alexdigioia.s6l5.services.DipendentiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/dipendenti")
public class DipendentiController {

    @Autowired
    private DipendentiService dipendentiService;

    // GET http://localhost:3001/dipendenti
    @GetMapping
    public Page<Dipendente> getAllDipendenti(@RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "10") int size,
                                             @RequestParam(defaultValue = "id") String sortBy) {
        return this.dipendentiService.findAll(page, size, sortBy);
    }


    // POST http://localhost:3001/dipendenti + BODY
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NewDipendenteResponseDTO save(@RequestBody @Validated NewDipendenteDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            String messages = validationResult.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.joining(". "));
            throw new BadRequestException(messages);
        } else {
            return new NewDipendenteResponseDTO(this.dipendentiService.save(body).getIdDipendente());
        }
    }

    // GET http://localhost:3001/dipendenti/{idDipendente}
    @GetMapping("/{idDipendente}")
    public Dipendente findById(@PathVariable UUID DipendenteId) {
        return this.dipendentiService.findById(DipendenteId);
    }

    // DELETE http://localhost:3001/dipendenti/{idDipendente}
    @DeleteMapping("/{idDipendente}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findByIdAndDelete(@PathVariable UUID idDipendente) {
        this.dipendentiService.findByIdAndDelete(idDipendente);
    }

    // PUT http://localhost:3001/dipendenti/{idDipendente} + BODY
    @PutMapping("/{idDipendente}")
    public Dipendente findByIdAndUpdate(@PathVariable UUID idDipendente, @RequestBody NewDipendenteDTO body) {
        return this.dipendentiService.findByIdAndUpdate(idDipendente, body);
    }

    // POST http://localhost:3001/dipendenti/{idDipendente}/avatarURL
    @PostMapping("/{idDipendente}/avatar")
    public void uploadImage(@RequestParam("avatar") MultipartFile img, @PathVariable UUID idDipendente) throws IOException {
        this.dipendentiService.uploadImage(idDipendente, img);
    }


}
