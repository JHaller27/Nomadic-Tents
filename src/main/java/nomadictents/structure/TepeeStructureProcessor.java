package nomadictents.structure;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import nomadictents.NTRegistry;
import nomadictents.NomadicTents;
import nomadictents.block.TepeeBlock;

import javax.annotation.Nullable;
import java.util.Random;

public class TepeeStructureProcessor extends StructureProcessor {

    public static final Codec<TepeeStructureProcessor> CODEC = Codec.unit(TepeeStructureProcessor::new);

    public static final TepeeStructureProcessor TEPEE_PROCESSOR = new TepeeStructureProcessor();

    @Nullable
    @Override
    public StructureTemplate.StructureBlockInfo process(LevelReader level, BlockPos rawPos, BlockPos pos, StructureTemplate.StructureBlockInfo rawBlockInfo, StructureTemplate.StructureBlockInfo blockInfo, StructurePlaceSettings placementSettings, @Nullable StructureTemplate template) {
        // process blank tepee wall
        BlockPos p = blockInfo.pos;
        if (blockInfo.state.getBlock() == NTRegistry.BlockReg.BLANK_TEPEE_WALL) {
            // random pattern using block position as seed
            if (p.getY() % 2 == 0) {
                Random rand = new Random(p.getY() + pos.hashCode());
                return new StructureTemplate.StructureBlockInfo(p, TepeeBlock.getRandomPattern(rand), null);
            }
            // random design using existing seeded random
            if (placementSettings.getRandom(null).nextInt(100) < NomadicTents.CONFIG.TEPEE_DECORATED_CHANCE.get()) {
                return new StructureTemplate.StructureBlockInfo(p, TepeeBlock.getRandomSymbol(placementSettings.getRandom(null)), null);
            }
        }
        return blockInfo;
    }

    @Override
    protected StructureProcessorType<?> getType() {
        return NTRegistry.ProcessorReg.TEPEE_PROCESSOR;
    }
}
