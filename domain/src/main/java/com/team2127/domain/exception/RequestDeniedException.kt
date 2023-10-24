package com.team2127.domain.exception

// 권한이 없을때
class RequestDeniedException(val permission: String): Exception()
