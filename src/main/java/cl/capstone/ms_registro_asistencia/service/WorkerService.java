package cl.capstone.ms_registro_asistencia.service;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.rekognition.RekognitionClient;
import software.amazon.awssdk.services.rekognition.model.Image;
import software.amazon.awssdk.services.rekognition.model.IndexFacesRequest;
import software.amazon.awssdk.services.rekognition.model.RekognitionException;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Service
public class WorkerService {

    private final RekognitionClient rekognitionClient;
    private final S3Client s3Client;

    @Value("${aws.rekognition.collectionId}")
    private String collectionId;

    @Value("${aws.s3.bucketName}")
    private String bucketName;

    @Value("${local.storage.path}")
    private String localStoragePath;

    public WorkerService(RekognitionClient rekognitionClient, S3Client s3Client) {
        this.rekognitionClient = rekognitionClient;
        this.s3Client = s3Client;
    }

    public ResponseEntity<String> registerWorker(String workerId, MultipartFile file) {
        try {
            if (file.isEmpty() || workerId == null || workerId.isEmpty()) {
                return ResponseEntity.badRequest().body("File or Worker ID cannot be empty");
            }

            // Cargar la imagen en S3
            String s3ObjectKey = "workers/" + workerId;
            s3Client.putObject(PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(s3ObjectKey)
                    .build(), RequestBody.fromBytes(file.getBytes()));

            // Agregar la imagen a Rekognition
            IndexFacesRequest indexFacesRequest = IndexFacesRequest.builder()
                    .collectionId(collectionId)
                    .image(Image.builder().bytes(SdkBytes.fromByteBuffer(ByteBuffer.wrap(file.getBytes()))).build())
                    .externalImageId(workerId)
                    .build();

            rekognitionClient.indexFaces(indexFacesRequest);
            return ResponseEntity.ok("Worker registered successfully with ID: " + workerId);

        } catch (IOException e) {
            return ResponseEntity.status(500).body("Failed to register worker due to file error: " + e.getMessage());
        } catch (RekognitionException e) {
            return ResponseEntity.status(500)
                    .body("Failed to register worker in Rekognition: " + e.awsErrorDetails().errorMessage());
        }
    }

    public ResponseEntity<String> registerWorkerLocally(String workerId, MultipartFile file) {
        try {
            // Obtener el directorio de trabajo de la aplicación (la raíz del proyecto)
            String baseDir = System.getProperty("user.dir");

            // Crear la ruta completa a partir del directorio base y la ruta configurada
            Path path = Paths.get(baseDir, localStoragePath);

            // Crear el directorio si no existe
            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }

            Path filePath = path.resolve(workerId + ".jpg"); // Usamos el workerId como nombre del archivo
            file.transferTo(filePath.toFile());

            System.out.println("Saving file to: " + filePath.toAbsolutePath().toString());

            return ResponseEntity.ok("Worker registered successfully with ID: " + workerId);

        } catch (IOException e) {
            return ResponseEntity.status(500).body("Failed to register worker due to file error: " + e.getMessage());
        }
    }

}
