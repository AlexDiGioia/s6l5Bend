package alexdigioia.s6l5.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "viaggi")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Viaggio {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;
    private String destinazione;
    private LocalDate dataPartenza;
    private String stato;

    public Viaggio(String destinazione, LocalDate dataPartenza, String stato) {
        this.destinazione = destinazione;
        this.dataPartenza = dataPartenza;
        this.stato = stato;
    }
}
