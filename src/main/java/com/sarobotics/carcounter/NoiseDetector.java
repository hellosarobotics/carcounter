package com.sarobotics.carcounter;

import javax.sound.sampled.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sarobotics.carcounter.service.CounterService;
import com.sarobotics.carcounter.service.LogEventiService;

import java.util.ArrayList;
import java.util.List;

@Component 
public class NoiseDetector {
    private static final float SAMPLE_RATE = 44100;
    private static final int SAMPLE_SIZE_IN_BITS = 16;
    private static final int CHANNELS = 1;
    private static final boolean SIGNED = true;
    private static final boolean BIG_ENDIAN = false;

    private static final int BUFFER_SIZE = 1024;
    private double NOISE_THRESHOLD = 0.05; // Impostato a 0.0 per rilevare tutti i suoni
    private long DETECTION_PAUSE = 2000; // 4 secondi di pausa tra rilevamenti per evitare conteggi multipli
    private long COUNTER_ID = 1L; // Valore di default del contatore da modificare
    
    private List<NoiseListener> listeners = new ArrayList<>();
    private int carCount = 0; // Contatore delle auto
    private long lastDetectionTime = 0; // Tempo dell'ultimo rilevamento
    private double maxDecibels = 0; // Variabile per tenere traccia del massimo livello di decibel

    public void setNoiseThreshold(double noiseThreshold) {
        this.NOISE_THRESHOLD = noiseThreshold;
    }

    public void setDetectionPause(long detectionPause) {
        this.DETECTION_PAUSE = detectionPause;
    }
    
    public void setCounterID(long counterID) {
        this.COUNTER_ID = counterID;
    }
    
    public void addNoiseListener(NoiseListener listener) {
        listeners.add(listener);
    }
    
    private final CounterService cs;
    private final LogEventiService les;

    @Autowired
    public NoiseDetector(CounterService counterService, LogEventiService _les ) {
        this.cs = counterService;
        this.les = _les;
    }

    public void startListening() {
        AudioFormat format = new AudioFormat(SAMPLE_RATE, SAMPLE_SIZE_IN_BITS, CHANNELS, SIGNED, BIG_ENDIAN);
        TargetDataLine microphone = null;
        try {
            microphone = AudioSystem.getTargetDataLine(format);
            microphone.open(format);
            microphone.start();
            System.out.println("Microfono aperto e in ascolto...");

            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead;
            while (true) {
                bytesRead = microphone.read(buffer, 0, buffer.length);
                if (bytesRead > 0) {
                    double amplitude = calculateAmplitude(buffer);
                    if (amplitude > NOISE_THRESHOLD) {
                        double decibels = 20 * Math.log10(Math.max(amplitude, 1e-10));
                        long currentTime = System.currentTimeMillis();
                        if (decibels < maxDecibels) {
                            maxDecibels = decibels; // Aggiorna il massimo livello di decibel
                        }
                        if (currentTime - lastDetectionTime > DETECTION_PAUSE) {
                            carCount++;
                            lastDetectionTime = currentTime;
                            notifyListeners(decibels);
                        }
                    }
                }
            }
        } catch (LineUnavailableException e) {
            System.err.println("Linea non disponibile: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Errore durante l'ascolto del microfono: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (microphone != null) {
                microphone.close();
            }
        }
    }

    private double calculateAmplitude(byte[] audioData) {
        double sum = 0;
        for (int i = 0; i < audioData.length; i += 2) {
            int sample = (audioData[i + 1] << 8) | (audioData[i] & 0xFF);
            sum += Math.abs(sample) / 32768.0;
        }
        return sum / (audioData.length / 2);
    }


    private void notifyListeners(double decibels) {
        for (NoiseListener listener : listeners) {
            listener.onNoiseDetected(decibels);
        }
        //cs.incrementCounter(1L);
        cs.incrementCounter(COUNTER_ID);
        les.insetNewEvent(decibels);
        System.out.println("Noise detected! Decibels: " + decibels + ". Car count: " + carCount + ". Max decibels: " + maxDecibels);
        
    }

//    public static void main(String[] args) {
//        NoiseDetector detector = new NoiseDetector();
//        detector.addNoiseListener(decibels -> {
//            // Puoi aggiungere ulteriori azioni qui se necessario
//        });
//        detector.startListening();
//    }
}