package tictactoe

val winSequences =
    listOf(
        listOf(0, 1, 2),
        listOf(3, 4, 5),
        listOf(6, 7, 8),
        listOf(0, 3, 6),
        listOf(1, 4, 7),
        listOf(2, 5, 8),
        listOf(0, 4, 8),
        listOf(2, 4, 6))

fun main() {
  val boardAsCharList = "         ".toMutableList()
  printBoard(boardAsCharList)
  var movements = 0
  do {
    movements++
    val playerSymbol = if (movements % 2 == 0 ) 'O' else 'X'
    processOneAction(boardAsCharList, playerSymbol)
    printBoard(boardAsCharList)
    val gameFinished = if (movements > 5) processActionsFromBoardMovements(boardAsCharList) else false
  } while (!gameFinished)
}

fun printBoard(boardAsCharList: MutableList<Char>) {
  println("---------")
  var rowIndex = 0
  while (rowIndex < boardAsCharList.size) {
    print("| ")
    repeat(3) {
      print("${boardAsCharList.get(rowIndex)} ")
      rowIndex++
    }
    print("|\n")
  }
  println("---------")
}

fun processActionsFromBoardMovements(boardAsCharList: List<Char>): Boolean {
  val gameFinished = when {
    treeInRow(boardAsCharList, 'X') -> {
      println("X wins")
      true
    }
    treeInRow(boardAsCharList, 'O') -> {
      println("O wins")
      true
    }
    emptyCells(boardAsCharList) -> {
      println("Draw")
      true
    }
    else -> false
  }
  return gameFinished
}

fun treeInRow(boardAsCharList: List<Char>, playerSymbol: Char): Boolean {
  var tryCount = 0
  do {
    val checkSequence = winSequences[tryCount]
    if (boardAsCharList[checkSequence[0]] == playerSymbol &&
        boardAsCharList[checkSequence[1]] == playerSymbol &&
        boardAsCharList[checkSequence[2]] == playerSymbol) {
      return true
    } else {
      tryCount++
    }
  } while (tryCount < winSequences.size)
  return false
}

fun emptyCells(boardAsCharList: List<Char>): Boolean {
  return !boardAsCharList.contains(' ')
}

fun processOneAction(boardAsCharList: MutableList<Char>, playerSymbol: Char) {
  var isValidInput: Boolean
  var inputCoordinate: String
  do {
    isValidInput = true
    var rowValue= ""
    var columnValue = ""
    inputCoordinate = readln()
    if (inputCoordinate.split(" ").size != 2) {
      println("You should enter numbers!")
      isValidInput = false
    } else {
      val coordinate = inputCoordinate.split(" ")
      rowValue = coordinate[0]
      columnValue = coordinate[1]
      if (rowValue.length > 1 || columnValue.length > 1) {
        println("You should enter numbers!")
        isValidInput = false
      }
    }
    isValidInput = isValidInput && when {
      !isOneNumber(rowValue) || !isOneNumber(columnValue) -> {
        println("You should enter numbers!")
        false
      }
      rowValue.toInt() > 3 || columnValue.toInt() > 3 -> {
        println("Coordinates should be from 1 to 3!")
        false
      }
      else -> true
    }

    if (isValidInput) {
      val rowIntValue = rowValue.toInt()
      val columnIntValue = columnValue.toInt()
      val position = (rowIntValue - 1 ) * 3 + (columnIntValue - 1 )
      if (boardAsCharList[position] != ' ') {
        println("This cell is occupied! Choose another one!")
        isValidInput = false
      } else {
        boardAsCharList[position] = playerSymbol
      }
    }
  } while(!isValidInput)
}

fun isOneNumber(value: String): Boolean {
  val isValid =
    when {
      value.length > 1 -> false
      !value[0].isDigit() -> false
      else -> true
    }
  return isValid
}
