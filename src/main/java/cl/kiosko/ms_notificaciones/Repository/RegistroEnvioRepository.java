package cl.kiosko.ms_notificaciones.Repository;

import cl.kiosko.ms_notificaciones.model.RegistroEnvio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegistroEnvioRepository extends JpaRepository<RegistroEnvio, Long> {
}
