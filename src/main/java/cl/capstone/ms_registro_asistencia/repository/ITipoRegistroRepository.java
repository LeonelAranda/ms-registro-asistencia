package cl.capstone.ms_registro_asistencia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.capstone.ms_registro_asistencia.model.TipoRegistro;

@Repository
public interface ITipoRegistroRepository extends JpaRepository<TipoRegistro, Long> {

}
