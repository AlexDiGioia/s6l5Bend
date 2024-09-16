package alexdigioia.s6l5.controllers;

import alexdigioia.s6l5.entities.Viaggio;
import alexdigioia.s6l5.exceptions.BadRequestException;
import alexdigioia.s6l5.payloads.viaggi.NewViaggioDTO;
import alexdigioia.s6l5.payloads.viaggi.NewViaggioResponseDTO;
import alexdigioia.s6l5.payloads.viaggi.StatoViaggioDTO;
import alexdigioia.s6l5.services.ViaggiService;
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
@RequestMapping("/viaggi")
public class ViaggiController {

    @Autowired
    private ViaggiService viaggiService;

    // GET http://localhost:3001/viaggi
    @GetMapping
    public Page<Viaggio> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "dataPartenza") String sortBy) {
        return this.viaggiService.findAll(page, size, sortBy);
    }

    // GET http://localhost:3001/viaggi/{idViaggio}
    @GetMapping("/{idViaggio}")
    public Viaggio findById(@RequestParam UUID idViaggio) {
        return this.viaggiService.findById(idViaggio);
    }

    // POST http://localhost:3001/viaggi + BODY
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NewViaggioResponseDTO save(@RequestBody @Validated NewViaggioDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            String messages = validationResult.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.joining(". "));
            throw new BadRequestException(messages);
        } else {
            return new NewViaggioResponseDTO(this.viaggiService.save(body).getIdViaggio());
        }
    }

    // DELETE http://localhost:3001/viaggi/{idViaggio}
    @DeleteMapping("/{idViaggio}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findByIdAndDelete(@PathVariable UUID idViaggio) {
        this.viaggiService.findByIdAndDelete(idViaggio);
    }

    // PUT http://localhost:3001/viaggi/{idViaggio} + BODY
    @PutMapping("/{idViaggio}")
    public Viaggio findByIdAndUpdate(@PathVariable UUID idViaggio, @RequestBody NewViaggioDTO body) {
        return this.viaggiService.findByIdAndUpdate(idViaggio, body);
    }

    // PUT http://localhost:3001/viaggi/{idViaggio}/stato + BODY
    @PutMapping("/{idViaggio}/stato")
    public Viaggio findByIdAndUpdateState(@PathVariable UUID idViaggio, @RequestBody StatoViaggioDTO body) {
        return this.viaggiService.findByIdAndUpdateStato(idViaggio, body);
    }

}
