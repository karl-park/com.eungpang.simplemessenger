package com.eungpang.simplemessenger.data.mapper

interface DomainMapper<T, R> {
    fun mapTo(from: T): R
    fun mapFrom(from: R): T
}