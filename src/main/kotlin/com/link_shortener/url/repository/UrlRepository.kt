package com.link_shortener.url.repository

import com.link_shortener.url.entity.Url

interface UrlRepository : BaseRepository<Url> {

  fun findByShortenedUrl(shortenedUrl: String): Url
}