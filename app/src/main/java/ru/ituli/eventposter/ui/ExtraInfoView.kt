package ru.ituli.eventposter.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.Typeface
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import androidx.core.content.withStyledAttributes
import androidx.core.graphics.alpha
import ru.ituli.eventposter.R

class ExtraInfoView @JvmOverloads constructor(
    context         : Context,
    attrs           : AttributeSet? = null,
    defStyleAttr    : Int = 0,
    defStyleRes     : Int = 0
): View(context, attrs, defStyleAttr, defStyleRes) {

    private val defaultTextColor = Color.BLACK
    private val defaultBackgroundColor = Color.WHITE

    lateinit var text: String
    private var fontSize = 12f.sp(context)
    private var contour = fontSize / 2


    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = fontSize
        color = defaultTextColor
        typeface = Typeface.DEFAULT_BOLD
    }

    private val backPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = defaultBackgroundColor.apply { setAlpha(0.9F) }
    }

    private val textBounds = Rect()

    init {
        context.withStyledAttributes(attrs, R.styleable.ExtraInfoView) {
            text = getString(R.styleable.ExtraInfoView_text)?: ""
            fontSize = getDimension(R.styleable.ExtraInfoView_fontSize, 12f.sp(context))
            contour = getDimension(R.styleable.ExtraInfoView_contour, fontSize / 2)


            val textColor = getColor(R.styleable.ExtraInfoView_textColor, defaultTextColor)
            val backgroundColor = getColor(R.styleable.ExtraInfoView_backgroundColor, defaultBackgroundColor)

            textPaint.apply {
                textSize = fontSize
                color = textColor
            }
            backPaint.color = backgroundColor.apply { setAlpha(0.9F) }
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        textPaint.textSize = fontSize
        textPaint.getTextBounds(text, 0, text.length, textBounds)

        val rectWidth = textBounds.width() + 2*contour.toInt()
        val rectHeight = textBounds.height() + 2*contour.toInt()

        val measuredWidth = resolveSize(rectWidth + paddingLeft + paddingRight, widthMeasureSpec)
        val measuredHeight = resolveSize(rectHeight + paddingTop + paddingBottom, heightMeasureSpec)
        setMeasuredDimension(measuredWidth, measuredHeight)
    }

    override fun onDraw(canvas: Canvas) {
        val centerY = height / 2 - textBounds.exactCenterY()
        val centerX = width / 2 - textBounds.exactCenterX()

        val rectLeft = centerX - contour
        val rectRight = centerX + textBounds.right  + contour
        val rectBottom = height / 2 + textBounds.top / 2f - contour
        val rectTop = height / 2 - textBounds.top / 2f + contour

        canvas.drawRoundRect(rectLeft, rectTop, rectRight, rectBottom, 30f, 30f, backPaint)
        canvas.drawText(text, centerX, centerY, textPaint)
    }

    private fun Float.sp(context: Context) = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_SP,
        this,
        context.resources.displayMetrics
    )
}