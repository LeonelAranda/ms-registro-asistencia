package cl.capstone.ms_registro_asistencia.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import cl.capstone.ms_registro_asistencia.model.TipoMarcaje;
import cl.capstone.ms_registro_asistencia.repository.ITipoMarcajeRepository;

public class TipoMarcajeService implements ITipoMarcajeService {

    @Autowired
    ITipoMarcajeRepository tipoMarcajeRepository;

    @Override
    public List<TipoMarcaje> getTipoMarcaje() {
        List<TipoMarcaje> listaTipoMarcajes = tipoMarcajeRepository.findAll();
        return listaTipoMarcajes;

    }

    @Override
    public TipoMarcaje saveTipoMarcaje(TipoMarcaje tipoMarcaje) {
        tipoMarcajeRepository.save(tipoMarcaje);

        return tipoMarcaje;
    }

    @Override
    public void deleteTipoMarcaje(Long id) {
        tipoMarcajeRepository.deleteById(id);

    }

    @Override
    public TipoMarcaje findTipoMarcaje(Long id) {
        TipoMarcaje tipoMarcaje = tipoMarcajeRepository.findById(id).orElse(null);
        return tipoMarcaje;
    }

    @Override
    public void editTipoMarcaje(TipoMarcaje tipoMarcaje) {
        this.saveTipoMarcaje(tipoMarcaje);

    }

}
