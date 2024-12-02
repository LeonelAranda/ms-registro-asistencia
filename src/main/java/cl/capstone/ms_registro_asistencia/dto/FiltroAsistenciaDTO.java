package cl.capstone.ms_registro_asistencia.dto;

import java.util.Date;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class FiltroAsistenciaDTO {

    private Date fecha;
    private Long cargo;
    private String run;
    private Long faena;
}
