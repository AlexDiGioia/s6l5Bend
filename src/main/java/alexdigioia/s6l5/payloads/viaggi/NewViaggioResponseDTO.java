package alexdigioia.s6l5.payloads.viaggi;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record NewViaggioResponseDTO(
        @NotNull(message = "L'id è obbligatorio")
        UUID idViaggio
) {
}
