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
import cl.capstone.ms_registro_asistencia.model.TipoMarcaje;
import cl.capstone.ms_registro_asistencia.service.ITipoMarcajeService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class TipoMarcajeController {

    @Autowired
    ITipoMarcajeService tipoMarcajeService;

    @PostMapping("/tipoMarcaje/crear")
    public ResponseEntity<Response> saveTipoMarcaje(@RequestBody TipoMarcaje tipoMarcaje) {

        Response response = new Response();
        LocalDateTime currentDate = LocalDateTime.now();

        TipoMarcaje nuevoTipoMarcaje = tipoMarcajeService.saveTipoMarcaje(tipoMarcaje);

        if (nuevoTipoMarcaje != null) {
            // Si existe el trabajador, procedemos a eliminarlo
            response.setCodigoRetorno(0); // Código de éxito
            response.setGlosaRetorno("Nuevo tipo de marcaje creado.");
            response.setResultado(nuevoTipoMarcaje);
            response.setTimestamp(currentDate);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            // Si el trabajador no existe, retornamos un mensaje de error
            response.setCodigoRetorno(-1); // Código de error
            response.setGlosaRetorno("No se pudo crear el nuevo tipo de marcaje.");
            response.setResultado(null);
            response.setTimestamp(currentDate);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/tipoMarcaje/borrar/{id}")
    public ResponseEntity<Response> deleteTipoMarcaje(@PathVariable Long id) {

        Response response = new Response();
        LocalDateTime currentDate = LocalDateTime.now();

        TipoMarcaje tipoMarcaje = tipoMarcajeService.findTipoMarcaje(id);

        if (tipoMarcaje != null) {
            // Si existe el trabajador, procedemos a eliminarlo
            tipoMarcajeService.deleteTipoMarcaje(id);
            response.setCodigoRetorno(0); // Código de éxito
            response.setGlosaRetorno("Tipo marcaje eliminado.");
            response.setResultado(tipoMarcaje);
            response.setTimestamp(currentDate);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            // Si el trabajador no existe, retornamos un mensaje de error
            response.setCodigoRetorno(-1); // Código de error
            response.setGlosaRetorno("No se encontró el tipo marcaje.");
            response.setResultado(null);
            response.setTimestamp(currentDate);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/tipoMarcaje/editar/")
    public TipoMarcaje editTipoMarcaje(@RequestBody TipoMarcaje tipoMarcaje) {
        tipoMarcajeService.editTipoMarcaje(tipoMarcaje);

        return tipoMarcajeService.findTipoMarcaje(tipoMarcaje.getIdTipoRegistro());
    }

    @GetMapping("/tipoMarcaje/traer/{id}")
    public ResponseEntity<Response> getTipoMarcajeById(@PathVariable Long id) {

        Response response = new Response();
        LocalDateTime currentDate = LocalDateTime.now();

        TipoMarcaje tipoMarcaje = tipoMarcajeService.findTipoMarcaje(id);

        if (tipoMarcaje == null) {
            response.setCodigoRetorno(-1);
            response.setGlosaRetorno("Tipo marcaje no encotrado.");
            response.setResultado(tipoMarcaje);
            response.setTimestamp(currentDate);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } else {
            response.setCodigoRetorno(0);
            response.setGlosaRetorno("Tipo marcaje encontrado.");
            response.setResultado(tipoMarcaje);
            response.setTimestamp(currentDate);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    @GetMapping("/tipoMarcaje/traer")
    public ResponseEntity<Response> getTipoMarcaje() {

        List<TipoMarcaje> tipoMarcajes = tipoMarcajeService.getTipoMarcaje();
        Response response = new Response();
        LocalDateTime currentDate = LocalDateTime.now();

        if (tipoMarcajes == null || tipoMarcajes.isEmpty()) {
            response.setCodigoRetorno(-1);
            response.setGlosaRetorno("Tipo de marcajes no encotrados.");
            response.setResultado(tipoMarcajes);
            response.setTimestamp(currentDate);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } else {
            response.setCodigoRetorno(0);
            response.setGlosaRetorno("Tipo de marcajes encontrados.");
            response.setResultado(tipoMarcajes);
            response.setTimestamp(currentDate);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

    }

}
