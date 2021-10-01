import kotlin.math.ln
import kotlin.math.max

// creates a data class Process containing properties for the runtime, downtime, arrival time, start time and end time of the process
data class Process(val runTime:Double, val downTime:Double, val arrivalTime: Double = 0.0, val startTime: Double = 0.0, val endTime: Double = 0.0)

fun main() {

    // specified mean for the exponential random number
    val specifiedMean = 5.0

    // generates random number
    val randNum = kotlin.random.Random(System.nanoTime())

    // generates random number on an exponential distribution based on a specified mean
    val makeExpRandNum = { mean:Double -> { _:Int -> -mean * ln(randNum.nextDouble())}}

    val expRandNum: (Int) -> Double = makeExpRandNum( specifiedMean )

    // number of processes that will be generated
    val size = 12

    // number of random numbers that will be generated for the processes
    val data = List(size*2, expRandNum)

    // generates a list of processes with random numbers for first two properties
    val queue0 = mutableListOf<Process>()
    var index = 0
    for (i in 1..size) {
        val process = Process(data[index], data[index+1])
        queue0.add(process)
        index += 2}

    // generates new list of processes with time between arrival of processes added
    val queue1 = mutableListOf<Process>()
    var prevTime = 0.0
    for (process in queue0) {
        val currentTime = process.downTime + prevTime
        val updatedProcess = process.copy(arrivalTime = currentTime)
        queue1.add(updatedProcess)
        prevTime = currentTime

    }

    // generates new list of processes with start and end times of processes added
    val queue2 = mutableListOf<Process>()
    var lastEnd = 0.0
    for (process in queue1) {
        val currentStart = max(process.arrivalTime, lastEnd)
            val updatedProcess = process.copy(startTime = currentStart, endTime = currentStart + process.runTime)
            queue2.add(updatedProcess)
            lastEnd = currentStart + process.runTime
    }

    // generates a list of the time between the arrival and start time of a process
    val waitTimes = mutableListOf<Double>()
    for (process in queue2) {
        val wait = process.startTime - process.arrivalTime
        waitTimes.add(wait)
    }

    /*
    for( p in queue2 ) {
        println(p)
    }

    for( p in waitTimes ) {
        println(p)
    }
    */

    // finds the max, min, and average waiting times
    val max = waitTimes.maxOrNull()
    val min = waitTimes.minOrNull()
    val mean = waitTimes.average()

    // outputs the max, min, and average waiting times
    println("The minimum wait time is $min")
    println("The maximum wait time is $max")
    println("The average wait time is $mean")

}