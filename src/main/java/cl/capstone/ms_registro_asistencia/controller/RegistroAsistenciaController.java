package cl.capstone.ms_registro_asistencia.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import cl.capstone.ms_registro_asistencia.model.RegistroAsistencia;
import cl.capstone.ms_registro_asistencia.model.Response;
import cl.capstone.ms_registro_asistencia.service.IRegistroAsistenciaService;

@RestController
public class RegistroAsistenciaController {

    @Autowired
    private IRegistroAsistenciaService registroAsistenciaService;

    @PostMapping("/registroasistencia/crear")
    public String saveRegistroAsistencia(@RequestBody RegistroAsistencia registroAsistencia) {

        // Response response = new Response();
        // LocalDateTime currentDate = LocalDateTime.now();

        registroAsistenciaService.saveRegistroAsistencias(registroAsistencia);
        return "Registro creada";
    }

    @DeleteMapping("/registroasistencia/borrar/{id}")
    public ResponseEntity<Response> deleteRegistroAsistencia(@PathVariable Long id) {

        Response response = new Response();
        LocalDateTime currentDate = LocalDateTime.now();

        RegistroAsistencia registroAsistencia = registroAsistenciaService.findRegistroAsistencia(id);

        if (registroAsistencia != null) {
            // Si existe el trabajador, procedemos a eliminarlo
            registroAsistenciaService.deleteRegistroAsistencia(id);
            response.setCodigoRetorno(0); // Código de éxito
            response.setGlosaRetorno("Registro eliminado.");
            response.setResultado(registroAsistencia);
            response.setTimestamp(currentDate);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            // Si el trabajador no existe, retornamos un mensaje de error
            response.setCodigoRetorno(-1); // Código de error
            response.setGlosaRetorno("No se encontró registro.");
            response.setResultado(null);
            response.setTimestamp(currentDate);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/registroasistencia/editar/")
    public RegistroAsistencia editRegistroAsistencia(@RequestBody RegistroAsistencia registroAsistencia) {
        registroAsistenciaService.editRegistroAsistencia(registroAsistencia);

        return registroAsistenciaService.findRegistroAsistencia(registroAsistencia.getIdRegistro());
    }

    @GetMapping("/registroasistencia/traer/{id}")
    public ResponseEntity<Response> getRegistroAsistenciaById(@PathVariable Long id) {

        Response response = new Response();
        LocalDateTime currentDate = LocalDateTime.now();

        RegistroAsistencia registroAsistencia = registroAsistenciaService.findRegistroAsistencia(id);

        if (registroAsistencia == null) {
            response.setCodigoRetorno(-1);
            response.setGlosaRetorno("Registro no encotrado.");
            response.setResultado(registroAsistencia);
            response.setTimestamp(currentDate);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } else {
            response.setCodigoRetorno(0);
            response.setGlosaRetorno("Registro encontrado.");
            response.setResultado(registroAsistencia);
            response.setTimestamp(currentDate);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    @GetMapping("/registroasistencia/traer")
    public ResponseEntity<Response> getRegistroAsistencia() {

        List<RegistroAsistencia> registroAsistencias = registroAsistenciaService.getRegistroAsistencias();
        Response response = new Response();
        LocalDateTime currentDate = LocalDateTime.now();

        if (registroAsistencias == null || registroAsistencias.isEmpty()) {
            response.setCodigoRetorno(-1);
            response.setGlosaRetorno("Registros no encotrados.");
            response.setResultado(registroAsistencias);
            response.setTimestamp(currentDate);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } else {
            response.setCodigoRetorno(0);
            response.setGlosaRetorno("Registros encontrados.");
            response.setResultado(registroAsistencias);
            response.setTimestamp(currentDate);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

    }

}
