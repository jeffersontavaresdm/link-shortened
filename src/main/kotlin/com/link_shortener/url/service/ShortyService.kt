package com.link_shortener.url.service

import com.link_shortener.url.enums.HashAlgorithm
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.stereotype.Service
import java.math.BigInteger
import java.security.MessageDigest

@Service
class ShortyService(private val redisTemplate: ReactiveRedisTemplate<String, String>) {

  private val digest = MessageDigest.getInstance(HashAlgorithm.SHA_256.value)

  fun shorten(url: String): String {
    val existingHash = redisTemplate.opsForValue().get(url).block()
    if (existingHash != null) {
      return existingHash
    }

    val bytes = digest.digest(url.toByteArray())
    val hash = String.format("%32x", BigInteger(1, bytes)).take(6)

    redisTemplate.opsForValue().set(hash, url)

    return hash
  }

  fun resolve(hash: String): String {
    return redisTemplate
      .opsForValue()
      .get(hash)
      .toString()
  }
}