package cl.capstone.ms_registro_asistencia.service;

import java.util.List;

import org.springframework.stereotype.Service;

import cl.capstone.ms_registro_asistencia.model.TipoMarcaje;

@Service
public interface ITipoMarcajeService {

    public List<TipoMarcaje> getTipoMarcaje();

    public TipoMarcaje saveTipoMarcaje(TipoMarcaje tipoMarcaje);

    public void deleteTipoMarcaje(Long id);

    public TipoMarcaje findTipoMarcaje(Long id);

    public void editTipoMarcaje(TipoMarcaje tipoMarcaje);

}
