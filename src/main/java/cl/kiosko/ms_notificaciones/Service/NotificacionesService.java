package cl.kiosko.ms_notificaciones.Service;

import cl.kiosko.ms_notificaciones.DTO.NotificacionRequestDTO;
import cl.kiosko.ms_notificaciones.DTO.PlantillaMensajeDTO;
import cl.kiosko.ms_notificaciones.DTO.RegistroEnvioDTO;
import cl.kiosko.ms_notificaciones.exception.PlantillaNoEncontradaException;
import cl.kiosko.ms_notificaciones.model.PlantillaMensaje;
import cl.kiosko.ms_notificaciones.model.RegistroEnvio;
import cl.kiosko.ms_notificaciones.Repository.PlantillaMensajeRepository;
import cl.kiosko.ms_notificaciones.Repository.RegistroEnvioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificacionesService {

    private final PlantillaMensajeRepository plantillaRepository;
    private final RegistroEnvioRepository registroEnvioRepository;

    @Transactional
    public RegistroEnvioDTO procesarEnvioNotificacion(NotificacionRequestDTO request) {
        log.info("Procesando envío de notificación para: {}", request.getDestinatario());

        PlantillaMensaje plantilla = plantillaRepository.findByNombre(request.getNombrePlantilla())
                .orElseThrow(() -> new PlantillaNoEncontradaException("Plantilla no encontrada: " + request.getNombrePlantilla()));

        String contenidoPersonalizado = procesarVariables(plantilla.getContenido(), request.getVariables());
        String estado = "ENVIADO";
        String errorMsg = null;

        // Simulando el envío (aquí iría la integración con MailSender, Twilio, etc)
        try {
            log.info("Enviando mensaje a {} - Asunto: {}", request.getDestinatario(), plantilla.getAsunto());
            log.debug("Contenido: {}", contenidoPersonalizado);
            // simulated delay for sending
            Thread.sleep(100); 
        } catch (Exception e) {
            log.error("Error al enviar notificación a {}", request.getDestinatario(), e);
            estado = "FALLIDO";
            errorMsg = e.getMessage();
        }

        RegistroEnvio registro = RegistroEnvio.builder()
                .destinatario(request.getDestinatario())
                .plantillaMensaje(plantilla)
                .fechaEnvio(LocalDateTime.now())
                .estado(estado)
                .mensajeError(errorMsg)
                .contenidoEnviado(contenidoPersonalizado)
                .build();

        registro = registroEnvioRepository.save(registro);

        return mapearARegistroDTO(registro);
    }

    @Transactional(readOnly = true)
    public List<PlantillaMensajeDTO> listarPlantillas() {
        return plantillaRepository.findAll().stream()
                .map(this::mapearAPlantillaDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public PlantillaMensajeDTO crearPlantilla(PlantillaMensajeDTO dto) {
        PlantillaMensaje plantilla = PlantillaMensaje.builder()
                .nombre(dto.getNombre())
                .asunto(dto.getAsunto())
                .contenido(dto.getContenido())
                .build();
        plantilla = plantillaRepository.save(plantilla);
        return mapearAPlantillaDTO(plantilla);
    }

    private String procesarVariables(String contenido, Map<String, String> variables) {
        if (variables == null || variables.isEmpty()) {
            return contenido;
        }
        String resultado = contenido;
        for (Map.Entry<String, String> entry : variables.entrySet()) {
            resultado = resultado.replace("{" + entry.getKey() + "}", entry.getValue());
        }
        return resultado;
    }

    private RegistroEnvioDTO mapearARegistroDTO(RegistroEnvio registro) {
        return RegistroEnvioDTO.builder()
                .id(registro.getId())
                .destinatario(registro.getDestinatario())
                .nombrePlantilla(registro.getPlantillaMensaje().getNombre())
                .fechaEnvio(registro.getFechaEnvio())
                .estado(registro.getEstado())
                .mensajeError(registro.getMensajeError())
                .contenidoEnviado(registro.getContenidoEnviado())
                .build();
    }

    private PlantillaMensajeDTO mapearAPlantillaDTO(PlantillaMensaje plantilla) {
        return PlantillaMensajeDTO.builder()
                .id(plantilla.getId())
                .nombre(plantilla.getNombre())
                .asunto(plantilla.getAsunto())
                .contenido(plantilla.getContenido())
                .build();
    }
}
