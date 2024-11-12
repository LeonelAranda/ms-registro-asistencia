package cl.capstone.ms_registro_asistencia.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.capstone.ms_registro_asistencia.model.TipoRegistro;
import cl.capstone.ms_registro_asistencia.repository.ITipoRegistroRepository;

@Service
public class TipoRegistroService implements ITipoRegistroService {

    @Autowired
    ITipoRegistroRepository tipoRegistroRepository;

    @Override
    public List<TipoRegistro> getTipoRegistros() {

        List<TipoRegistro> listaTipoRegistros = tipoRegistroRepository.findAll();
        return listaTipoRegistros;
    }

    @Override
    public TipoRegistro saveTipoRegistro(TipoRegistro tipoRegistro) {

        tipoRegistroRepository.save(tipoRegistro);

        return tipoRegistro;

    }

    @Override
    public void deleteTipoRegistro(Long id) {

        tipoRegistroRepository.deleteById(id);

    }

    @Override
    public TipoRegistro findTipoRegistro(Long id) {

        TipoRegistro tipoRegistro = tipoRegistroRepository.findById(id).orElse(null);
        return tipoRegistro;
    }

    @Override
    public void editTipoRegistro(TipoRegistro tipoRegistro) {

        this.saveTipoRegistro(tipoRegistro);

    }

}
