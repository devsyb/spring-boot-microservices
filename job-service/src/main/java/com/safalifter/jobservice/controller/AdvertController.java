package com.safalifter.jobservice.controller;

import com.safalifter.jobservice.dto.AdvertDto;
import com.safalifter.jobservice.enums.Advertiser;
import com.safalifter.jobservice.request.advert.AdvertCreateRequest;
import com.safalifter.jobservice.request.advert.AdvertUpdateRequest;
import com.safalifter.jobservice.service.AdvertService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/advert")
@RequiredArgsConstructor
public class AdvertController {
    private final AdvertService advertService;
    private final ModelMapper modelMapper;

    @PostMapping("/create")
    public ResponseEntity<AdvertDto> createAdvert(@RequestBody AdvertCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(modelMapper.map(advertService.createAdvert(request), AdvertDto.class));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<AdvertDto>> getAll() {
        return ResponseEntity.ok(advertService.getAll().stream()
                .map(advert -> modelMapper.map(advert, AdvertDto.class)).toList());
    }

    @GetMapping("/getAdvertById/{id}")
    public ResponseEntity<AdvertDto> getAdvertById(@PathVariable String id) {
        return ResponseEntity.ok(modelMapper.map(advertService.getAdvertById(id), AdvertDto.class));
    }

    @GetMapping("/getAdvertsByUserId/{id}")
    public ResponseEntity<List<AdvertDto>> getAdvertsByUserId(@PathVariable String id,
                                                              @RequestParam Advertiser type) {
        return ResponseEntity.ok(advertService.getAdvertsByUserId(id, type).stream()
                .map(advert -> modelMapper.map(advert, AdvertDto.class)).toList());
    }

    @PutMapping("/update")
    public ResponseEntity<AdvertDto> updateAdvertById(@RequestBody AdvertUpdateRequest request) {
        return ResponseEntity.ok(modelMapper.map(advertService.updateAdvertById(request), AdvertDto.class));
    }

    @DeleteMapping("/deleteAdvertById/{id}")
    public ResponseEntity<Void> deleteAdvertById(@PathVariable String id) {
        advertService.deleteAdvertById(id);
        return ResponseEntity.ok().build();
    }
}
