package cl.capstone.ms_registro_asistencia.controller;

import cl.capstone.ms_registro_asistencia.service.RekognitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/rekognition")
public class RekognitionController {

    @Autowired
    private RekognitionService rekognitionService;

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
}
