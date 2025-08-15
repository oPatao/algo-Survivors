package game;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.net.URL;

public class Musica {
    private Clip clip;
    private FloatControl volumeControl;

    public Musica(String pathMusica) {
        try {
            URL url = getClass().getResource(pathMusica);
            if (url == null) {
                System.err.println("nao foi encontrada");
                return;
            }

            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);

            if (clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            } else {
                System.err.println("Controle de volume não suportado para este áudio.");
            }
        } catch (Exception e) {
            System.err.println("Nao foi encontrada: " + e.getMessage());
            throw new RuntimeException(e);

        }
    }

    public void play() {
        if (clip != null) {
            clip.stop();
            clip.setFramePosition(0);
            clip.start();
        }
    }
    public void loop() {
        if (clip != null) {
            clip.setLoopPoints(0, -1);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    public void loopIni(int startSecs, int endSecs) {
        if (clip != null) {

            int startFrame = (int)(startSecs * clip.getFormat().getFrameRate());
            int endFrame = (int)(endSecs * clip.getFormat().getFrameRate());

            if(endFrame > clip.getFrameLength() || endFrame <= startFrame) {
                System.err.println("pontos de loop invalidos");
                loop();
                return;
            }

            clip.setLoopPoints(startFrame, endFrame);
            clip.loop(Clip.LOOP_CONTINUOUSLY);

        }
    }

    public void stop() {
        if (clip != null) {
            clip.stop();
        }
    }
    public void setVolume(float level) {
        if (volumeControl != null) {
            if (level < 0.0f || level > 1.0f) {
                System.err.println("Nível de volume inválido. Deve ser entre 0.0 e 1.0.");
                return;
            }

            float min = volumeControl.getMinimum();
            float max = volumeControl.getMaximum();
            float volumeEmDB = (max - min) * level + min;
            volumeControl.setValue(volumeEmDB);
        }
    }
}
