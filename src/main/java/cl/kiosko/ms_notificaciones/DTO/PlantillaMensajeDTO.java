package cl.kiosko.ms_notificaciones.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlantillaMensajeDTO {
    private Long id;
    private String nombre;
    private String asunto;
    private String contenido;
}
