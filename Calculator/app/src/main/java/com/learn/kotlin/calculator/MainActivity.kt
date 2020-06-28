package com.learn.kotlin.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.ArithmeticException

class MainActivity : AppCompatActivity() {

    var lastNumeric : Boolean = false
    var lastDot : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    //Instead of implementing onClickListener we can define a function and work on that
    fun onDigit(view: View){
        // view we will get the input button
        displayInput.append((view as Button).text)
        lastNumeric = true
    }

    fun onClear(view: View){
        // view we will get the input button
        displayInput.text = ""
        lastNumeric = false
        lastDot = false
    }

    fun onDecimal(view: View) {
        if(lastNumeric && !lastDot){
            displayInput.append(".")
            lastNumeric = false
            lastDot = true
        }
    }

    fun onOperator(view: View){
        if(lastNumeric && !isOperatorAdded(displayInput.text.toString())){
            displayInput.append((view as Button).text)
            lastNumeric = false
            lastDot = false
        }

    }

    fun onEqual(view: View){
        if(lastNumeric){
            var inputValue = displayInput.text.toString()
            var prefix : String = ""
            try {
                //Handle -ve sign at the start
                if(inputValue.startsWith("-")){
                    prefix = "-"
                    inputValue = inputValue.substring(1)
                }

                //Calculations
                if(inputValue.contains("-")){
                    var splitInputValue = inputValue.split("-")
                    var firstInputValue = splitInputValue[0]
                    var secondInputValue = splitInputValue[1]

                    if(!prefix.isEmpty()){
                        firstInputValue = prefix + firstInputValue
                    }
                    displayInput.text = removeZeroAfterDot((firstInputValue.toDouble() - secondInputValue.toDouble()).toString())
                } else if(inputValue.contains("+")){
                    var splitInputValue = inputValue.split("+")
                    var firstInputValue = splitInputValue[0]
                    var secondInputValue = splitInputValue[1]
                    if(!prefix.isEmpty()){
                        firstInputValue = prefix + firstInputValue
                    }
                    displayInput.text = removeZeroAfterDot((firstInputValue.toDouble() + secondInputValue.toDouble()).toString())
                } else if(inputValue.contains("/")){
                    var splitInputValue = inputValue.split("/")
                    var firstInputValue = splitInputValue[0]
                    var secondInputValue = splitInputValue[1]
                    if(!prefix.isEmpty()){
                        firstInputValue = prefix + firstInputValue
                    }
                    displayInput.text = removeZeroAfterDot((firstInputValue.toDouble() / secondInputValue.toDouble()).toString())
                } else if(inputValue.contains("*")){
                    var splitInputValue = inputValue.split("*")
                    var firstInputValue = splitInputValue[0]
                    var secondInputValue = splitInputValue[1]
                    if(!prefix.isEmpty()){
                        firstInputValue = prefix + firstInputValue
                    }
                    displayInput.text = removeZeroAfterDot((firstInputValue.toDouble() * secondInputValue.toDouble()).toString())
                }
            } catch (e: ArithmeticException){
                e.printStackTrace()
            }
        }
    }

    private fun removeZeroAfterDot(result: String) : String {
        var value = result
        if(result.contains(".0"))
            value = result.substring(0, result.length - 2)
        return value
    }

    private fun isOperatorAdded(value: String): Boolean{
        return if(value.startsWith("-")) {
            false
        } else {
            value.contains("/") || value.contains("*") ||
                    value.contains("+") || value.contains("-")
        }
    }
}