package khusmanda.example.minesweeper

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.view.MotionEvent
import khusmanda.example.minesweeper.MineCells.COVERED
import khusmanda.example.minesweeper.MineCells.UNCOVERED
import khusmanda.example.minesweeper.MineCells.FLAGGED
import khusmanda.example.minesweeper.MineCells.gameOver



class CustomMine(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    val paint_Red = Paint(Paint.ANTI_ALIAS_FLAG)
    val paint_Black = Paint(Paint.ANTI_ALIAS_FLAG)
    val paint_Yellow = Paint(Paint.ANTI_ALIAS_FLAG)
    val paint_White = Paint(Paint.ANTI_ALIAS_FLAG)

    var paintUncoveredBoard = Color.GRAY
    var paintTextRed = Color.RED
    var paintTextBlack = Color.BLACK
    var paintBackYellow = Color.YELLOW
    var paintTextWhite = Color.WHITE

    private val num_of_rows = 10
    private var width = 0f
    private var cell = 0f



    //method to put together board and lines
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        // putting board and lines together
        drawBoard(canvas)
        drawLines(canvas)
        drawCell(canvas)


    }


    private fun drawBoard(canvas: Canvas) {
        val board = Color.BLACK
        paint.color = board
        // width and height of large board
        width = canvas.width.toFloat()


        //creating the board
        val rectangleshape = canvas.drawRect(0f, 0f, width, width, paint)


    }

    private fun drawLines(canvas: Canvas) {
        val lines = Color.WHITE
        paint.color = lines

        cell = width / num_of_rows;

        // making horizontal line
        for (i in 1 until num_of_rows) {
            canvas.drawLine(i * cell, 0F, i * cell, width, paint)
        }

        // making vertical line
        for (i in 1 until num_of_rows) {
            canvas.drawLine(0F, i * cell, width, i * cell, paint)
        }
    }

    private fun drawCell(canvas: Canvas) {
        for (i in 0 until 10) {
            for (j in 0 until 10) {
                drawCellSymbol(i, j, canvas)
            }
        }
    }

    private fun drawCellSymbol(row: Int, column: Int, canvas: Canvas) {
        var cellheight = cell
        var cellwidth = cell
        paint.color = paintUncoveredBoard
        paint_Red.color= paintTextRed
        paint_Black.color = paintTextBlack
        paint_Yellow.color = paintBackYellow
        paint_White.color = paintTextWhite

        //if FLAGGED, cell turn yellow
        if (MineCells.getCoverState(row, column) == FLAGGED) {
            //add flag symbol
            canvas.drawRect(
                row * cellwidth,
                column * cellheight,
                (row + 1) * cellwidth,
                (column + 1) * cellheight,
                paint_Yellow
            )
        }

        if (MineCells.getCoverState(row, column) == UNCOVERED) {
            canvas.drawRect(
                row * cellwidth,
                column * cellheight,
                (row + 1) * cellwidth,
                (column + 1) * cellheight,
                paint
            )
            if (MineCells.isMine(row, column)) {
                //add mine symbol

                canvas.drawRect(
                    row * cellwidth,
                    column * cellheight,
                    (row + 1) * cellwidth,
                    (column + 1) * cellheight,
                    paint_Red
                )
                paint_Black.setTextSize(40F);
                canvas.drawText(
                    "M",
                    row * cellwidth + (0.4 * cellwidth).toFloat(),
                    (column + 1) * cellheight - (0.4 * cellheight).toFloat(),
                    paint_Black
                )
            }
            else {
                paint_White.setTextSize(40f)
                var counter = MineCells.getMineCounter(row, column)
                if (counter != 0.toShort()) {
                    //add number of mines nearby
                    canvas.drawText(counter.toString(),
                        row * cellwidth + (.4 * cellwidth).toFloat(),
                        (column + 1) * cellheight - (.4 * cellheight).toFloat(),
                        paint_White)
                }
            }
        }



    }


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {

        val tX = event.x.toInt() / (width / MineCells.numColumns)
        val tY = event.y.toInt() / (width / MineCells.numRows)

            //if the game is not over, change the state of the field at (tX, tY) in the model
            if(gameOver ==false && (tX <= MineCells.numColumns) && (tY <= MineCells.numRows)) {
                    if(MineCells.getCoverState(tX.toInt(), tY.toInt()) == COVERED && MineCells.mode == FLAGGED) {
                        MineCells.changeSquareState(tX.toInt(), tY.toInt())
                    }
                    if (MineCells.getCoverState(tX.toInt(), tY.toInt()) == COVERED){
                        MineCells.changeSquareState(tX.toInt(), tY.toInt())
                    }

            }

        invalidate()

        return super.onTouchEvent(event)
    }


}



