package com.example.mypractical

import android.content.ComponentName
import android.net.Uri
import android.os.Bundle
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.example.mypractical.R
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.ui.PlaybackControlView
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import com.google.android.exoplayer2.util.Util
import kotlinx.android.synthetic.main.exo_playback_control_view.*

/**
 * Created by Meera
 * Date : 9-08-2019
 * Package_Name : com.example.mypractical
 * Class_Name : Player
 * Description : This class used to initialize player and count thumbup and thumbdown click.
 */
class Player : AppCompatActivity()
{
    @BindView(R.id.epvPlayerView)
    lateinit var exoplayerView : PlayerView

    @BindView(R.id.txtThumbDown)
    lateinit var txtThumbDown : TextView

    @BindView(R.id.txtThumbUp)
    lateinit var txtThumbUp : TextView

    @BindView(R.id.imgThumbDown)
    lateinit var imgThumbDown : ImageView

    @BindView(R.id.imgThumbUp)
    lateinit var imgThumbUp : ImageView

    private var exoplayer : SimpleExoPlayer? = null
    private var playbackStateBuilder : PlaybackStateCompat.Builder? = null
    private var mediaSession: MediaSessionCompat? = null
    lateinit var videoUrl : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.player_activity)
        getSupportActionBar()?.setTitle("Player")
        ButterKnife.bind(this)

        initializePlayer()
        getCountOfThumb()

    }

    /**
     * This method use for cout ThumbUp and ThumbDown
     */
    private fun getCountOfThumb() {
        // Count total thumb Up
        imgThumbUp.setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {
                txtThumbUp.text = ( txtThumbUp.text.toString().toInt() + 1).toString()
            }
        })

        // Count total thumb down
        imgThumbDown.setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {
                txtThumbDown.text = ( txtThumbDown.text.toString().toInt() + 1).toString()
            }
        })
    }

    /**
     * This method used for initialize Exoplayer
     */
    private fun initializePlayer() {
        if(intent != null) {
            videoUrl = intent.getStringExtra("EXTRA_VIDEOURL")
        }
        val trackSelector = DefaultTrackSelector()
        exoplayer = ExoPlayerFactory.newSimpleInstance(this, trackSelector)
        exoplayerView?.player = exoplayer

        val userAgent = Util.getUserAgent(this, "Exo")
        val mediaUri = Uri.parse(videoUrl)
        val mediaSource = ProgressiveMediaSource.Factory(DefaultDataSourceFactory(this, userAgent))
            .createMediaSource(mediaUri)

        exoplayer?.prepare(mediaSource)
        exoplayer?.playWhenReady = true
        val componentName = ComponentName(this, "Exo")
        mediaSession = MediaSessionCompat(this, "ExoPlayer", componentName, null)
        playbackStateBuilder = PlaybackStateCompat.Builder()
        playbackStateBuilder?.setActions(PlaybackStateCompat.ACTION_PLAY or PlaybackStateCompat.ACTION_PAUSE or
                PlaybackStateCompat.ACTION_FAST_FORWARD)
        mediaSession?.setPlaybackState(playbackStateBuilder?.build())
        mediaSession?.isActive = true
    }

    // This method used for Release Exoplyer on activity destroy
    override fun onDestroy() {
        super.onDestroy()
        releasePlayer()
    }

    // This method used for Release Exoplayer
    private fun releasePlayer() {
        if (exoplayer != null) {
            exoplayer?.stop()
            exoplayer?.release()
            exoplayer = null
        }
    }


}