package cl.kiosko.ms_notificaciones.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegistroEnvioDTO extends RepresentationModel<RegistroEnvioDTO> {
    private Long id;
    private String destinatario;
    private String nombrePlantilla;
    private LocalDateTime fechaEnvio;
    private String estado;
    private String mensajeError;
    private String contenidoEnviado;
}
