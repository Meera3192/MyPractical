package com.example.objectbox

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

/**
 * Created by Meera
 * Date : 9-08-2019.
 * Package_Name : com.example.objectbox
 * Class_Name : UserSearchRecord
 * Description : Create Entity class.
 */
@Entity
class UserSearchRecord {
    @Id
    var id: Long = 0
    var original_url: String? = null
    var image_url: String? = null
}