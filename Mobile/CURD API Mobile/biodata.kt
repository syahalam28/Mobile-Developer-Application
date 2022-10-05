package com.app.id.pemmob.model

class biodata {
    var id: Int = 0
    var nim: String = ""
    var nama: String = ""

    constructor(nim: String, nama: String) {
        this.nim = nim
        this.nama = nama
    }

    constructor(id: Int, nim: String, nama: String) {
        this.id = id
        this.nim = nim
        this.nama = nama
    }

}