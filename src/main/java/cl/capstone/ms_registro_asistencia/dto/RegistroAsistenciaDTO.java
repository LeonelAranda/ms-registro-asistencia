package cl.capstone.ms_registro_asistencia.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
public class RegistroAsistenciaDTO {

    private String nombreCompleto;
    private String fecha;
    private String cargo;
    private Long idFaena;
    private String runTrabajador;
    private String horaEntrada;
    private String horaSalida;

}
