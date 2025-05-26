package com.example.firebase_app

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.firebase_app.ui.theme.Firebase_appTheme
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Firebase_appTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AppAula()
                }
            }
        }
    }
}


@Composable
fun AppAula() {
    // Variáveis para armazenar os valores dos campos
    var value_name_field by remember { mutableStateOf("") }
    var value_endereco_field by remember { mutableStateOf("") }
    var value_bairro_field by remember { mutableStateOf("") }
    var value_cep_field by remember { mutableStateOf("") }
    var value_cidade_field by remember { mutableStateOf("") }
    var value_estado_field by remember { mutableStateOf("") }

    Column(
        Modifier
            .fillMaxSize()
    ) {
        Row(
            Modifier
                .fillMaxWidth(),
            Arrangement.Center
        ) {
            Text("App Aula")
        }

        // Nome
        Row(
            Modifier
                .fillMaxWidth(),
            Arrangement.Center
        ) {
            TextField(
                value = value_name_field,
                onValueChange = { value_name_field = it },
                label = { Text("Nome") }
            )
        }

        Spacer(modifier = Modifier.height(16.dp)) // AQUI!

        // Endereço
        Row(
            Modifier
                .fillMaxWidth(),
            Arrangement.Center
        ) {
            TextField(
                value = value_endereco_field,
                onValueChange = { value_endereco_field = it },
                label = { Text("Endereço") }
            )
        }

        Spacer(modifier = Modifier.height(16.dp)) // AQUI!

        // Bairro
        Row(
            Modifier
                .fillMaxWidth(),
            Arrangement.Center
        ) {
            TextField(
                value = value_bairro_field,
                onValueChange = { value_bairro_field = it },
                label = { Text("Bairro") }
            )
        }

        Spacer(modifier = Modifier.height(16.dp)) // AQUI!

        // CEP
        Row(
            Modifier
                .fillMaxWidth(),
            Arrangement.Center
        ) {
            TextField(
                value = value_cep_field,
                onValueChange = { value_cep_field = it },
                label = { Text("CEP") }
            )
        }

        Spacer(modifier = Modifier.height(16.dp)) // AQUI!

        // Cidade
        Row(
            Modifier
                .fillMaxWidth(),
            Arrangement.Center
        ) {
            TextField(
                value = value_cidade_field,
                onValueChange = { value_cidade_field = it },
                label = { Text("Cidade") }
            )
        }

        Spacer(modifier = Modifier.height(16.dp)) // AQUI!

        // Estado
        Row(
            Modifier
                .fillMaxWidth(),
            Arrangement.Center
        ) {
            TextField(
                value = value_estado_field,
                onValueChange = { value_estado_field = it },
                label = { Text("Estado") }
            )
        }

        Spacer(modifier = Modifier.height(16.dp)) // AQUI!

        // Row dos botões de ação
        Row(
            Modifier
                .fillMaxWidth(),
            Arrangement.Center
        ) {
            // Cadastrar
            Column(
                Modifier
                    .fillMaxWidth(0.4f),
            ) {
                Button(onClick = {
                    val db = Firebase.firestore

                    // Criando o usuário com os valores dos campos
                    val user = hashMapOf(
                        "nome" to value_name_field,
                        "endereco" to value_endereco_field,
                        "bairro" to value_bairro_field,
                        "CEP" to value_cep_field,
                        "Cidade" to value_cidade_field,
                        "Estado" to value_estado_field
                    )

                    db.collection("users")
                        .add(user)
                        .addOnSuccessListener { documentReference ->
                            Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                        }
                        .addOnFailureListener { e ->
                            Log.w(TAG, "Error adding document", e)
                        }
                }) {
                    Text("Cadastrar")
                }
            }

            // Cancelar
            Column(
            ) {
                Button(onClick = {

                }) {
                    Text("Cancelar")
                }
            }
        }

        // Exibindos dados
        Spacer(modifier = Modifier.height(16.dp)) // AQUI!

        Row(
            Modifier
                .fillMaxWidth(),
            Arrangement.Center
        ) {
            Text("Dados")
        }

        val data = remember { mutableStateListOf<Map<String, Any>>() }

        LaunchedEffect(Unit) {
            val db = Firebase.firestore

            db.collection("users")
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        data.add(document.data) // aqui salva todos os campos do documento
                    }
                }
                .addOnFailureListener { exception ->
                    Log.w("TAG", "Erro ao buscar documentos.", exception)
                }
        }

        val scrollState = rememberScrollState()

        Column(modifier = Modifier.verticalScroll(scrollState)) {
            for (doc in data) {
                val nome = doc["nome"] as? String ?: "Sem nome"
                val telefone = doc["telefone"] as? String ?: "Sem telefone"
                Text(text = "Nome: $nome")
                Text(text = "Telefone: $telefone")
                Divider()
            }
        }

    }
}

@Preview
@Composable
fun appAulaPreview() {
    Firebase_appTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            AppAula()
        }
    }
}