package cl.capstone.ms_registro_asistencia.service;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.rekognition.RekognitionClient;
import software.amazon.awssdk.services.rekognition.model.FaceMatch;
import software.amazon.awssdk.services.rekognition.model.Image;
import software.amazon.awssdk.services.rekognition.model.IndexFacesRequest;
import software.amazon.awssdk.services.rekognition.model.RekognitionException;
import software.amazon.awssdk.services.rekognition.model.SearchFacesByImageRequest;
import software.amazon.awssdk.services.rekognition.model.SearchFacesByImageResponse;
import software.amazon.awssdk.services.rekognition.model.DescribeCollectionRequest;
import software.amazon.awssdk.services.rekognition.model.CreateCollectionRequest;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Service
public class RekognitionService {

    private final RekognitionClient rekognitionClient;
    private final S3Client s3Client;

    /*
     * @Value("${aws.rekognition.collectionId}")
     * private String collectionId;
     */
    private final String collectionId = "trabajadoresFaena"; // Asignación en duro

    @Value("${aws.s3.bucketName}")
    private String bucketName;

    public RekognitionService(RekognitionClient rekognitionClient, S3Client s3Client) {
        this.rekognitionClient = rekognitionClient;
        this.s3Client = s3Client;
        createCollectionIfNotExists(); // Verificar y crear la colección si no existe
    }

    private void createCollectionIfNotExists() {
        try {
            // Verificar si la colección existe
            rekognitionClient.describeCollection(DescribeCollectionRequest.builder()
                    .collectionId(collectionId)
                    .build());
        } catch (RekognitionException e) {
            // Si la colección no existe, crearla
            if ("ResourceNotFoundException".equals(e.awsErrorDetails().errorCode())) {
                rekognitionClient.createCollection(CreateCollectionRequest.builder()
                        .collectionId(collectionId)
                        .build());
                System.out.println("Collection created: " + collectionId);
            } else {
                throw e; // Re-throw the exception if it's not a "not found" error
            }
        }
    }

    public ResponseEntity<String> registerWorker(String workerId, MultipartFile file) {
        try {
            // Cargar la imagen en S3
            String s3ObjectKey = "workers/" + workerId;
            s3Client.putObject(PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(s3ObjectKey)
                    .build(), RequestBody.fromBytes(file.getBytes()));

            // Agregar la imagen a Rekognition
            IndexFacesRequest indexFacesRequest = IndexFacesRequest.builder()
                    .collectionId(collectionId)
                    .image(Image.builder().bytes(SdkBytes.fromByteBuffer(ByteBuffer.wrap(file.getBytes()))).build()) // Convertir
                                                                                                                     // ByteBuffer
                                                                                                                     // a
                                                                                                                     // SdkBytes
                    .externalImageId(workerId)
                    .build();

            rekognitionClient.indexFaces(indexFacesRequest);
            return ResponseEntity.ok("Worker registered successfully with ID: " + workerId);

        } catch (IOException | RekognitionException e) {
            return ResponseEntity.status(500).body("Failed to register worker: " + e.getMessage());
        }
    }

    public ResponseEntity<String> identifyWorker(MultipartFile file) {
        try {
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body("File cannot be empty");
            }

            // Crear la solicitud de búsqueda de coincidencia de rostros
            SearchFacesByImageRequest searchFacesByImageRequest = SearchFacesByImageRequest.builder()
                    .collectionId(collectionId)
                    .image(Image.builder().bytes(SdkBytes.fromByteBuffer(ByteBuffer.wrap(file.getBytes()))).build())
                    .build();

            SearchFacesByImageResponse searchFacesByImageResponse = rekognitionClient
                    .searchFacesByImage(searchFacesByImageRequest);
            List<FaceMatch> faceMatches = searchFacesByImageResponse.faceMatches();

            if (!faceMatches.isEmpty()) {
                String workerId = faceMatches.get(0).face().externalImageId();
                return ResponseEntity.ok("Worker identified with ID: " + workerId);
            } else {
                return ResponseEntity.status(404).body("Worker not identified.");
            }

        } catch (IOException e) {
            return ResponseEntity.status(500).body("Failed to identify worker due to file error: " + e.getMessage());
        } catch (RekognitionException e) {
            return ResponseEntity.status(500)
                    .body("Failed to identify worker in Rekognition: " + e.awsErrorDetails().errorMessage());
        }
    }

}
