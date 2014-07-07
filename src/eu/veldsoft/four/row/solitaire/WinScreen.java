/*
 This file is a part of Four Row Solitaire

 Copyright (C) 2010-2014 by Matt Stephen, Todor Balabanov, Konstantin Tsanov, Ventsislav Medarov

 Four Row Solitaire is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 Four Row Solitaire is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with FourRowSolitaire.  If not, see <http://www.gnu.org/licenses/>.
 */

package eu.veldsoft.four.row.solitaire;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import java.net.URL;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.event.MouseInputAdapter;

/**
 * Class: WinScreen
 * 
 * Description: The WinScreen class manages the win animation and sounds window.
 * 
 * @author Matt Stephen, Todor Balabanov, Konstantin Tsanov, Ventsislav Medarov
 */
public class WinScreen extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// TODO Use shared preferences. Change int with enum.
	static int animation = 0;

	// TODO Use shared preferences. Change int with enum.
	static int sounds = 0;

	/**
	 * Sound threat.
	 */
	private SoundThread sound = null;

	/**
	 * If the sound settings are on, plays the win sounds. If the animation
	 * settings are on, fires the fireworks.
	 */
	public WinScreen() {
		setUndecorated(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setFocusable(true);

		/*
		 * For JDialog instead of JFrame, but the mouse listener doesn't work
		 * with JDialog for some reason.
		 * setModalityType(Dialog.ModalityType.DOCUMENT_MODAL);
		 */
		if (sounds == 1) {
			setSize(200, 200);
			sound = new SoundThread();
			sound.run();
		}

		if (animation == 1) {
			setSize(800, 600);

			FireworksDisplay fw = new FireworksDisplay(100, 200);
			add(fw);
			fw.restartDisplay();
			setLocationRelativeTo(null);
		} else {
			this.setLocation(0, 0);
			add(new JLabel("Click Here to Stop Music"));
		}

		setVisible(true);

		addMouseListener(new MouseInputAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (sound != null && sound.sequencer.isRunning()) {
					sound.sequencer.stop();
				}

				WinScreen.this.dispose();
			}
		});

		addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e) {
				WinScreen.this.requestFocus();
			}
		});
	}

	/**
	 * Creates the sound thread. The sound thread is called only when the sounds
	 * are turned on. Manages the sounds.
	 * 
	 * @author Matt Stephen, Todor Balabanov, Konstantin Tsanov, Ventsislav Medarov
	 */
	private class SoundThread extends Thread {
		public Sequencer sequencer;

		/**
		 * 
		 */
		public void run() {
			/*
			 * To hold choice.
			 */
			String song = "";

			try {
				/*
				 * Doesn't work as a .jar file.
				 */
				File songDir = new File(getClass().getResource("sounds/win/")
						.toURI());
				String[] songs = songDir.list();
				boolean retry = true;

				do {
					song = songs[Common.PRNG.nextInt(songs.length)];

					if (song.toLowerCase().contains(".mid")) {
						retry = false;
					}
				} while (retry);
			} catch (Exception ex) {
				int songInt = Common.PRNG.nextInt(4);

				if (songInt == 0) {
					song = "celebration.mid";
				} else if (songInt == 1) {
					song = "anotheronebitesthedust.mid";
				} else if (songInt == 2) {
					song = "wearethechampions.mid";
				} else if (songInt == 3) {
					song = "bluedabadee.mid";
				}
			}

			URL filelocation = getClass().getResource("sounds/win/" + song);

			try {
				Sequence sequence = MidiSystem.getSequence(filelocation);
				sequencer = MidiSystem.getSequencer();
				sequencer.open();
				sequencer.setSequence(sequence);
				sequencer.setLoopCount(0);
				sequencer.start();
			} catch (Exception ex) {
				System.err.println("Error opening win sound file.");
			}
		}
	}
}
