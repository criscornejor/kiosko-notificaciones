package cl.kiosko.ms_notificaciones.Service;

import cl.kiosko.ms_notificaciones.DTO.NotificacionRequestDTO;
import cl.kiosko.ms_notificaciones.DTO.PlantillaMensajeDTO;
import cl.kiosko.ms_notificaciones.DTO.RegistroEnvioDTO;
import cl.kiosko.ms_notificaciones.exception.PlantillaNoEncontradaException;
import cl.kiosko.ms_notificaciones.model.PlantillaMensaje;
import cl.kiosko.ms_notificaciones.model.RegistroEnvio;
import cl.kiosko.ms_notificaciones.Repository.PlantillaMensajeRepository;
import cl.kiosko.ms_notificaciones.Repository.RegistroEnvioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificacionesServiceTest {

    @Mock
    private PlantillaMensajeRepository plantillaRepository;

    @Mock
    private RegistroEnvioRepository registroEnvioRepository;

    @InjectMocks
    private NotificacionesService notificacionesService;

    private PlantillaMensaje plantillaMock;

    @BeforeEach
    void setUp() {
        plantillaMock = new PlantillaMensaje();
        plantillaMock.setId(1L);
        plantillaMock.setNombre("BIENVENIDA");
        plantillaMock.setAsunto("Bienvenido {nombre}");
        plantillaMock.setContenido("Hola {nombre}, bienvenido a la plataforma.");
    }

    @Test
    void procesarEnvioNotificacion_Exito() {
        // Arrange
        Map<String, String> variables = new HashMap<>();
        variables.put("nombre", "Kurisu");

        NotificacionRequestDTO request = new NotificacionRequestDTO();
        request.setDestinatario("usuario@test.com");
        request.setNombrePlantilla("BIENVENIDA");
        request.setVariables(variables);

        when(plantillaRepository.findByNombre("BIENVENIDA")).thenReturn(Optional.of(plantillaMock));

        RegistroEnvio registroGuardado = new RegistroEnvio();
        registroGuardado.setId(100L);
        registroGuardado.setDestinatario("usuario@test.com");
        registroGuardado.setPlantillaMensaje(plantillaMock);
        registroGuardado.setFechaEnvio(LocalDateTime.now());
        registroGuardado.setEstado("ENVIADO");
        registroGuardado.setContenidoEnviado("Hola Kurisu, bienvenido a la plataforma.");

        when(registroEnvioRepository.save(any(RegistroEnvio.class))).thenReturn(registroGuardado);

        // Act
        RegistroEnvioDTO response = notificacionesService.procesarEnvioNotificacion(request);

        // Assert
        assertNotNull(response);
        assertEquals(100L, response.getId());
        assertEquals("ENVIADO", response.getEstado());
        assertEquals("Hola Kurisu, bienvenido a la plataforma.", response.getContenidoEnviado());
        verify(plantillaRepository, times(1)).findByNombre("BIENVENIDA");
        verify(registroEnvioRepository, times(1)).save(any(RegistroEnvio.class));
    }

    @Test
    void procesarEnvioNotificacion_PlantillaNoEncontrada() {
        // Arrange
        NotificacionRequestDTO request = new NotificacionRequestDTO();
        request.setDestinatario("usuario@test.com");
        request.setNombrePlantilla("INEXISTENTE");

        when(plantillaRepository.findByNombre("INEXISTENTE")).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(PlantillaNoEncontradaException.class, () -> {
            notificacionesService.procesarEnvioNotificacion(request);
        });
        verify(registroEnvioRepository, never()).save(any(RegistroEnvio.class));
    }

    @Test
    void crearPlantilla_Exito() {
        // Arrange
        PlantillaMensajeDTO request = new PlantillaMensajeDTO();
        request.setNombre("NUEVA");
        request.setAsunto("Asunto");
        request.setContenido("Contenido");

        PlantillaMensaje guardada = new PlantillaMensaje();
        guardada.setId(2L);
        guardada.setNombre("NUEVA");
        guardada.setAsunto("Asunto");
        guardada.setContenido("Contenido");

        when(plantillaRepository.save(any(PlantillaMensaje.class))).thenReturn(guardada);

        // Act
        PlantillaMensajeDTO response = notificacionesService.crearPlantilla(request);

        // Assert
        assertNotNull(response);
        assertEquals(2L, response.getId());
        assertEquals("NUEVA", response.getNombre());
        verify(plantillaRepository, times(1)).save(any(PlantillaMensaje.class));
    }
}
