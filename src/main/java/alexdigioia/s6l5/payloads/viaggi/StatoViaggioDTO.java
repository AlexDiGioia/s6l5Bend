package alexdigioia.s6l5.payloads.viaggi;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record StatoViaggioDTO(
        @NotEmpty(message = "Lo stato è obbligatorio")
        @Size(min = 3, max = 30, message = "Il numero caratteri dello stato deve essere compreso tra 3 e 30 caratteri")
        String stato
) {
}
