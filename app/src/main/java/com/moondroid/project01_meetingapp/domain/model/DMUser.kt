package com.moondroid.project01_meetingapp.domain.model

import com.google.gson.annotations.SerializedName
import com.moondroid.project01_meetingapp.utils.RequestParam
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey


/**
 * User Model Class for Realm
 **/
class DMUser(): RealmObject{
    @PrimaryKey
    @SerializedName(RequestParam.ID)
    var id : String = ""
    @SerializedName(RequestParam.NAME)
    var name: String= ""
    @SerializedName(RequestParam.BIRTH)
    var birth: String = ""
    @SerializedName(RequestParam.GENDER)
    var gender: String = ""
    @SerializedName(RequestParam.LOCATION)
    var location: String = ""
    @SerializedName(RequestParam.INTEREST)
    var interest: String = ""
    @SerializedName(RequestParam.THUMB)
    var thumb: String = ""
    @SerializedName(RequestParam.MESSAGE)
    var message: String = ""

    constructor(user: DMUser): this() {
        id = user.id
        settings(user)
    }

    fun settings(user: DMUser) {
        name = user.name
        birth = user.birth
        gender = user.gender
        location = user.location
        interest = user.interest
        thumb = user.thumb
        message = user.message
    }

    fun isEmpty():Boolean {
        return id.isEmpty() || name.isEmpty() || birth.isEmpty() || gender.isEmpty() || location.isEmpty() || interest.isEmpty() || thumb.isEmpty() || message.isEmpty()
    }

    override fun toString(): String {
        return "DMUser(id='$id', name='$name', birth='$birth', gender='$gender', location='$location', interest='$interest', thumb='$thumb', message='$message')"
    }
}