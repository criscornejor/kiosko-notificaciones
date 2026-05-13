package cl.kiosko.ms_notificaciones.controller;

import cl.kiosko.ms_notificaciones.DTO.NotificacionRequestDTO;
import cl.kiosko.ms_notificaciones.DTO.PlantillaMensajeDTO;
import cl.kiosko.ms_notificaciones.DTO.RegistroEnvioDTO;
import cl.kiosko.ms_notificaciones.Service.NotificacionesService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notificaciones")
@RequiredArgsConstructor
public class NotificacionesController {

    private final NotificacionesService notificacionesService;

    @PostMapping("/enviar")
    public ResponseEntity<RegistroEnvioDTO> enviarNotificacion(@Valid @RequestBody NotificacionRequestDTO request) {
        RegistroEnvioDTO response = notificacionesService.procesarEnvioNotificacion(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/plantillas")
    public ResponseEntity<List<PlantillaMensajeDTO>> listarPlantillas() {
        return ResponseEntity.ok(notificacionesService.listarPlantillas());
    }

    @PostMapping("/plantillas")
    public ResponseEntity<PlantillaMensajeDTO> crearPlantilla(@RequestBody PlantillaMensajeDTO plantillaDTO) {
        return new ResponseEntity<>(notificacionesService.crearPlantilla(plantillaDTO), HttpStatus.CREATED);
    }
}
