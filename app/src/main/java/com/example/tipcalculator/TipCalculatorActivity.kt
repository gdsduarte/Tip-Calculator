package com.example.tipcalculator

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.tipcalculator.databinding.ActivityTipCalculatorBinding
import java.text.NumberFormat

class TipCalculatorActivity : AppCompatActivity() {

    // Binding object instance with access to the views in the
    // activity_tip_calculator.xml layout
    private lateinit var binding: ActivityTipCalculatorBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Apply the theme
        applyTheme()

        // Inflate the layout XML file and return a binding object instance
        binding = ActivityTipCalculatorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup a click listener on the calculate button to calculate the tip
        binding.calculateButton.setOnClickListener { calculateTip() }
    }

    // Create the options menu
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.theme_menu, menu)
        return true
    }

    // Respond to menu item clicks by setting the checked state of the item clicked and
    // setting the app theme accordingly.
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.light_theme -> {
                setThemePreference("Light")
                true
            }
            R.id.dark_theme -> {
                setThemePreference("Dark")
                true
            }
            R.id.custom_theme -> {
                setThemePreference("Custom")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    // Set the app theme based on the user's menu choice and save the choice in
    // shared preferences so that it persists across app restarts.
    private fun setThemePreference(theme: String) {
        val sharedPreferences = getSharedPreferences("AppSettingsPrefs", MODE_PRIVATE)
        sharedPreferences.edit().putString("Theme", theme).apply()
        applyTheme()
        recreate()
    }

    // Apply the theme stored in shared preferences to the app. If no theme is stored,
    // set the app theme to the default, light theme.
    private fun applyTheme() {
        val sharedPreferences = getSharedPreferences("AppSettingsPrefs", MODE_PRIVATE)
        val themeChoice = sharedPreferences.getString("Theme", "Light")

        when (themeChoice) {
            "Light" -> setTheme(R.style.Theme_TipCalculator_Light)
            "Dark" -> setTheme(R.style.Theme_TipCalculator_Dark)
            "Custom" -> setTheme(R.style.Theme_TipCalculator_Custom)
        }
    }

    // Calculate the tip and display on the screen
    private fun calculateTip() {
        // Get the decimal value from the cost of service EditText field
        val stringInTextField = binding.costOfService.text.toString()
        // Convert the text to a decimal value
        val cost = stringInTextField.toDoubleOrNull()
        // If the cost is null or 0, then display 0 tip and exit this function early
        if (cost == null) {
            binding.tipResult.text = ""
            return
        }
        // Get the tip percentage based on which radio button is selected
        val tipPercentage = when (binding.tipOptions.checkedRadioButtonId) {
            R.id.option_twenty_percent -> 0.20
            R.id.option_eighteen_percent -> 0.18
            else -> 0.15
        }
        // Calculate the tip
        var tip = tipPercentage * cost
        if (binding.roundUpSwitch.isChecked) {
            tip = kotlin.math.ceil(tip)
        }
        // Display the formatted tip value on screen
        val formattedTip = NumberFormat.getCurrencyInstance().format(tip)
        binding.tipResult.text = getString(R.string.tip_amount, formattedTip)
    }
}

