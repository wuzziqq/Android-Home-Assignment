package com.example.androidhomeassignment

class Customer {
    var id: Int = 0;
    var customerName: String = "";
    var country: String = "";
    var total: Int = 0;

    constructor(_customerName:String,_country:String,_total: Int) {
        customerName = _customerName
        country = _country
        total = _total
    }

    constructor()
    {

    }
}