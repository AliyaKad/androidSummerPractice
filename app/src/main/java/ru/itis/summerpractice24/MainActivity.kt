package ru.itis.summerpractice24

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import ru.itis.summerpractice24.R.id.editTextText
import ru.itis.summerpractice24.R.id.send_text_btn

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val text = findViewById<EditText>(R.id.editTextText)
        val button = findViewById<Button>(R.id.send_text_btn)
        var cnt = 0
        button.setOnClickListener {
            val inputText = text.text.toString()
            val inputNumber = inputText.toIntOrNull()
            if (inputNumber != null && inputNumber > 0) {
                cnt = inputNumber
                println("количество автомобилей $cnt")
                main(cnt)
            } else {
                println("Некорректный ввод: '$inputText'. Введите положительное число.")
            }
        }
    }
    fun main(number : Int) {
        val numberOfCars = number
        val cars = List(numberOfCars) { createCarRandomly() }

        cars.forEach { it.display() }

        var racingCars = cars
        var round = 1
        while (racingCars.size > 1) {
            println("раунд $round:")
            val winners = mutableListOf<Car>()
            val pairedCars = racingCars.shuffled()

            for (i in pairedCars.indices step 2) {
                if (i == pairedCars.lastIndex) {
                    winners.add(pairedCars[i])
                    println("${pairedCars[i].brand} отправляется на следующий раунд")
                } else {
                    val winner = race(pairedCars[i], pairedCars[i + 1])
                    winners.add(winner)
                    println("победитель раунда: ${winner.brand}")
                }
            }
            racingCars = winners
            round++
        }

        println("победитель: ${racingCars.first().brand}")
    }

}


open class Car(val brand: String, val model: String, val year: Int, val weight: String, val speed : Int) {
    open fun display() {
        println("Brand: $brand, Model: $model, Year: $year, Weinght: $weight, Speed: $speed")
    }
}

class Crossover(brand: String, model: String, year: Int, weight: String, speed : Int, val driveType: String, val enginePower: Int) : Car(brand, model, year, weight, speed) {
    override fun display() {
        println("Brand: $brand, Model: $model, Year: $year, Drive Type: $driveType, Engine Power: $enginePower")
    }
}

class Roadster(brand: String, model: String, year: Int, weight: String, speed : Int, val engineType: String, val enginePower: Int) : Car(brand, model, year, weight, speed) {
    override fun display() {
        println("Brand: $brand, Model: $model, Year: $year, Drive Type: $engineType, Engine Power: $enginePower")
    }
}

class Cabriolet(brand: String, model: String, year: Int, weight: String, speed : Int, val driveType: String, val engineVolume: Int) : Car(brand, model, year, weight, speed) {
    override fun display() {
        println("Brand: $brand, Model: $model, Year: $year, Drive Type: $driveType, Engine Power: $engineVolume")
    }
}

class Sedan(brand: String, model: String, year: Int, weight: String, speed : Int, val engineType: String, val engineVolume: Int) : Car(brand, model, year, weight, speed) {
    override fun display() {
        println("Brand: $brand, Model: $model, Year: $year, Drive Type: $engineType, Engine Power: $engineVolume")
    }
}

fun createCarRandomly(): Car {
    val brands = arrayOf("Toyota", "BMW", "Audi")
    val models = arrayOf("Corolla", "X5", "A4")
    val years = arrayListOf(2010, 2015, 2020)
    val weights = arrayOf("2tonne", "3tonne", "3,5tonne")
    val speeds = arrayOf(100, 110, 120, 130, 140, 150, 160, 170, 180, 190, 200)

    val brand = brands.random()
    val model = models.random()
    val year = years.random()
    val weight = weights.random()
    val speed = speeds.random()

    return Car(brand, model, year, weight, speed)
}
fun race(car1: Car, car2: Car): Car {
    println("Racing: ${car1.brand} vs ${car2.brand}")
    return if (car1.speed > car2.speed) car1 else car2
}