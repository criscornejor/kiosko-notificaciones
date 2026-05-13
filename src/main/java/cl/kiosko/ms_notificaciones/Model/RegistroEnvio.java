package cl.kiosko.ms_notificaciones.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "registroEnvio")
public class RegistroEnvio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String destinatario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plantilla_id", nullable = false)
    private PlantillaMensaje plantillaMensaje;

    @Column(name = "fecha_envio", nullable = false)
    private LocalDateTime fechaEnvio;

    @Column(nullable = false, length = 20)
    private String estado;

    @Column(name = "mensaje_error", columnDefinition = "TEXT")
    private String mensajeError;

    @Column(columnDefinition = "TEXT")
    private String contenidoEnviado;
}
