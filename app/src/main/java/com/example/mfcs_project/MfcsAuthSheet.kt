package com.example.mfcs_project

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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val AuthBgDark = Color(0xFF121212)
private val AuthCard = Color(0xFF1B1B1B)
private val AuthRed = Color(0xFFE50914)
private val AuthDeepRed = Color(0xFF4A070B)
private val AuthGold = Color(0xFFF59E0B)
private val AuthBorder = Color(0xFF2B2B2B)
private val AuthWhite = Color(0xFFFFFFFF)
private val AuthGray = Color(0xFFA6A6A6)

enum class AuthMode(val tabTitle: String) {
    LOGIN("НЭВТРЭХ"),
    REGISTER("БҮРТГҮҮЛЭХ")
}

data class AuthUserSession(
    val displayName: String,
    val contact: String,
    val isNewRegistration: Boolean
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MfcsAuthBottomSheet(
    initialMode: AuthMode = AuthMode.LOGIN,
    onDismiss: () -> Unit,
    onAuthSuccess: (AuthUserSession) -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var mode by remember { mutableStateOf(initialMode) }
    var fullName by remember { mutableStateOf("") }
    var contact by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = AuthBgDark,
        contentColor = AuthWhite,
        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(horizontal = 18.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "MFCS ACCOUNT",
                        color = AuthRed,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Black,
                        letterSpacing = 0.8.sp
                    )
                    Text(
                        text = if (mode == AuthMode.LOGIN) "ХЭРЭГЛЭГЧ НЭВТРЭХ" else "ШИНЭ БҮРТГЭЛ ҮҮСГЭХ",
                        color = AuthWhite,
                        fontSize = 19.sp,
                        fontWeight = FontWeight.Black
                    )
                }

                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Хаах",
                    tint = AuthGray,
                    modifier = Modifier
                        .size(22.dp)
                        .clickable { onDismiss() }
                )
            }

            // Нэвтрэх / Бүртгүүлэх горим солих товч
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(AuthCard)
                    .border(1.dp, AuthBorder, RoundedCornerShape(10.dp))
                    .padding(4.dp),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                AuthMode.entries.forEach { tab ->
                    val active = tab == mode
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(8.dp))
                            .background(
                                if (active) Brush.horizontalGradient(listOf(AuthRed, AuthDeepRed))
                                else Brush.horizontalGradient(listOf(Color.Transparent, Color.Transparent))
                            )
                            .clickable {
                                mode = tab
                                errorMessage = null
                            }
                            .padding(vertical = 9.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = tab.tabTitle,
                            color = if (active) AuthWhite else AuthGray,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Black
                        )
                    }
                }
            }

            if (mode == AuthMode.REGISTER) {
                OutlinedTextField(
                    value = fullName,
                    onValueChange = {
                        fullName = it
                        errorMessage = null
                    },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    label = { Text("Таны нэр", color = AuthGray, fontSize = 12.sp) },
                    leadingIcon = {
                        Icon(Icons.Default.Person, contentDescription = null, tint = AuthGray)
                    },
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = AuthWhite,
                        unfocusedTextColor = AuthWhite,
                        focusedBorderColor = AuthRed,
                        unfocusedBorderColor = AuthBorder,
                        focusedContainerColor = AuthCard,
                        unfocusedContainerColor = AuthCard,
                        cursorColor = AuthRed
                    )
                )
            }

            OutlinedTextField(
                value = contact,
                onValueChange = {
                    contact = it
                    errorMessage = null
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                label = { Text("Имэйл эсвэл утасны дугаар", color = AuthGray, fontSize = 12.sp) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = AuthWhite,
                    unfocusedTextColor = AuthWhite,
                    focusedBorderColor = AuthRed,
                    unfocusedBorderColor = AuthBorder,
                    focusedContainerColor = AuthCard,
                    unfocusedContainerColor = AuthCard,
                    cursorColor = AuthRed
                )
            )

            OutlinedTextField(
                value = password,
                onValueChange = {
                    password = it
                    errorMessage = null
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                label = { Text("Нууц үг (хамгийн багадаа 6 тэмдэгт)", color = AuthGray, fontSize = 12.sp) },
                leadingIcon = {
                    Icon(Icons.Default.Lock, contentDescription = null, tint = AuthGray)
                },
                trailingIcon = {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            imageVector = if (passwordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                            contentDescription = null,
                            tint = AuthGray
                        )
                    }
                },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = AuthWhite,
                    unfocusedTextColor = AuthWhite,
                    focusedBorderColor = AuthRed,
                    unfocusedBorderColor = AuthBorder,
                    focusedContainerColor = AuthCard,
                    unfocusedContainerColor = AuthCard,
                    cursorColor = AuthRed
                )
            )

            errorMessage?.let { err ->
                Text(
                    text = err,
                    color = AuthGold,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            HorizontalDivider(color = AuthBorder)

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(AuthRed)
                    .clickable {
                        val cleanContact = contact.trim()
                        val cleanName = fullName.trim()
                        when {
                            mode == AuthMode.REGISTER && cleanName.length < 2 -> {
                                errorMessage = "Нэрээ зөв оруулна уу."
                            }
                            cleanContact.isEmpty() -> {
                                errorMessage = "Имэйл эсвэл утасны дугаараа оруулна уу."
                            }
                            password.length < 6 -> {
                                errorMessage = "Нууц үг хамгийн багадаа 6 тэмдэгт байх ёстой."
                            }
                            else -> {
                                val resolvedName = if (mode == AuthMode.REGISTER) {
                                    cleanName
                                } else {
                                    cleanContact.substringBefore("@").ifBlank { "MFCS Fan" }
                                }
                                onAuthSuccess(
                                    AuthUserSession(
                                        displayName = resolvedName,
                                        contact = cleanContact,
                                        isNewRegistration = mode == AuthMode.REGISTER
                                    )
                                )
                                onDismiss()
                            }
                        }
                    }
                    .padding(vertical = 14.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (mode == AuthMode.LOGIN) "НЭВТРЭХ" else "БҮРТГҮҮЛЭХ",
                    color = AuthWhite,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 0.6.sp
                )
            }

            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}