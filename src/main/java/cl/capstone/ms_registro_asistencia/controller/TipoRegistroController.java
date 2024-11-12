package cl.capstone.ms_registro_asistencia.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import cl.capstone.ms_registro_asistencia.model.Response;
import cl.capstone.ms_registro_asistencia.model.TipoRegistro;
import cl.capstone.ms_registro_asistencia.service.ITipoRegistroService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*") // CORS para todos los endpoints en esta clase
public class TipoRegistroController {

    @Autowired
    ITipoRegistroService tipoRegistroService;

    @PostMapping("/tiporegistro/crear")
    public ResponseEntity<Response> saveTipoRegistro(@RequestBody TipoRegistro tipoRegistro) {

        Response response = new Response();
        LocalDateTime currentDate = LocalDateTime.now();

        TipoRegistro nuevoTipoRegistro = tipoRegistroService.saveTipoRegistro(tipoRegistro);

        if (nuevoTipoRegistro != null) {
            // Si existe el trabajador, procedemos a eliminarlo
            response.setCodigoRetorno(0); // Código de éxito
            response.setGlosaRetorno("Nuevo tipo de registro creado.");
            response.setResultado(nuevoTipoRegistro);
            response.setTimestamp(currentDate);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            // Si el trabajador no existe, retornamos un mensaje de error
            response.setCodigoRetorno(-1); // Código de error
            response.setGlosaRetorno("No se pudo crear el nuevo tipo de registro.");
            response.setResultado(null);
            response.setTimestamp(currentDate);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/tiporegistro/borrar/{id}")
    public ResponseEntity<Response> deleteTipoRegistro(@PathVariable Long id) {

        Response response = new Response();
        LocalDateTime currentDate = LocalDateTime.now();

        TipoRegistro tipoRegistro = tipoRegistroService.findTipoRegistro(id);

        if (tipoRegistro != null) {
            // Si existe el trabajador, procedemos a eliminarlo
            tipoRegistroService.deleteTipoRegistro(id);
            response.setCodigoRetorno(0); // Código de éxito
            response.setGlosaRetorno("Tipo registro eliminado.");
            response.setResultado(tipoRegistro);
            response.setTimestamp(currentDate);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            // Si el trabajador no existe, retornamos un mensaje de error
            response.setCodigoRetorno(-1); // Código de error
            response.setGlosaRetorno("No se encontró el tipo registro.");
            response.setResultado(null);
            response.setTimestamp(currentDate);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/tiporegistro/editar/")
    public TipoRegistro editTipoRegistro(@RequestBody TipoRegistro tipoRegistro) {
        tipoRegistroService.editTipoRegistro(tipoRegistro);

        return tipoRegistroService.findTipoRegistro(tipoRegistro.getIdTipoRegistro());
    }

    @GetMapping("/tiporegistro/traer/{id}")
    public ResponseEntity<Response> getTipoRegistroById(@PathVariable Long id) {

        Response response = new Response();
        LocalDateTime currentDate = LocalDateTime.now();

        TipoRegistro tipoRegistro = tipoRegistroService.findTipoRegistro(id);

        if (tipoRegistro == null) {
            response.setCodigoRetorno(-1);
            response.setGlosaRetorno("Tipo registro no encotrado.");
            response.setResultado(tipoRegistro);
            response.setTimestamp(currentDate);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } else {
            response.setCodigoRetorno(0);
            response.setGlosaRetorno("Tipo registro encontrado.");
            response.setResultado(tipoRegistro);
            response.setTimestamp(currentDate);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    @GetMapping("/tiporegistro/traer")
    public ResponseEntity<Response> getTipoRegistros() {

        List<TipoRegistro> tipoRegistros = tipoRegistroService.getTipoRegistros();
        Response response = new Response();
        LocalDateTime currentDate = LocalDateTime.now();

        if (tipoRegistros == null || tipoRegistros.isEmpty()) {
            response.setCodigoRetorno(-1);
            response.setGlosaRetorno("Tipo de registros no encotrados.");
            response.setResultado(tipoRegistros);
            response.setTimestamp(currentDate);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } else {
            response.setCodigoRetorno(0);
            response.setGlosaRetorno("Tipo de registros encontrados.");
            response.setResultado(tipoRegistros);
            response.setTimestamp(currentDate);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

    }

}
