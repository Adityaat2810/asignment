package com.example.cred_assignment.api

import retrofit2.http.GET

interface ApiService {
    @GET("test_mint")
    suspend fun getStackData(): StackResponse
}

// Data classes for API response
data class StackResponse(
    val loan_duration: LoanDuration,
    val loan_amount: LoanAmount,
    val bank_detail: BankDetail
)

data class LoanDuration(
    val title: String,
    val options: List<DurationOption>
)

data class DurationOption(
    val duration: String,
    val emi: String,
    val interest_rate: String,
    val is_recommended: Boolean
)

data class LoanAmount(
    val title: String,
    val max_amount: Int,
    val min_amount: Int,
    val default_amount: Int
)

data class BankDetail(
    val title: String,
    val banks: List<Bank>
)

data class Bank(
    val bank_name: String,
    val account_number: String,
    val ifsc: String,
    val is_preferred: Boolean
)