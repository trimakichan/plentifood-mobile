package com.example.plentifood.ui.utils

import com.example.plentifood.data.models.response.Site

fun cleanAddress(site: Site): List<String> =
    listOfNotNull(
        site.addressLine1,
        site.addressLine2,
        site.city,
        site.state,
        site.postalCode
    )
