package alexdigioia.s6l5.payloads.prenotazioni;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record NewPrenotazioneDTO(

        @NotNull(message = "La data di prenotazione è obbligatoria.")
        LocalDate dataPrenotazione,

        @Size(min = 0, max = 200, message = "Il numero caratteri delle preferenze deve essere compreso tra 0 e 200 caratteri")
        String preferenze,

        @NotNull(message = "Il viaggio è obbligatorio.")
        @Size(min = 36, max = 36, message = "L'id del viaggio deve avere 36 caratteri")
        String viaggio,

        @NotNull(message = "Il dipendente è obbligatorio.")
        @Size(min = 36, max = 36, message = "L'id del dipendente deve avere 36 caratteri")
        String dipendente

) {
}
