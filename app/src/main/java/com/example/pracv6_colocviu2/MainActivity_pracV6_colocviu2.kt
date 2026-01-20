package com.example.pracv6_colocviu2
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pracv6_colocviu2.general.Constants
import com.example.pracv6_colocviu2.network.ClientThread
import com.example.pracv6_colocviu2.network.ServerThread

class MainActivity_pracV6_colocviu2 : AppCompatActivity() {

    // 1. Declaram variabilele pentru elementele vizuale
    private lateinit var serverPortEditText: EditText
    private lateinit var serverConnectButton: Button

    private lateinit var clientAddressEditText: EditText
    private lateinit var clientPortEditText: EditText
    private lateinit var clientWordEditText: EditText
    private lateinit var clientInformationTypeEditText: EditText
    private lateinit var clientGetAutocompleteButton: Button
    private lateinit var clientResultTextView: TextView
    private lateinit var openMapsButton: Button

    // 2. Referinta catre thread-ul Serverului (pentru a-l opri la final)
    private var serverThread: ServerThread? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Legam codul de fisierul XML (asigura-te ca XML-ul tau are acest nume!)
        setContentView(R.layout.activity_pracv6_colocviu2_main)

        // 3. Initializam controalele (le gasim dupa ID-urile din XML)
        serverPortEditText = findViewById(R.id.server_port_edit_text)
        serverConnectButton = findViewById(R.id.server_connect_button)

        clientAddressEditText = findViewById(R.id.client_address_edit_text)
        clientPortEditText = findViewById(R.id.client_port_edit_text)
        clientWordEditText = findViewById(R.id.please_enter_word)
        clientGetAutocompleteButton = findViewById(R.id.client_get_autocomplete_button)
        clientResultTextView = findViewById(R.id.client_result_text_view)

        openMapsButton = findViewById(R.id.go_second_activity)

        // -----------------------------------------------------------------------
        // LOGICA PENTRU SERVER (Partea de sus a ecranului)
        // -----------------------------------------------------------------------
        serverConnectButton.setOnClickListener {
            val serverPort = serverPortEditText.text.toString()

            if (serverPort.isEmpty()) {
                Toast.makeText(this, "Te rog introdu un port pentru server!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Pornim serverul doar daca nu ruleaza deja
            if (serverThread == null || !serverThread!!.isAlive) {
                serverThread = ServerThread(serverPort.toInt())
                serverThread!!.startServer()
                Toast.makeText(this, "Server pornit pe portul $serverPort", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Serverul ruleaza deja!", Toast.LENGTH_SHORT).show()
            }
        }

        // -----------------------------------------------------------------------
        // LOGICA PENTRU CLIENT (Partea de jos a ecranului)
        // -----------------------------------------------------------------------
        clientGetAutocompleteButton.setOnClickListener {
            val clientAddress = clientAddressEditText.text.toString()
            val clientPort = clientPortEditText.text.toString()
            val word = clientWordEditText.text.toString()

            // Validari ca sa nu crape aplicatia daca uiti un camp gol
            if (clientAddress.isEmpty() || clientPort.isEmpty() || word.isEmpty()) {
                Toast.makeText(this, "Completeaza toate campurile de la Client!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Resetam textul rezultatelor
            clientResultTextView.text = ""

            // Pornim un thread de client care se conecteaza la server
            val clientThread = ClientThread(
                clientAddress,
                clientPort.toInt(),
                word,
                clientResultTextView
            )
            clientThread.start()
        }

        openMapsButton.setOnClickListener {
            val intent = Intent(this, MapsActivity::class.java)
            startActivity(intent)
        }
    }

    // Aceasta metoda se apeleaza cand inchizi aplicatia de tot
    override fun onDestroy() {
        Log.i(Constants.TAG, "[MAIN] Aplicatia se inchide, oprim serverul...")
        serverThread?.stopServer()
        super.onDestroy()
    }
}