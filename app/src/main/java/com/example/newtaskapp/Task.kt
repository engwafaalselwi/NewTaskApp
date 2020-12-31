package com.example.newtaskapp

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*
@Entity
data class Task(@PrimaryKey val id : UUID = UUID.randomUUID(),
                     var title_task:String="",
                     var details:String="",
                     var time_end:Date=Date(),
                     var status_task:Int=1)