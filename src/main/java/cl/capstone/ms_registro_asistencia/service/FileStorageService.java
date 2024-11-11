package cl.capstone.ms_registro_asistencia.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.web.multipart.MultipartFile;

public class FileStorageService {

    private final String storagePath = System.getProperty("user.dir") + "/uploads/worker-images";

    public void saveFile(MultipartFile file) throws IOException {
        // Crear directorio si no existe
        Path path = Paths.get(storagePath);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }

        // Guardar el archivo en la ruta
        Path filePath = path.resolve(file.getOriginalFilename());
        file.transferTo(filePath.toFile());
    }
}
