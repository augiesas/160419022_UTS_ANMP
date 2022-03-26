package id.ac.ubaya.a160419022_ubayakuliner.model

data class Stand(
    val id:Int?,
    val nama_tempat:String?,
    val lokasi:String?,
    val deskripsi:String?,
    val url_img:String
)

data class Genre(
    val id:Int?,
    val name:String?
)

data class Comment(
    val id:Int?,
    val nama_pengguna:String?,
    val comment:String?,
    val id_stand:String?,
    val date:String?
)
