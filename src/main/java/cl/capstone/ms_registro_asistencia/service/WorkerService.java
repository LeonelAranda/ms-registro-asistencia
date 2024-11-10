package cl.capstone.ms_registro_asistencia.service;

import java.io.IOException;
import java.nio.ByteBuffer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.services.rekognition.RekognitionClient;
import software.amazon.awssdk.services.rekognition.model.Image;
import software.amazon.awssdk.services.rekognition.model.IndexFacesRequest;
import software.amazon.awssdk.services.rekognition.model.RekognitionException;

@Service
public class WorkerService {

    private final RekognitionClient rekognitionClient;

    @Value("${aws.rekognition.collectionId}")
    private String collectionId;

    public WorkerService(RekognitionClient rekognitionClient) {
        this.rekognitionClient = rekognitionClient;
    }

    public void registerWorker(String workerId, MultipartFile file) {
        try {
            // Crear el objeto Image usando SdkBytes
            Image image = Image.builder()
                    .bytes(SdkBytes.fromByteBuffer(ByteBuffer.wrap(file.getBytes())))
                    .build();

            // Crear la solicitud de IndexFacesRequest usando el patrón builder
            IndexFacesRequest indexFacesRequest = IndexFacesRequest.builder()
                    .collectionId(collectionId)
                    .image(image)
                    .externalImageId(workerId)
                    .build();

            rekognitionClient.indexFaces(indexFacesRequest);
        } catch (RekognitionException e) {
            // Manejar error
        } catch (IOException e) {
            // Manejar error
        }
    }
}
