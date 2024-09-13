package alexdigioia.s6l5.services;

import alexdigioia.s6l5.entities.Viaggio;
import alexdigioia.s6l5.exceptions.BadRequestException;
import alexdigioia.s6l5.exceptions.NotFoundException;
import alexdigioia.s6l5.payloads.viaggi.NewViaggioDTO;
import alexdigioia.s6l5.payloads.viaggi.StatoViaggioDTO;
import alexdigioia.s6l5.repositories.ViaggiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class ViaggiService {

    @Autowired
    private ViaggiRepository viaggiRepository;

    public Viaggio save(NewViaggioDTO newViaggioDTO) {
        if (newViaggioDTO.dataPartenza().isBefore(LocalDate.now())) {
            throw new BadRequestException("La data di partenza non può essere nel passato.");
        }
        Viaggio viaggio = new Viaggio(newViaggioDTO.destinazione(), newViaggioDTO.dataPartenza(), newViaggioDTO.stato());

        return viaggiRepository.save(viaggio);
    }

    public Page<Viaggio> findAll(int page, int size, String sortBy) {
        if (page > 100) page = 100;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.viaggiRepository.findAll(pageable);
    }

    public Viaggio findById(UUID viaggioId) {
        return this.viaggiRepository.findById(viaggioId).orElseThrow(() -> new NotFoundException(viaggioId));
    }

    public void findByIdAndDelete(UUID viaggioId) {
        Viaggio found = this.findById(viaggioId);
        this.viaggiRepository.delete(found);
    }

    public Viaggio findByIdAndUpdate(UUID idViaggio, NewViaggioDTO newViaggioDTO) {
        Viaggio viaggio = viaggiRepository.findById(idViaggio)
                .orElseThrow(() -> new NotFoundException(idViaggio));

        viaggio.setDestinazione(newViaggioDTO.destinazione());
        viaggio.setDataPartenza(newViaggioDTO.dataPartenza());
        viaggio.setStato(newViaggioDTO.stato());

        return viaggiRepository.save(viaggio);
    }

    public Viaggio findByIdAndUpdateStato(UUID viaggioId, StatoViaggioDTO updatedStatoViaggioDTO) {
        Viaggio viaggio = viaggiRepository.findById(viaggioId)
                .orElseThrow(() -> new NotFoundException(viaggioId));

        viaggio.setStato(updatedStatoViaggioDTO.stato());

        return viaggiRepository.save(viaggio);
    }

}
