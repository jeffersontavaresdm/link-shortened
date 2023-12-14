package com.link_shortener.url.controller

import com.link_shortener.url.dto.ShortyRequest
import com.link_shortener.url.dto.ShortyResponse
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
  fun shorten(@RequestBody request: ShortyRequest): ResponseEntity<ShortyResponse> {
    return ResponseEntity
      .status(HttpStatus.OK)
      .body(ShortyResponse(service.shorten(request.url).block() ?: "error"))
  }

  @GetMapping("/{hash}")
  fun resolve(@PathVariable hash: String): ResponseEntity<HttpStatus> {
    return ResponseEntity
      .status(HttpStatus.MOVED_PERMANENTLY)
      .location(URI.create(service.resolve(hash).block() ?: "error"))
      .header(HttpHeaders.CONNECTION, "close")
      .build()
  }
}