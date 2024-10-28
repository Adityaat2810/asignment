package com.example.cred_assignment.api

data class ResponseData(
    val items: List<StackItem>
)

data class StackItem(
    val open_state: OpenState,
    val closed_state: ClosedState,
    val cta_text: String
)

data class OpenState(
    val body: Body
)

data class ClosedState(
    val body: ClosedStateBody
)

data class Body(
    val title: String,
    val subtitle: String,
    val card: Card? = null,
    val items: List<ItemData>? = null,
    val footer: String
)

data class ClosedStateBody(
    val key1: String? = null,
    val key2: String? = null
)

data class Card(
    val header: String,
    val description: String,
    val max_range: Int,
    val min_range: Int
)

data class ItemData(
    val icon: String? = null,
    val title: String,
    val subtitle: Any, // This can be either Int or String
    val emi: String? = null,
    val duration: String? = null,
    val tag: String? = null
)