package com.freezyapp.data.model

import java.util.*

class User {

    var id : Int = 1
    var uuid : UUID = UUID.randomUUID()

    constructor(){
    }

    constructor(uuid: UUID){
        this.uuid = uuid
    }
}