package alexdigioia.s6l5.payloads.dipendenti;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record NewDipendenteResponseDTO(
        @NotNull(message = "L'id è obbligatorio")
        UUID idDipendente
) {

}