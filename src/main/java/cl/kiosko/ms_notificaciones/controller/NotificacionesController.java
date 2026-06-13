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
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

@RestController
@RequestMapping("/api/notificaciones")
@RequiredArgsConstructor
public class NotificacionesController {

    private final NotificacionesService notificacionesService;

    @PostMapping("/enviar")
    public ResponseEntity<RegistroEnvioDTO> enviarNotificacion(@Valid @RequestBody NotificacionRequestDTO request) {
        RegistroEnvioDTO response = notificacionesService.procesarEnvioNotificacion(request);
        response.add(linkTo(methodOn(NotificacionesController.class).enviarNotificacion(null)).withSelfRel());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/plantillas")
    public ResponseEntity<List<PlantillaMensajeDTO>> listarPlantillas() {
        List<PlantillaMensajeDTO> plantillas = notificacionesService.listarPlantillas();
        plantillas.forEach(plantilla -> 
            plantilla.add(linkTo(methodOn(NotificacionesController.class).listarPlantillas()).withSelfRel())
        );
        return ResponseEntity.ok(plantillas);
    }

    @PostMapping("/plantillas")
    public ResponseEntity<PlantillaMensajeDTO> crearPlantilla(@RequestBody PlantillaMensajeDTO plantillaDTO) {
        PlantillaMensajeDTO response = notificacionesService.crearPlantilla(plantillaDTO);
        response.add(linkTo(methodOn(NotificacionesController.class).crearPlantilla(null)).withSelfRel());
        response.add(linkTo(methodOn(NotificacionesController.class).listarPlantillas()).withRel("plantillas"));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
