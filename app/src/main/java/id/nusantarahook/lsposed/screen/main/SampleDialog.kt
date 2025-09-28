package id.nusantarahook.lsposed.screen.main

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransparentDialogTest(
    onDismiss: () -> Unit = {},
    onSubmit: () -> Unit = {},
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
            dismissOnClickOutside = false
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Transparent)
                .padding(12.dp),
            contentAlignment = Alignment.Center
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.outline,
                        shape = RoundedCornerShape(8.dp)
                    ),
                shape = RoundedCornerShape(8.dp),
                elevation = CardDefaults.cardElevation(8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surface)
                ) {
                    // Header
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, end = 8.dp, top = 4.dp, bottom = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Xtream Name",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary,
                                fontSize = 18.sp
                            ),
                            modifier = Modifier.weight(1f)
                        )
                        IconButton(onClick = onDismiss) {
                            Icon(
                                painter = painterResource(android.R.drawable.ic_menu_close_clear_cancel),
                                contentDescription = "Close",
                                tint = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }

                    Divider()

                    // Isi scroll
                    Column(
                        modifier = Modifier
                            //.weight(1f)
                            .verticalScroll(rememberScrollState())
                            .padding(start = 12.dp, end = 12.dp, top = 8.dp, bottom = 8.dp)
                    ) {
                        // di dalam Column scroll
                        var name by remember { mutableStateOf("") }
                        var username by remember { mutableStateOf("") }
                        var password by remember { mutableStateOf("") }
                        var host by remember { mutableStateOf("") }

                        OutlinedTextField(
                            value = name,
                            onValueChange = { name = it },
                            label = { Text("Any Name") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )

                        OutlinedTextField(
                            value = username,
                            onValueChange = { username = it },
                            label = { Text("User Name") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth(),
                            trailingIcon = {
                                if (username.isNotEmpty()) {
                                    IconButton(
                                        onClick = { username = "" },
                                        modifier = Modifier
                                            .background(
                                                color = Color.White.copy(alpha = 0.6f),
                                                shape = CircleShape
                                            )
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Clear,
                                            contentDescription = "Clear text",
                                            tint = MaterialTheme.colorScheme.onSurface // otomatis sesuai tema
                                        )
                                    }
                                }
                            }
                        )

                        OutlinedTextField(
                            value = password,
                            onValueChange = { password = it },
                            label = { Text("Password") },
                            singleLine = true,
                            textStyle = LocalTextStyle.current.copy(fontSize = 14.sp), // kecilkan font
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(min = 40.dp) // tekan tinggi minimal
                        )

                        OutlinedTextField(
                            value = host,
                            onValueChange = { host = it },
                            label = { Text("Host") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )

                        Divider(Modifier.padding(vertical = 8.dp))

                        // Dropdown (format stream)
                        var expanded by remember { mutableStateOf(false) }
                        var selectedText by remember { mutableStateOf("M3U8") }
                        ExposedDropdownMenuBox(
                            expanded = expanded,
                            onExpandedChange = { expanded = !expanded }
                        ) {
                            OutlinedTextField(
                                value = selectedText,
                                onValueChange = {},
                                label = { Text("Stream Format") },
                                readOnly = true,
                                modifier = Modifier
                                    .menuAnchor()
                                    .fillMaxWidth()
                            )
                            ExposedDropdownMenu(
                                expanded = expanded,
                                onDismissRequest = { expanded = false }
                            ) {
                                listOf("M3U8", "DASH", "TS").forEach { option ->
                                    DropdownMenuItem(
                                        text = { Text(option) },
                                        onClick = {
                                            selectedText = option
                                            expanded = false
                                        }
                                    )
                                }
                            }
                        }

                        Spacer(Modifier.height(8.dp))

                        // Switches
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Switch(checked = true, onCheckedChange = {})
                            Spacer(Modifier.width(8.dp))
                            Text("Import Live Channel")
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Switch(checked = false, onCheckedChange = {})
                            Spacer(Modifier.width(8.dp))
                            Text("Import Series")
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Switch(checked = false, onCheckedChange = {})
                            Spacer(Modifier.width(8.dp))
                            Text("Import VODs")
                        }
                    }

                    // Tombol aksi
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.surface)
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        OutlinedButton(
                            onClick = onDismiss,
                            modifier = Modifier
                                .weight(1f)
                                .padding(horizontal = 4.dp)
                        ) {
                            Text("Cancel", fontSize = 12.sp)
                        }
                        Button(
                            onClick = onSubmit,
                            modifier = Modifier
                                .weight(1f)
                                .padding(horizontal = 4.dp)
                        ) {
                            Text("Save", fontSize = 12.sp)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TransparentDialog() {
    var showDialog by remember { mutableStateOf(true) }
    if (showDialog) {
        TransparentDialogTest(
            onDismiss = { showDialog = false },
            onSubmit = { showDialog = false }
        )
    }
}
