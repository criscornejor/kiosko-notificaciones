package cl.kiosko.ms_notificaciones.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "plantillasMensaje")
public class PlantillaMensaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String nombre;

    @Column(nullable = false)
    private String asunto;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String contenido;
    
    @OneToMany(mappedBy = "plantillaMensaje", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RegistroEnvio> registrosEnvio;

}
