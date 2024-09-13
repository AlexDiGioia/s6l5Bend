package alexdigioia.s6l5.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "prenotazioni")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Prenotazione {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;
    private LocalDate dataPrenotazione;
    private String preferenze;

    @ManyToOne
    @JoinColumn(name = "viaggio_id")
    private Viaggio viaggio;
    @ManyToOne
    @JoinColumn(name = "dipendente_id")
    private Dipendente dipendente;


    public Prenotazione(LocalDate data, String preferenze, Viaggio viaggio, Dipendente dipendente) {
        this.dataPrenotazione = data;
        this.preferenze = preferenze;
        this.viaggio = viaggio;
        this.dipendente = dipendente;
    }
}
