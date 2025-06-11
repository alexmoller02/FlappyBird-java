package Functions;

import Classes.Pipe;
import java.util.Random;

public class PipeSpawner {
    public static void spawnPipes(GameState state) {
        int pipeHeight = 512;
        int pipeWidth = 64;
        int pipeX = state.width;
        int spacing = state.pipeGap;
        int topY = -pipeHeight / 4 - new Random().nextInt(pipeHeight / 2);

        Pipe topPipe = new Pipe(pipeX, topY, pipeWidth, pipeHeight, state.topPipeImage);
        Pipe bottomPipe = new Pipe(pipeX, topPipe.y + pipeHeight + spacing, pipeWidth, pipeHeight, state.bottomPipeImage);

        state.pipes.add(topPipe);
        state.pipes.add(bottomPipe);
    }
}
