package fragments

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.*
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot


class Fragment4 : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v: View = inflater.inflate(R.layout.fragment_4, container, false)
        val button1: Button = v.findViewById(R.id.button)
        button1.setOnClickListener {
            val intent = Intent(this@Fragment4.context, GetData::class.java)
            startActivity(intent)
        }

        //**********Logout
        var auth = FirebaseAuth.getInstance()
        auth.addAuthStateListener {
            if (auth.currentUser == null){
                activity?.finish()
            }
        }
        val logout: Button = v.findViewById(R.id.btn_logout)
        logout.setOnClickListener {
            Toast.makeText(v.context, "Logging Out...", Toast.LENGTH_LONG).show()
            auth.signOut()

        }


        var mp: MediaPlayer
        var totalTime: Int = 0



        mp = MediaPlayer.create(v.context, R.raw.music)
        mp.isLooping = true
        mp.setVolume(0.5f, 0.5f)
        totalTime = mp.duration

        // Volume Bar
        var volume = v.findViewById<SeekBar>(R.id.volumeBar)
        volume.setOnSeekBarChangeListener(
            object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekbar: SeekBar?, progress: Int, fromUser: Boolean) {
                    if (fromUser) {
                        var volumeNum = progress / 100.0f
                        mp.setVolume(volumeNum, volumeNum)
                    }
                }
                override fun onStartTrackingTouch(p0: SeekBar?) {
                }
                override fun onStopTrackingTouch(p0: SeekBar?) {
                }
            }
        )

        // Position Bar
        var position = v.findViewById<SeekBar>(R.id.positionBar)
        position.max = totalTime
        position.setOnSeekBarChangeListener(
            object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                    if (fromUser) {
                        mp.seekTo(progress)
                    }
                }
                override fun onStartTrackingTouch(p0: SeekBar?) {
                }
                override fun onStopTrackingTouch(p0: SeekBar?) {
                }
            }
        )

        fun createTimeLabel(time: Int): String {
            var timeLabel = ""
            var min = time / 1000 / 60
            var sec = time / 1000 % 60

            timeLabel = "$min:"
            if (sec < 10) timeLabel += "0"
            timeLabel += sec

            return timeLabel
        }

        @SuppressLint("HandlerLeak")
        var handler = object : Handler() {
            override fun handleMessage(msg: Message) {
                var currentPosition = msg.what

                // Update positionBar
                position.progress = currentPosition

                // Update Labels
                val label1    = v.findViewById<TextView>(R.id.elapsedTimeLabel)
                var elapsedTime = createTimeLabel(currentPosition)
                label1.text = elapsedTime

                val label2    = v.findViewById<TextView>(R.id.remainingTimeLabel)
                var remainingTime = createTimeLabel(totalTime - currentPosition)
                label2.text = "-$remainingTime"
            }
        }
        // Thread
        Thread(Runnable {
            while (mp != null) {
                try {
                    var msg = Message()
                    msg.what = mp.currentPosition
                    handler.sendMessage(msg)
                    Thread.sleep(1000)
                } catch (e: InterruptedException) {
                }
            }
        }).start()


        val button = v.findViewById<Button>(R.id.playBtn)


        button.setOnClickListener {
            if (mp.isPlaying) {
                // Stop
                mp.pause()
                button.setBackgroundResource(R.drawable.play)

            } else {
                // Start
                mp.start()
                button.setBackgroundResource(R.drawable.stop)
            }
        }

        return v
    }




}
