package cl.capstone.ms_registro_asistencia.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.capstone.ms_registro_asistencia.model.RegistroAsistencia;

@Repository
public interface IRegistroAsistenciaRepository extends JpaRepository<RegistroAsistencia, Long> {

    public List<RegistroAsistencia> findByIdFaena(int idFaena);

}
