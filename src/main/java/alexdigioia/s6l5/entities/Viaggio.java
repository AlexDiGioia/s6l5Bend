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
    private String descrizione;
    private LocalDate dataPartenza;
    private String stato;
}
