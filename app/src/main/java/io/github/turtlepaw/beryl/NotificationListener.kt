package io.github.turtlepaw.beryl

import android.app.Notification
import android.media.MediaMetadata
import android.media.session.MediaController
import android.media.session.MediaSession
import android.media.session.PlaybackState
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log

class NotificationListenerService : NotificationListenerService() {
    override fun onNotificationPosted(sbn: StatusBarNotification?) {
        super.onNotificationPosted(sbn)
        Log.d(
            "NotificationListenerService",
            "Notification posted: $sbn"
        )

        val notification = sbn?.notification ?: return
        val extras = notification.extras

        // get media session token
        val token = extras.getParcelable(
            Notification.EXTRA_MEDIA_SESSION,
            MediaSession.Token::class.java
        ) ?: return

        // create controller
        val controller = MediaController(this, token)

        // read metadata
        val metadata = controller.metadata
        val title = metadata?.getString(MediaMetadata.METADATA_KEY_TITLE)
        val artist = metadata?.getString(MediaMetadata.METADATA_KEY_ARTIST)
        val album = metadata?.getString(MediaMetadata.METADATA_KEY_ALBUM)

        // check if playing
        val isPlaying = controller.playbackState?.state == PlaybackState.STATE_PLAYING

        Log.d(
            "NotificationListenerService",
            "Now playing: $title by $artist from $album. Playing: $isPlaying"
        )

    }
}