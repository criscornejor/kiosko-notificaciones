package cl.kiosko.ms_notificaciones.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificacionRequestDTO {

    @NotBlank(message = "El destinatario es obligatorio")
    @Email(message = "El formato del correo es inválido")
    private String destinatario;

    @NotBlank(message = "El nombre de la plantilla es obligatorio")
    private String nombrePlantilla;

    private Map<String, String> variables;
}
