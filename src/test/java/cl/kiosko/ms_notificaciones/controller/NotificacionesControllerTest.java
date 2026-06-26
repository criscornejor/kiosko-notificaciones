package cl.kiosko.ms_notificaciones.controller;

import cl.kiosko.ms_notificaciones.DTO.NotificacionRequestDTO;
import cl.kiosko.ms_notificaciones.DTO.PlantillaMensajeDTO;
import cl.kiosko.ms_notificaciones.DTO.RegistroEnvioDTO;
import cl.kiosko.ms_notificaciones.Service.NotificacionesService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificacionesControllerTest {

    @Mock
    private NotificacionesService notificacionesService;

    @InjectMocks
    private NotificacionesController controller;

    @Test
    void enviarNotificacion_RetornaCreatedConLinks() {
        NotificacionRequestDTO request = new NotificacionRequestDTO("test@mail.com", "BIENVENIDA", Map.of("nombre", "Ana"));
        when(notificacionesService.procesarEnvioNotificacion(request)).thenReturn(registroResponse());

        ResponseEntity<RegistroEnvioDTO> response = controller.enviarNotificacion(request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().hasLink("self"));
    }

    @Test
    void listarPlantillas_RetornaListaConLinks() {
        when(notificacionesService.listarPlantillas()).thenReturn(List.of(plantillaResponse()));

        ResponseEntity<List<PlantillaMensajeDTO>> response = controller.listarPlantillas();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertTrue(response.getBody().get(0).hasLink("self"));
    }

    @Test
    void crearPlantilla_RetornaCreatedConLinks() {
        PlantillaMensajeDTO request = plantillaResponse();
        when(notificacionesService.crearPlantilla(request)).thenReturn(plantillaResponse());

        ResponseEntity<PlantillaMensajeDTO> response = controller.crearPlantilla(request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertTrue(response.getBody().hasLink("self"));
        assertTrue(response.getBody().hasLink("plantillas"));
    }

    private RegistroEnvioDTO registroResponse() {
        return RegistroEnvioDTO.builder()
                .id(1L)
                .destinatario("test@mail.com")
                .nombrePlantilla("BIENVENIDA")
                .fechaEnvio(LocalDateTime.now())
                .estado("ENVIADO")
                .contenidoEnviado("Hola")
                .build();
    }

    private PlantillaMensajeDTO plantillaResponse() {
        return PlantillaMensajeDTO.builder()
                .id(1L)
                .nombre("BIENVENIDA")
                .asunto("Hola")
                .contenido("Hola {nombre}")
                .build();
    }
}
