package id.ac.ubaya.a160419022_ubayakuliner.model

data class Stand(
    val id:Int?,
    val nama_tempat:String?,
    val lokasi:String?,
    val deskripsi:String?,
    val url_img:String?,
    val like:Int?
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

data class User(
    val id:Int?,
    val username:String?,
    val alamat:String?,
    val url_image: String?
)

data class ApiResponse(
    val data: List<Stand>
)

data class ApiResponseGenre(
    val data: List<Genre>
)

data class ApiResponseComment(
    val data: List<Comment>
)

data class ApiResponseUser(
    val data: List<User>
)