package com.link_shortener.url.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
data class Url(

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  val id: Long? = null,
  val originalUrl: String? = null,
  val shortenedUrl: String? = null
) {

  @Override
  override fun toString(): String {
    return this::class.simpleName + "(id = $id )"
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as Url

    if (id != other.id) return false
    if (originalUrl != other.originalUrl) return false
    if (shortenedUrl != other.shortenedUrl) return false

    return true
  }

  override fun hashCode(): Int {
    var result = id?.hashCode() ?: 0
    result = 31 * result + (originalUrl?.hashCode() ?: 0)
    result = 31 * result + (shortenedUrl?.hashCode() ?: 0)
    return result
  }
}