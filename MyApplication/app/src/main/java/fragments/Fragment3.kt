package fragments

import android.media.MediaPlayer
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.myapplication.R
import android.annotation.SuppressLint
import android.os.Handler
import android.os.Message

import android.widget.SeekBar


class Fragment3 : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v: View = inflater.inflate(R.layout.fragment_3, container, false)

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