package com.example.weaterapptesttask.core

/**
 * Created by HP on 23.03.2023.
 **/
interface Mapper<T,R> {

    fun map(data: T): R
}