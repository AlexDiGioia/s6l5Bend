package alexdigioia.s6l5.payloads.prenotazioni;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record NewPrenotazioneResponseDTO(
        @NotNull(message = "L'id è obbligatorio")
        UUID idPrenotazione
) {
}
