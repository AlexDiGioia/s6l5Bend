package alexdigioia.s6l5.payloads.viaggi;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record NewViaggioDTO(
        @NotEmpty(message = "La destinazione è obbligatoria")
        @Size(min = 3, max = 30, message = "Il numero caratteri dell destinazione deve essere compreso tra 3 e 30 caratteri")
        String destinazione,
        @NotNull(message = "La data partenza è obbligatorio")
        LocalDate dataPartenza,
        @NotEmpty(message = "Lo stato è obbligatorio")
        @Size(min = 3, max = 30, message = "Il numero caratteri dello stato deve essere compreso tra 3 e 30 caratteri")
        String stato
) {
}
