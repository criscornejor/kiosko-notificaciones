package cl.kiosko.ms_notificaciones.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlantillaMensajeDTO extends RepresentationModel<PlantillaMensajeDTO> {
    private Long id;
    private String nombre;
    private String asunto;
    private String contenido;
}
