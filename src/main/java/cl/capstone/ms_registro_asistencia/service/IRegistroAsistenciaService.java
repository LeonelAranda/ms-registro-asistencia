package cl.capstone.ms_registro_asistencia.service;

import java.util.List;

import cl.capstone.ms_registro_asistencia.model.RegistroAsistencia;

public interface IRegistroAsistenciaService {

    public List<RegistroAsistencia> getRegistroAsistencias();

    public void saveRegistroAsistencias(RegistroAsistencia registroAsistencia);

    public void deleteRegistroAsistencia(Long id);

    public RegistroAsistencia findRegistroAsistencia(Long id);

    public void editRegistroAsistencia(RegistroAsistencia registroAsistencia);
}
