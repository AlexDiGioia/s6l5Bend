package alexdigioia.s6l5.services;

import alexdigioia.s6l5.entities.Prenotazione;
import alexdigioia.s6l5.exceptions.BadRequestException;
import alexdigioia.s6l5.payloads.prenotazioni.NewPrenotazioneDTO;
import alexdigioia.s6l5.repositories.DipendentiRepository;
import alexdigioia.s6l5.repositories.PrenotazioniRepository;
import alexdigioia.s6l5.repositories.ViaggiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PrenotazioniService {

    @Autowired
    private PrenotazioniRepository prenotazioniRepository;

    @Autowired
    private ViaggiRepository viaggiRepository;

    @Autowired
    private DipendentiRepository dipendentiRepository;

    public Prenotazione save(NewPrenotazioneDTO newPrenotazioneDTO) {

        Optional<Prenotazione> prenotazioneEsistente = prenotazioniRepository
                .findByDipendenteAndViaggio_DataPartenza(
                        newPrenotazioneDTO.dipendente(),
                        newPrenotazioneDTO.viaggio().getDataPartenza()
                );

        if (prenotazioneEsistente.isPresent()) {
            throw new BadRequestException("Esiste già una prenotazione uguale per questo dipendente"
                    + newPrenotazioneDTO.viaggio().getDataPartenza() + ".");
        }
        Prenotazione prenotazione = new Prenotazione(
                newPrenotazioneDTO.dataPrenotazione(),
                newPrenotazioneDTO.preferenze(),
                newPrenotazioneDTO.viaggio(),
                newPrenotazioneDTO.dipendente()
        );
        return prenotazioniRepository.save(prenotazione);
    }
}
