package cl.capstone.ms_registro_asistencia.service;

import java.util.List;

import cl.capstone.ms_registro_asistencia.model.TipoRegistro;

public interface ITipoRegistroService {

    public List<TipoRegistro> getTipoRegistros();

    public void saveTipoRegistro(TipoRegistro tipoRegistro);

    public void deleteTipoRegistro(Long id);

    public TipoRegistro findTipoRegistro(Long id);

    public void editTipoRegistro(TipoRegistro tipoRegistro);

}
