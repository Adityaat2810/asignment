package com.example.cred_assignment

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cred_assignment.adapters.StackViewAdapter
import com.example.cred_assignment.api.ResponseData
import com.example.cred_assignment.databinding.ActivityMainBinding
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val stackAdapter = StackViewAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        setupRecyclerView()
        loadStaticData()
    }

    private fun setupRecyclerView() {
        binding.stackRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = stackAdapter
        }
    }

    private fun loadStaticData() {
        val staticJsonData = """
            {
              "items": [{
                "open_state": {
                  "body": {
                    "title": "nikunj how much do you need?",
                    "subtitle": "move the dial and set any amount you need upto ₹487891 ",
                    "card": {
                      "header": "credit amount",
                      "description": "@1.04% monthly",
                      "max_range": 487891,
                      "min_range": 500
                    },
                    "footer": "stash is instant. money will be credited within seconds"
                  }
                },
                "closed_state": {
                  "body": {
                    "key1": "credit amount"
                  }
                },
                "cta_text": "Proceed to EMI selection"
              },
                {
                  "open_state": {
                    "body": {
                      "title": "how do you wish to repay?",
                      "subtitle": "choose one of our recommended plans or make your own ",
                      "items": [{
                        "emi": "₹4,247 /mo",
                        "duration": "12 months",
                        "title": "₹4,247 /mo for 12 months",
                        "subtitle": "See calculations"
                      },
                        {
                          "emi": "₹5,580 /mo",
                          "duration": "9 months",
                          "title": "₹5,580 /mo for 9 months",
                          "subtitle": "See calculations",
                          "tag": "recommended"
                        },
                        {
                          "emi": "₹8,270 /mo",
                          "duration": "6 months",
                          "title": "₹8,270 /mo for 6 months",
                          "subtitle": "See calculations"
                        }],
                      "footer": "create your own plan"
                    }
                  },
                  "closed_state": {
                    "body": {
                      "key1": "emi",
                      "key2": "duration"
                    }
                  },
                  "cta_text": "Select your bank account"
                },
                {
                  "open_state": {
                    "body": {
                      "title": "where should we send the money?",
                      "subtitle": "amount will be credited to the bank account. EMI will also be debited from this bank account",
                      "items": [{
                        "icon": "",
                        "title": "HDFC BANK",
                        "subtitle": 897458935
                      },
                        {
                          "icon": "",
                          "title": "SBI",
                          "subtitle": 897453435
                        },
                        {
                          "icon": "",
                          "title": "PNB",
                          "subtitle": 8974589334535
                        }],
                      "footer": "change account"
                    }
                  },
                  "closed_state": {
                    "body": {}
                  },
                  "cta_text": "Tap for 1-click KYC"
                }]
            }
        """.trimIndent()

        try {
            val response = Gson().fromJson(staticJsonData,ResponseData::class.java)

            // Convert to adapter items
            val adapterItems = response.items.map { item ->
                when {
                    item.open_state.body.card != null -> {
                        // First item - Amount selection
                        StackViewAdapter.StackItem.AmountItem(
                            title = item.open_state.body.title,
                            maxAmount = item.open_state.body.card.max_range,
                            minAmount = item.open_state.body.card.min_range,
                            subtitle = item.open_state.body.subtitle,
                            footer = item.open_state.body.footer,
                            ctaText = item.cta_text
                        )
                    }
                    item.open_state.body.items?.firstOrNull()?.emi != null -> {
                        // Second item - EMI selection
                        StackViewAdapter.StackItem.DurationItem(
                            title = item.open_state.body.title,
                            subtitle = item.open_state.body.subtitle,
                            options = item.open_state.body.items.map { emiItem ->
                                EmiOption(
                                    emi = emiItem.emi ?: "",
                                    duration = emiItem.duration ?: "",
                                    title = emiItem.title,
                                    subtitle = emiItem.subtitle.toString(),
                                    isRecommended = emiItem.tag == "recommended"
                                )
                            },
                            footer = item.open_state.body.footer,
                            ctaText = item.cta_text
                        )
                    }
                    else -> {
                        // Third item - Bank selection
                        StackViewAdapter.StackItem.BankItem(
                            title = item.open_state.body.title,
                            subtitle = item.open_state.body.subtitle,
                            banks = item.open_state.body.items?.map { bankItem ->
                                Bank(
                                    icon = bankItem.icon ?: "",
                                    name = bankItem.title,
                                    accountNumber = bankItem.subtitle.toString()
                                )
                            } ?: emptyList(),
                            footer = item.open_state.body.footer,
                            ctaText = item.cta_text
                        )
                    }
                }
            }

            stackAdapter.submitList(adapterItems)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

// Additional data classes for the adapter
data class EmiOption(
    val emi: String,
    val duration: String,
    val title: String,
    val subtitle: String,
    val isRecommended: Boolean
)

data class Bank(
    val icon: String,
    val name: String,
    val accountNumber: String
)