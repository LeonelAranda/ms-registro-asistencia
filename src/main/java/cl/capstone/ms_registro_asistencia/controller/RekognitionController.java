package cl.capstone.ms_registro_asistencia.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import cl.capstone.ms_registro_asistencia.service.RekognitionService;
import cl.capstone.ms_registro_asistencia.service.WorkerService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*") // CORS para todos los endpoints en esta clase
@RequestMapping("/api/rekognition")
public class RekognitionController {

    @Autowired
    private RekognitionService rekognitionService;
    @Autowired
    private WorkerService workerService;

    @PostMapping("/register")
    public ResponseEntity<String> registerWorker(@RequestParam("file") MultipartFile file,
            @RequestParam("workerId") String workerId) {
        System.out.println("File received: " + (file != null ? file.getOriginalFilename() : "No file"));
        System.out.println("Worker ID received: " + workerId);
        return rekognitionService.registerWorker(workerId, file);
    }

    @PostMapping("/identify")
    public ResponseEntity<String> identifyWorker(@RequestParam("file") MultipartFile file) {
        return rekognitionService.identifyWorker(file);
    }

    @PostMapping("/register-local")
    public ResponseEntity<String> registerWorkerLocally(@RequestParam("file") MultipartFile file,
            @RequestParam("workerId") String workerId) {
        return workerService.registerWorkerLocally(workerId, file);
    }

    @DeleteMapping("/delete-worker-image/{workerId}")
    public ResponseEntity<String> deleteWorkerImage(@PathVariable String workerId) {
        return workerService.deleteWorkerImage(workerId);
    }

    @GetMapping("/workers/list-local-images")
    public ResponseEntity<List<String>> listImages() {
        List<String> images = workerService.listImages();
        return ResponseEntity.ok(images);
    }

    @DeleteMapping("/workers/delete-local/{imageName}.jpg")
    public ResponseEntity<String> deleteImage(@PathVariable String imageName) {
        try {
            workerService.deleteImage(imageName);
            return ResponseEntity.ok("Image deleted successfully: " + imageName);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

}
