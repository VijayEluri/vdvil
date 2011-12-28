package no.lau.vdvil.renderer;


import no.bouvet.kpro.renderer.Instruction;
import no.bouvet.kpro.renderer.RendererToken;
import no.lau.vdvil.handler.CompositionI;
import no.lau.vdvil.handler.InstructionInterface;
import no.lau.vdvil.timing.MasterBeatPattern;
import java.io.IOException;
import java.util.SortedSet;
import java.util.TreeSet;

public abstract class SuperRenderer implements InstructionInterface, RendererToken {

    protected SortedSet<Instruction> instructionSet = new TreeSet<Instruction>();

    @Override
    public void setComposition(CompositionI composition, MasterBeatPattern beatPattern) throws IOException {
        for (Instruction instruction : composition.instructions(beatPattern).lock()) {
            if(passesFilter(instruction))
                instructionSet.add(instruction);
            }
        }

    /**
     * Callback to child to check if it is concerned with this object
     * @param instruction to be checked
     * @return true if it is to be added to InstructionList
     */
    abstract protected boolean passesFilter(Instruction instruction);

    public boolean isRendering() {
        return !instructionSet.isEmpty();
    }
}
