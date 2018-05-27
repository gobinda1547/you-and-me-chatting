package org.gobinda.extra;

import java.applet.Applet;
import java.applet.AudioClip;
import java.net.URL;

import javax.swing.JApplet;

public class SoundManager extends JApplet {

	private static final long serialVersionUID = 1L;

	private static SoundManager soundManager;
	private static boolean makeSoundOnOff = true;

	private AudioClip audioClip;

	public SoundManager() {
		String str = "TUS.WAV";
		URL url = getClass().getResource(str);
		audioClip = Applet.newAudioClip(url);
	}

	public static void playSound() {
		if (soundManager == null) {
			soundManager = new SoundManager();
		}
		soundManager.playSoundNow();
	}

	private void playSoundNow() {
		Thread th = new Thread(new Runnable() {
			@Override
			public void run() {
				if (makeSoundOnOff) {
					audioClip.play();
				}
			}
		});
		th.start();
	}

	public static void changeSoundSettings() {
		makeSoundOnOff = (makeSoundOnOff) ? false : true;
	}
}
