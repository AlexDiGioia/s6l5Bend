package alexdigioia.s6l5.repositories;

import alexdigioia.s6l5.entities.Dipendente;
import alexdigioia.s6l5.entities.Prenotazione;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

public interface PrenotazioniRepository extends JpaRepository<Prenotazione, UUID> {

    Optional<Prenotazione> findByDipendenteAndViaggio_DataPartenza(Dipendente dipendente, LocalDate dataPartenza);
}
