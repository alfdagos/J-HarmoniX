package it.alf.jharmonix.core.engine;

import it.alf.jharmonix.core.model.*;
import it.alf.jharmonix.core.port.ProgressionRequest.ModulationFrequency;

import java.util.ArrayList;
import java.util.List;

public final class ModulationStrategy {

    private final JazzRuleEngine ruleEngine;
    private final java.util.Random random;

    public ModulationStrategy(JazzRuleEngine ruleEngine, long seed) {
        this.ruleEngine = ruleEngine;
        this.random = new java.util.Random(seed);
    }

    public ModulationStrategy(JazzRuleEngine ruleEngine) {
        this(ruleEngine, System.currentTimeMillis());
    }

    public List<Chord> buildBridge(KeySignature source,
                                   KeySignature target,
                                   ModulationFrequency frequency) {
        return switch (frequency) {
            case NONE   -> List.of();
            case LOW    -> pivotChordBridge(source, target);
            case MEDIUM -> secondaryIIVBridge(target);
            case HIGH   -> chooseAdvanced(source, target);
        };
    }

    private List<Chord> pivotChordBridge(KeySignature source, KeySignature target) {
        Scale sourcScale = source.getScale();
        Scale targetScale = target.getScale();

        for (int sd = 0; sd < sourcScale.size(); sd++) {
            Chord sourceChord = source.diatonicChord(sd);
            for (int td = 0; td < targetScale.size(); td++) {
                Chord targetChord = target.diatonicChord(td);
                if (sourceChord.equals(targetChord)) {
                    return List.of(sourceChord);
                }
            }
        }
        return secondaryIIVBridge(target);
    }

    private List<Chord> secondaryIIVBridge(KeySignature target) {
        List<Chord> bridge = new ArrayList<>();
        bridge.add(target.diatonicChord(1));
        bridge.add(target.diatonicChord(4));
        return bridge;
    }

    private List<Chord> chooseAdvanced(KeySignature source, KeySignature target) {
        int roll = random.nextInt(3);
        return switch (roll) {
            case 0 -> tritoneSubModulation(target);
            case 1 -> pivotChordBridge(source, target);
            default -> secondaryIIVBridge(target);
        };
    }

    private List<Chord> tritoneSubModulation(KeySignature target) {
        Chord dominant = target.diatonicChord(4);
        Chord tritoneSub = dominant.tritoneSubstitution();
        return List.of(tritoneSub);
    }
}
