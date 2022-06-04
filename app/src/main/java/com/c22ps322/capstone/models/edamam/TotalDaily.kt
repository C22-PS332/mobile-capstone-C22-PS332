package com.c22ps322.capstone.models.edamam

import com.squareup.moshi.Json

data class TotalDaily(
    @Json(name = "CA")
    val ca: NTRCode? = null,

    @Json(name = "SUGAR.added")
    val addedSugar: NTRCode? = null,

    @Json(name = "CHOCDF.net")
    val chocdfNet: NTRCode? = null,

    @Json(name = "CHOCDF")
    val chocdf: NTRCode? = null,

    @Json(name = "CHOLE")
    val chole: NTRCode? = null,

    @Json(name = "ENERC_KCAL")
    val enercKcal: NTRCode? = null,

    @Json(name = "FAMS")
    val fams: NTRCode? = null,

    @Json(name = "FAPU")
    val fapu: NTRCode? = null,

    @Json(name = "FASAT")
    val fasat: NTRCode? = null,

    @Json(name = "FATRN")
    val fatrn: NTRCode? = null,

    @Json(name = "FIBTG")
    val fibtg: NTRCode? = null,

    @Json(name = "FOLDFE")
    val foldfe: NTRCode? = null,

    @Json(name = "FOLFD")
    val folfd: NTRCode? = null,

    @Json(name = "FOLAC")
    val folac: NTRCode? = null,

    @Json(name = "FE")
    val fE: NTRCode? = null,

    @Json(name = "MG")
    val mG: NTRCode? = null,

    @Json(name = "NIA")
    val nia: NTRCode? = null,

    @Json(name = "P")
    val p: NTRCode? = null,

    @Json(name = "K")
    val k: NTRCode? = null,

    @Json(name = "PROCNT")
    val procnt: NTRCode? = null,

    @Json(name = "RIBF")
    val ribf: NTRCode? = null,

    @Json(name = "NA")
    val nA: NTRCode? = null,

    @Json(name = "Sugar.alcohol")
    val sugarAlcohol: NTRCode? = null,

    @Json(name = "THIA")
    val thia: NTRCode? = null,

    @Json(name = "FAT")
    val fat: NTRCode? = null,

    @Json(name = "VITA_RAE")
    val vitaRae: NTRCode? = null,

    @Json(name = "VITB12")
    val vitB12: NTRCode? = null,

    @Json(name = "VITB6A")
    val vitB6a: NTRCode? = null,

    @Json(name = "VITC")
    val vitc: NTRCode? = null,

    @Json(name = "VITD")
    val vitd: NTRCode? = null,

    @Json(name = "TOCPHA")
    val tocpha: NTRCode? = null,

    @Json(name = "VITK1")
    val vITK1: NTRCode? = null,

    @Json(name = "WATER")
    val water: NTRCode? = null,

    @Json(name = "ZN")
    val zn: NTRCode? = null,
) {
    fun toMap(): Map<String, NTRCode?>{
        return mapOf(
            "addedSugar" to addedSugar,
            "chocdfNet" to chocdfNet,
            "chocdf" to chocdf,
            "chole" to chole,
            "enercKcal" to enercKcal,
            "fams" to fams,
            "fapu" to fapu,
            "fasat" to fasat,
            "fatrn" to fatrn,
            "fibtg" to fibtg,
            "foldfe" to foldfe,
            "folfd" to folfd,
            "folac" to folac,
            "FE" to fE,
            "MG" to mG,
            "nia" to nia,
            "P" to p,
            "K" to k,
            "procnt" to procnt,
            "ribf" to ribf,
            "NA" to nA,
            "sugarAlcohol" to sugarAlcohol,
            "thia" to thia,
            "fat" to fat,
            "vitaRae" to vitaRae,
            "vitB12" to vitB12,
            "vitB6a" to vitB6a,
            "vitc" to vitc,
            "vitd" to vitd,
            "tocpha" to tocpha,
            "VIT K1" to vITK1,
            "Water" to water,
            "ZN" to zn
        )
    }
}