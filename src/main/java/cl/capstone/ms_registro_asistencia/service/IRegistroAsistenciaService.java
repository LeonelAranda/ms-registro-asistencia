package cl.capstone.ms_registro_asistencia.service;

import java.util.List;

import cl.capstone.ms_registro_asistencia.dto.FiltroAsistenciaDTO;
import cl.capstone.ms_registro_asistencia.dto.RegistroAsistenciaDTO;
import cl.capstone.ms_registro_asistencia.model.RegistroAsistencia;

public interface IRegistroAsistenciaService {

    public List<RegistroAsistencia> getRegistroAsistencias();

    public RegistroAsistencia saveRegistroAsistencias(RegistroAsistencia registroAsistencia);

    public void deleteRegistroAsistencia(Long id);

    public RegistroAsistencia findRegistroAsistencia(Long id);

    public void editRegistroAsistencia(RegistroAsistencia registroAsistencia);

    public List<RegistroAsistencia> findByIdFaena(int idFaena);

    public List<RegistroAsistenciaDTO> obtenerRegistrosHistoricos(FiltroAsistenciaDTO filtro);

    public List<RegistroAsistenciaDTO> obtenerRegistrosDiario(FiltroAsistenciaDTO filtro);

}
