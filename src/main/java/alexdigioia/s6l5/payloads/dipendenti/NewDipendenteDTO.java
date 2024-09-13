package alexdigioia.s6l5.payloads.dipendenti;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record NewDipendenteDTO(
        @NotEmpty(message = "L'username è obbligatorio")
        @Size(min = 3, max = 30, message = "Il numero caratteri dell' username deve essere compreso tra 3 e 30 caratteri")
        String username,
        @NotEmpty(message = "Il nome è obbligatorio")
        @Size(min = 3, max = 30, message = "Il numero caratteri del nome deve essere compreso tra 3 e 30 caratteri")
        String nome,
        @NotEmpty(message = "Il cognome è obbligatorio")
        @Size(min = 3, max = 30, message = "Il numero caratteri del cognome deve essere compreso tra 3 e 30 caratteri")
        String cognome,
        @NotEmpty(message = "L'email è obbligatoria")
        @Email(message = "L'email inserita non è valida")
        String email
) {
}
