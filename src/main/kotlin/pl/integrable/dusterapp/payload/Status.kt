package pl.integrable.dusterapp.payload

import java.io.Serializable

class Status<T> : Serializable {
    public var message: T? = null
    public var errorCode = 0

    constructor() {}
    constructor(message: T) {
        this.message = message
    }

    constructor(message: T, errorCode: Int) {
        this.message = message
        this.errorCode = errorCode
    }

}