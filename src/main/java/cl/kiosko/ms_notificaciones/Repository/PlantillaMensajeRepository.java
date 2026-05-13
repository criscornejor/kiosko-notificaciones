package cl.kiosko.ms_notificaciones.Repository;

import cl.kiosko.ms_notificaciones.model.PlantillaMensaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlantillaMensajeRepository extends JpaRepository<PlantillaMensaje, Long> {
    Optional<PlantillaMensaje> findByNombre(String nombre);
}
