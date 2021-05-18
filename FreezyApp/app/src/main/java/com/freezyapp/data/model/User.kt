package com.freezyapp.data.model

import java.util.*

class User {

    var id : Int = 0
    var uuid : UUID = UUID.randomUUID()

    constructor(uuid: UUID){
        this.uuid = uuid
    }
}