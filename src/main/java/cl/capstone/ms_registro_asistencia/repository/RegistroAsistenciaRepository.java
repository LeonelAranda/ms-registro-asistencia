package cl.capstone.ms_registro_asistencia.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cl.capstone.ms_registro_asistencia.dto.FiltroAsistenciaDTO;
import cl.capstone.ms_registro_asistencia.dto.RegistroAsistenciaDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureQuery;

@Repository
public class RegistroAsistenciaRepository {

    @Autowired
    private EntityManager entityManager;

    public List<RegistroAsistenciaDTO> obtenerRegistrosHistoricos(FiltroAsistenciaDTO filtro) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("OBTENER_ASISTENCIA_HISTORICO");

        // Registrar parámetros
        query.registerStoredProcedureParameter("FECHA", Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("CARGO", Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("RUT", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("FAENA", Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_resultado", void.class, ParameterMode.REF_CURSOR);

        // Establecer valores para los parámetros dinámicos
        query.setParameter("FECHA", filtro.getFecha());
        query.setParameter("CARGO", filtro.getCargo());
        query.setParameter("RUT", filtro.getRun());
        query.setParameter("FAENA", filtro.getFaena());

        // Ejecutar el SP
        query.execute();

        // Mapear resultados a DTO
        List<Object[]> resultados = query.getResultList();
        return resultados.stream().map(obj -> new RegistroAsistenciaDTO(
                (String) obj[0],
                (String) obj[1], // runTrabajador
                (String) obj[2], // cargo
                obj[3] instanceof BigDecimal ? ((BigDecimal) obj[3]).longValue() : ((Long) obj[3]), // idFaena
                (String) obj[4], // horaEntrada
                (String) obj[5],
                (String) obj[6] // horaSalida
        )).collect(Collectors.toList());
    }

    public List<RegistroAsistenciaDTO> obtenerRegistrosDiarios(FiltroAsistenciaDTO filtro) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("OBTENER_ASISTENCIA_DIA");

        // Registrar parámetros
        query.registerStoredProcedureParameter("CARGO", Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("RUT", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_resultado", void.class, ParameterMode.REF_CURSOR);

        // Establecer valores para los parámetros dinámicos
        query.setParameter("CARGO", filtro.getCargo());
        query.setParameter("RUT", filtro.getRun());

        // Ejecutar el SP
        query.execute();

        // Mapear resultados a DTO
        List<Object[]> resultados = query.getResultList();
        return resultados.stream().map(obj -> new RegistroAsistenciaDTO(
                (String) obj[0],
                (String) obj[1], // runTrabajador
                (String) obj[2], // cargo
                obj[03] instanceof BigDecimal ? ((BigDecimal) obj[3]).longValue() : ((Long) obj[3]), // idFaena
                (String) obj[4], // horaEntrada
                (String) obj[5],
                (String) obj[6] // horaSalida
        )).collect(Collectors.toList());
    }

}
