package com.example.mfcs_project

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.QrCode2
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.abs

private val PayBgDark = Color(0xFF121212)
private val PayCard = Color(0xFF1B1B1B)
private val PayRed = Color(0xFFE50914)
private val PayDeepRed = Color(0xFF4A070B)
private val PayGold = Color(0xFFF59E0B)
private val PayGreen = Color(0xFF10B981)
private val PayBorder = Color(0xFF2B2B2B)
private val PayTextWhite = Color(0xFFFFFFFF)
private val PayTextGray = Color(0xFFA6A6A6)

enum class PaymentMethodOption(
    val title: String,
    val subtitle: String,
    val icon: ImageVector
) {
    QPAY("QPAY", "Бүх банкны апп болон QR-аар шууд төлөх", Icons.Default.QrCode2),
    CARD("BANK CARD", "Visa / Mastercard / Дотоодын карт", Icons.Default.CreditCard)
}

data class CheckoutPlan(
    val code: String,
    val title: String,
    val description: String,
    val priceFormatted: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MfcsPaymentBottomSheet(
    plan: CheckoutPlan,
    onDismiss: () -> Unit,
    onPaymentSuccess: (CheckoutPlan, PaymentMethodOption) -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var selectedMethod by remember { mutableStateOf(PaymentMethodOption.QPAY) }
    var showQrCodeBox by remember { mutableStateOf(true) }
    var isCompleted by remember { mutableStateOf(false) }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = PayBgDark,
        contentColor = PayTextWhite,
        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(horizontal = 18.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "MFCS CHECKOUT",
                        color = PayRed,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Black,
                        letterSpacing = 0.8.sp
                    )
                    Text(
                        text = if (isCompleted) "ТӨЛБӨР БАТАЛГААЖЛАА" else "ТӨЛБӨР ТӨЛӨХ",
                        color = PayTextWhite,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Black
                    )
                }

                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Хаах",
                    tint = PayTextGray,
                    modifier = Modifier
                        .size(22.dp)
                        .clickable { onDismiss() }
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .background(Brush.horizontalGradient(listOf(PayDeepRed, PayCard)))
                    .border(1.dp, PayRed.copy(alpha = 0.7f), RoundedCornerShape(14.dp))
                    .padding(14.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = plan.title,
                        color = PayTextWhite,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Black
                    )
                    Text(
                        text = plan.priceFormatted,
                        color = PayGold,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Black
                    )
                }
                Text(
                    text = plan.description,
                    color = PayTextGray,
                    fontSize = 12.sp
                )
            }

            if (!isCompleted) {
                Text(
                    text = "ТӨЛБӨРИЙН ХЭРЭГСЭЛ СОНГОХ",
                    color = PayTextGray,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 0.6.sp
                )

                PaymentMethodOption.entries.forEach { option ->
                    val active = option == selectedMethod
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(PayCard)
                            .border(
                                width = if (active) 1.5.dp else 1.dp,
                                color = if (active) PayRed else PayBorder,
                                shape = RoundedCornerShape(12.dp)
                            )
                            .clickable {
                                selectedMethod = option
                                showQrCodeBox = option == PaymentMethodOption.QPAY
                            }
                            .padding(14.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = option.icon,
                                contentDescription = null,
                                tint = if (active) PayRed else PayTextGray,
                                modifier = Modifier.size(22.dp)
                            )
                            Column {
                                Text(
                                    text = option.title,
                                    color = PayTextWhite,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Black
                                )
                                Text(
                                    text = option.subtitle,
                                    color = PayTextGray,
                                    fontSize = 11.sp
                                )
                            }
                        }

                        if (active) {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = null,
                                tint = PayRed,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }

                if (selectedMethod == PaymentMethodOption.QPAY && showQrCodeBox) {
                    QrPaymentCard(
                        codeSeed = "${plan.code}_${plan.priceFormatted}",
                        amountLabel = plan.priceFormatted,
                        onConfirmPaid = {
                            isCompleted = true
                            onPaymentSuccess(plan, selectedMethod)
                        }
                    )
                } else {
                    HorizontalDivider(color = PayBorder)

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.VerifiedUser,
                            contentDescription = null,
                            tint = PayGreen,
                            modifier = Modifier.size(15.dp)
                        )
                        Text(
                            text = "Төлбөр төлөгдмөгц шууд дамжуулалт ба VIP эрх идэвхжинэ.",
                            color = PayTextGray,
                            fontSize = 11.sp
                        )
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(PayRed)
                            .clickable {
                                isCompleted = true
                                onPaymentSuccess(plan, selectedMethod)
                            }
                            .padding(vertical = 14.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "ТӨЛӨХ • ${plan.priceFormatted}",
                            color = PayTextWhite,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Black,
                            letterSpacing = 0.5.sp
                        )
                    }
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(PayCard)
                        .border(1.dp, PayGreen.copy(alpha = 0.6f), RoundedCornerShape(12.dp))
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = PayGreen,
                        modifier = Modifier.size(34.dp)
                    )
                    Text(
                        text = "Эрх амжилттай идэвхжлээ",
                        color = PayTextWhite,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Black
                    )
                    Text(
                        text = "Сонгосон суваг: ${selectedMethod.title}",
                        color = PayTextGray,
                        fontSize = 12.sp
                    )
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(PayGreen)
                        .clickable { onDismiss() }
                        .padding(vertical = 13.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "ҮЗЭЖ ЭХЛЭХ",
                        color = PayTextWhite,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Black
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Composable
private fun QrPaymentCard(
    codeSeed: String,
    amountLabel: String,
    onConfirmPaid: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(PayCard)
            .border(1.dp, PayBorder, RoundedCornerShape(14.dp))
            .padding(14.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            text = "QR УНШУУЛЖ ТӨЛӨХ • $amountLabel",
            color = PayTextWhite,
            fontSize = 12.sp,
            fontWeight = FontWeight.Black,
            letterSpacing = 0.4.sp
        )

        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .background(Color.White)
                .border(2.dp, Color(0xFFD1D5DB), RoundedCornerShape(16.dp))
                .padding(14.dp),
            contentAlignment = Alignment.Center
        ) {
            QrMatrixCanvas(
                seedText = codeSeed,
                qrSize = 176.dp
            )
        }

        Text(
            text = "Банкны эсвэл төлбөрийн апп-аар энэ QR кодыг уншуулна уу",
            color = PayTextGray,
            fontSize = 11.sp
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .background(PayRed)
                .clickable { onConfirmPaid() }
                .padding(vertical = 12.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "ТӨЛБӨР ТӨЛСӨН (БАТАЛГААЖУУЛАХ)",
                color = PayTextWhite,
                fontSize = 12.sp,
                fontWeight = FontWeight.Black
            )
        }
    }
}

@Composable
private fun QrMatrixCanvas(
    seedText: String,
    qrSize: Dp
) {
    val gridSize = 25
    val hash = seedText.fold(7) { acc, ch -> acc * 31 + ch.code }

    fun isFinderPattern(row: Int, col: Int): Boolean {
        val inTopLeft = row in 0..6 && col in 0..6
        val inTopRight = row in 0..6 && col in (gridSize - 7) until gridSize
        val inBottomLeft = row in (gridSize - 7) until gridSize && col in 0..6
        if (!inTopLeft && !inTopRight && !inBottomLeft) return false

        val localRow = if (row in 0..6) row else row - (gridSize - 7)
        val localCol = if (col in 0..6) col else col - (gridSize - 7)

        val outerBorder = localRow == 0 || localRow == 6 || localCol == 0 || localCol == 6
        val innerBox = localRow in 2..4 && localCol in 2..4
        return outerBorder || innerBox
    }

    Canvas(modifier = Modifier.size(qrSize)) {
        val cellSize = size.width / gridSize
        for (row in 0 until gridSize) {
            for (col in 0 until gridSize) {
                val darkCell = if (isFinderPattern(row, col)) {
                    true
                } else {
                    val value = abs((row * 73856093) xor (col * 19349663) xor hash)
                    value % 3 != 0
                }
                if (darkCell) {
                    drawRect(
                        color = Color(0xFF111111),
                        topLeft = Offset(col * cellSize, row * cellSize),
                        size = Size(cellSize, cellSize)
                    )
                }
            }
        }
    }
}