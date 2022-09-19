package com.caniskit.hw_payment_tracking_kotlin

import java.io.Serializable
enum class Period{
    PERIOD,YILLIK,AYLIK,HAFTALIK
}
class Payment( var Id: Int?=null,
               var PaymentTypeId: Int?=null,
               var Date: String?=null,
               var Amount : String?=null){


}
class PaymentType(
    var Id: Int?=null,
    var Title :  String?=null,
    var Period: Int?=null,
    var Day: Int?=null,
    //var Payments: ArrayList<Payment>?=null
)
