package com.link_shortener.url.controller

import com.link_shortener.url.entity.dto.ShortyRequest
import com.link_shortener.url.entity.dto.ShortyResponse
import com.link_shortener.url.service.ShortyService
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI

@RestController
@RequestMapping("/urls")
class UrlController(private val service: ShortyService) {

  @PostMapping
  fun shorten(@RequestBody request: ShortyRequest): ShortyResponse {
    return ShortyResponse(service.shorten(request.url))
  }

  @GetMapping("/{hash}")
  fun resolve(@PathVariable hash: String): ResponseEntity<HttpStatus> {
    return ResponseEntity
      .status(HttpStatus.MOVED_PERMANENTLY)
      .location(URI.create(service.resolve(hash)))
      .header(HttpHeaders.CONNECTION, "close")
      .build()
  }
}