package it.alf.jharmonix.core.port;

import it.alf.jharmonix.core.model.Progression;

import java.util.List;

public interface HarmonyGeneratorPort {

    List<Progression> generate(ProgressionRequest request);
}
