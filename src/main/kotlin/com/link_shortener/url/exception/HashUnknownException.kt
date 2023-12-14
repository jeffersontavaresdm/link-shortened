package com.link_shortener.url.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.NOT_FOUND)
class HashUnknownException(hash: String) : RuntimeException("$hash not found")