package alexdigioia.s6l5.services;

import alexdigioia.s6l5.entities.Dipendente;
import alexdigioia.s6l5.entities.Prenotazione;
import alexdigioia.s6l5.entities.Viaggio;
import alexdigioia.s6l5.exceptions.BadRequestException;
import alexdigioia.s6l5.exceptions.NotFoundException;
import alexdigioia.s6l5.payloads.prenotazioni.NewPrenotazioneDTO;
import alexdigioia.s6l5.repositories.DipendentiRepository;
import alexdigioia.s6l5.repositories.PrenotazioniRepository;
import alexdigioia.s6l5.repositories.ViaggiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class PrenotazioniService {

    @Autowired
    private PrenotazioniRepository prenotazioniRepository;

    @Autowired
    private ViaggiRepository viaggiRepository;

    @Autowired
    private DipendentiRepository dipendentiRepository;

    public Prenotazione save(NewPrenotazioneDTO newPrenotazioneDTO) {

        //prima ottengo il viaggio e il dipendente grazie all'id che passo tramite il payload
        Viaggio viaggio = viaggiRepository.findById(UUID.fromString(newPrenotazioneDTO.viaggio()))
                .orElseThrow(() -> new NotFoundException(UUID.fromString(newPrenotazioneDTO.viaggio())));

        Dipendente dipendente = dipendentiRepository.findById(UUID.fromString(newPrenotazioneDTO.dipendente()))
                .orElseThrow(() -> new NotFoundException(UUID.fromString(newPrenotazioneDTO.dipendente())));

        //poi controllo se ci sono già prenotazioni con stesso Dipendente e viaggio con stessa data di partenza
        Optional<Prenotazione> prenotazioneEsistente = prenotazioniRepository
                .findByDipendenteAndViaggio_DataPartenza(dipendente, viaggio.getDataPartenza());

        if (prenotazioneEsistente.isPresent()) {
            throw new BadRequestException("Questo Dipendente ha già prenotato un viaggio per questa data:  "
                    + viaggio.getDataPartenza() + ".");
        }

        Prenotazione prenotazione = new Prenotazione(
                newPrenotazioneDTO.dataPrenotazione(),
                newPrenotazioneDTO.preferenze(),
                viaggio,
                dipendente
        );

        return prenotazioniRepository.save(prenotazione);
    }

    public Page<Prenotazione> findAll(int page, int size, String sortBy) {
        if (page > 100) page = 100;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.prenotazioniRepository.findAll(pageable);
    }

    public Prenotazione findById(UUID idPrenotazione) {
        return this.prenotazioniRepository.findById(idPrenotazione).orElseThrow(() -> new NotFoundException(idPrenotazione));
    }

    public void findByIdAndDelete(UUID idPrenotazione) {
        this.prenotazioniRepository.delete(this.findById(idPrenotazione));
    }

    public Prenotazione findByIdAndUpdate(UUID idPrenotazione, NewPrenotazioneDTO newPrenotazioneDTO) {
        //risolto come nel save
        Prenotazione prenotazione = prenotazioniRepository.findById(idPrenotazione)
                .orElseThrow(() -> new NotFoundException(idPrenotazione));

        Viaggio viaggio = viaggiRepository.findById(UUID.fromString(newPrenotazioneDTO.viaggio()))
                .orElseThrow(() -> new NotFoundException(UUID.fromString(newPrenotazioneDTO.viaggio())));

        Dipendente dipendente = dipendentiRepository.findById(UUID.fromString(newPrenotazioneDTO.dipendente()))
                .orElseThrow(() -> new NotFoundException(UUID.fromString(newPrenotazioneDTO.dipendente())));


        Optional<Prenotazione> prenotazioneEsistente = prenotazioniRepository
                .findByDipendenteAndViaggio_DataPartenza(dipendente, viaggio.getDataPartenza());

        if (prenotazioneEsistente.isPresent() && !prenotazioneEsistente.get().getIdPrenotazione().equals(idPrenotazione)) {
            throw new BadRequestException("Questo Dipendente ha già prenotato un viaggio per questa data:  "
                    + viaggio.getDataPartenza() + ".");
        }

        prenotazione.setDataPrenotazione(newPrenotazioneDTO.dataPrenotazione());
        prenotazione.setPreferenze(newPrenotazioneDTO.preferenze());
        prenotazione.setViaggio(viaggio);
        prenotazione.setDipendente(dipendente);

        return prenotazioniRepository.save(prenotazione);
    }
    
}