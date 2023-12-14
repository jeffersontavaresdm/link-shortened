package com.link_shortener.url.service

import com.link_shortener.url.enums.HashAlgorithm
import com.link_shortener.url.exception.HashUnknownException
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import reactor.core.publisher.SynchronousSink
import java.math.BigInteger

import java.security.MessageDigest

@Service

class ShortyService(private val redisTemplate: ReactiveRedisTemplate<String, String>) {

  private val digest = MessageDigest.getInstance(HashAlgorithm.SHA_256.value)

  fun shorten(url: String): Mono<String> {
    val existingHash = redisTemplate
      .opsForValue()
      .get(url)
      .blockOptional()
      .orElse(null)

    if (existingHash != null) {
      return Mono.just(existingHash)
    }

    val bytes = digest.digest(url.toByteArray())
    val hash = String.format("%32x", BigInteger(1, bytes)).take(6)

    val result = redisTemplate.opsForValue().set(hash, url)

    return result.handle { it: Boolean, sink: SynchronousSink<String> ->
      if (it) {
        sink.next(hash)
      } else {
        sink.error(HashUnknownException("Failed to store URL"))
      }
    }
  }

  fun resolve(hash: String): Mono<String> {
    return redisTemplate
      .opsForValue()
      .get(hash)
      .switchIfEmpty(Mono.error(HashUnknownException("URL not found")))
  }
}