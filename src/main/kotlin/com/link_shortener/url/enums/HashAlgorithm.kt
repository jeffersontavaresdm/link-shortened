package com.link_shortener.url.enums

enum class HashAlgorithm(val value: String) {
  SHA_256(value = "SHA-256"),
  MD5(value = "MD5"),
  SHA_512(value = "SHA-512"),
  CRC32(value = "CRC32"),
  CRC32C(value = "CRC32C")
}