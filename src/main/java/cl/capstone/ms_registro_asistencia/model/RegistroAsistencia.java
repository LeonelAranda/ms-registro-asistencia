package cl.capstone.ms_registro_asistencia.model;

import java.time.LocalTime;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Entity
@Table(name = "REGISTRO_ASISTENCIA")
public class RegistroAsistencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_REGISTRO", nullable = false)
    private Long idRegistro;

    @Column(name = "RUN_TRABAJADOR", nullable = false)
    private String runTrabajador;

    @Column(name = "FECHA", nullable = false)
    private Date fecha;

    @Column(name = "HORA", nullable = false)
    private LocalTime hora;

    @Column(name = "ID_FAENA", nullable = false)
    private Long idFaena;

    @ManyToOne
    @JoinColumn(name = "TIPO_REGISTRO", referencedColumnName = "ID_TIPO_REGISTRO")
    private TipoRegistro tipoRegistroJoin;

    @ManyToOne
    @JoinColumn(name = "TIPO_MARCAJE", referencedColumnName = "ID_TIPO_MARCAJE")
    private TipoMarcaje tipoMarcaje;

}
