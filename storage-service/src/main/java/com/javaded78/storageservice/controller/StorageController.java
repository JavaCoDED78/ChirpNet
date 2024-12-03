package com.javaded78.storageservice.controller;

import com.javaded78.storageservice.service.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/images")
public class StorageController {

    private final StorageService storageService;

    @PostMapping
    public ResponseEntity<String> uploadFile(@RequestParam MultipartFile image) {
        return ResponseEntity.ok(storageService.uploadImage(image));
    }

    @GetMapping
    public ResponseEntity<ByteArrayResource> downloadFile(@RequestParam String url) {
        byte[] data = storageService.downloadImage(url);
        ByteArrayResource resource = new ByteArrayResource(data);
        return ResponseEntity.ok()
                .contentLength(data.length)
                .header("Content-type", "application/octet-stream")
                .header("Content-disposition", "attachment; filename=\"" + url + "\"")
                .body(resource);
    }

    @DeleteMapping
    public ResponseEntity<Boolean> deleteFile(@RequestParam String url) {
        return ResponseEntity.ok(storageService.deleteImage(url));
    }
}
