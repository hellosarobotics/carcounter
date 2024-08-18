package com.sarobotics.carcounter;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CarcounterApplication {

	public static void main(String[] args) {
		 //ConfigurableApplicationContext context = 
				 SpringApplication.run(CarcounterApplication.class, args);
		
		
		// Una volta che l'applicazione è avviata, puoi ottenere l'istanza di NoiseDetector e avviare il metodo necessario
       // NoiseDetector noiseDetector = context.getBean(NoiseDetector.class);
       // noiseDetector.startListening();
        
//        NoiseDetector detector = new NoiseDetector();
//        detector.addNoiseListener(decibels -> {
//            // Puoi aggiungere ulteriori azioni qui se necessario
//        });
//        detector.startListening();
				 //Aggiungiamo un commento per fare la push
	}
	
	
    @Bean
    public CommandLineRunner commandLineRunner(NoiseDetector noiseDetector) {
        return args -> {
            for (String arg : args) {
                if (arg.startsWith("NOISE_THRESHOLD=")) {
                    noiseDetector.setNoiseThreshold(Double.parseDouble(arg.split("=")[1]));
                }
                if (arg.startsWith("DETECTION_PAUSE=")) {
                    noiseDetector.setDetectionPause(Long.parseLong(arg.split("=")[1]));
                }
                if (arg.startsWith("COUNTER_ID=")) {
                    noiseDetector.setCounterID(Long.parseLong(arg.split("=")[1]));
                }
            }
            noiseDetector.startListening();
        };
    }

}
