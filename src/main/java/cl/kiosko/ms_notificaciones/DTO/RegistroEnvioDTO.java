package cl.kiosko.ms_notificaciones.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegistroEnvioDTO {
    private Long id;
    private String destinatario;
    private String nombrePlantilla;
    private LocalDateTime fechaEnvio;
    private String estado;
    private String mensajeError;
    private String contenidoEnviado;
}
