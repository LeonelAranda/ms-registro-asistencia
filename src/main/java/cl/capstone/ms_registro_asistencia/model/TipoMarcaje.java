package cl.capstone.ms_registro_asistencia.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Entity
@Table(name = "TIPO_MARCAJE")
public class TipoMarcaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_TIPO_MARCAJE", nullable = false)
    private Long idTipoRegistro;

    @Column(name = "TIPO_MARCAJE", nullable = false)
    private String tipoRegistro;
}
