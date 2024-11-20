package cl.capstone.ms_registro_asistencia.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.capstone.ms_registro_asistencia.model.RegistroAsistencia;
import cl.capstone.ms_registro_asistencia.repository.IRegistroAsistenciaRepository;

@Service
public class RegistroAsistenciaService implements IRegistroAsistenciaService {

    @Autowired
    IRegistroAsistenciaRepository registroAsistenciaRepository;

    @Override
    public List<RegistroAsistencia> getRegistroAsistencias() {

        List<RegistroAsistencia> listaRegistroAsistencias = registroAsistenciaRepository.findAll();
        return listaRegistroAsistencias;
    }

    @Override
    public RegistroAsistencia saveRegistroAsistencias(
            cl.capstone.ms_registro_asistencia.model.RegistroAsistencia registroAsistencia) {

        registroAsistenciaRepository.save(registroAsistencia);

        return registroAsistencia;
    }

    @Override
    public void deleteRegistroAsistencia(Long id) {

        registroAsistenciaRepository.deleteById(id);
    }

    @Override
    public RegistroAsistencia findRegistroAsistencia(Long id) {

        RegistroAsistencia registroAsistencia = registroAsistenciaRepository.findById(id).orElse(null);
        return registroAsistencia;
    }

    @Override
    public void editRegistroAsistencia(RegistroAsistencia registroAsistencia) {

        this.saveRegistroAsistencias(registroAsistencia);

    }

    @Override
    public List<RegistroAsistencia> findByIdFaena(int idFaena) {

        return registroAsistenciaRepository.findByIdFaena(idFaena);
    }

}
