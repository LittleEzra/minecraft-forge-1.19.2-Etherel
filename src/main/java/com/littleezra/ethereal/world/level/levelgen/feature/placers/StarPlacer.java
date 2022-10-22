package com.littleezra.ethereal.world.level.levelgen.feature.placers;

import com.littleezra.ethereal.world.level.levelgen.feature.configurations.EtherealSapConfiguration;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.BiConsumer;

public class StarPlacer {

    public void placeStar(BlockPos rootPos, BlockState state, BiConsumer<BlockPos, BlockState> biConsumer, RandomSource randomSource, EtherealSapConfiguration configuration)
    {
        List<BlockPos> positions = new List<BlockPos>() {
            @Override
            public int size() {
                return 0;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public boolean contains(Object o) {
                return false;
            }

            @NotNull
            @Override
            public Iterator<BlockPos> iterator() {
                return null;
            }

            @NotNull
            @Override
            public Object[] toArray() {
                return new Object[0];
            }

            @NotNull
            @Override
            public <T> T[] toArray(@NotNull T[] a) {
                return null;
            }

            @Override
            public boolean add(BlockPos blockPos) {
                return false;
            }

            @Override
            public boolean remove(Object o) {
                return false;
            }

            @Override
            public boolean containsAll(@NotNull Collection<?> c) {
                return false;
            }

            @Override
            public boolean addAll(@NotNull Collection<? extends BlockPos> c) {
                return false;
            }

            @Override
            public boolean addAll(int index, @NotNull Collection<? extends BlockPos> c) {
                return false;
            }

            @Override
            public boolean removeAll(@NotNull Collection<?> c) {
                return false;
            }

            @Override
            public boolean retainAll(@NotNull Collection<?> c) {
                return false;
            }

            @Override
            public void clear() {

            }

            @Override
            public BlockPos get(int index) {
                return null;
            }

            @Override
            public BlockPos set(int index, BlockPos element) {
                return null;
            }

            @Override
            public void add(int index, BlockPos element) {

            }

            @Override
            public BlockPos remove(int index) {
                return null;
            }

            @Override
            public int indexOf(Object o) {
                return 0;
            }

            @Override
            public int lastIndexOf(Object o) {
                return 0;
            }

            @NotNull
            @Override
            public ListIterator<BlockPos> listIterator() {
                return null;
            }

            @NotNull
            @Override
            public ListIterator<BlockPos> listIterator(int index) {
                return null;
            }

            @NotNull
            @Override
            public List<BlockPos> subList(int fromIndex, int toIndex) {
                return null;
            }
        };

        positions.add(rootPos);

        int size = getRandomNumber(1, 5);

        for (int i = 0; i < size; i++)
        {
            List<BlockPos> newPositions = new List<BlockPos>() {
                @Override
                public int size() {
                    return 0;
                }

                @Override
                public boolean isEmpty() {
                    return false;
                }

                @Override
                public boolean contains(Object o) {
                    return false;
                }

                @NotNull
                @Override
                public Iterator<BlockPos> iterator() {
                    return null;
                }

                @NotNull
                @Override
                public Object[] toArray() {
                    return new Object[0];
                }

                @NotNull
                @Override
                public <T> T[] toArray(@NotNull T[] a) {
                    return null;
                }

                @Override
                public boolean add(BlockPos blockPos) {
                    return false;
                }

                @Override
                public boolean remove(Object o) {
                    return false;
                }

                @Override
                public boolean containsAll(@NotNull Collection<?> c) {
                    return false;
                }

                @Override
                public boolean addAll(@NotNull Collection<? extends BlockPos> c) {
                    return false;
                }

                @Override
                public boolean addAll(int index, @NotNull Collection<? extends BlockPos> c) {
                    return false;
                }

                @Override
                public boolean removeAll(@NotNull Collection<?> c) {
                    return false;
                }

                @Override
                public boolean retainAll(@NotNull Collection<?> c) {
                    return false;
                }

                @Override
                public void clear() {

                }

                @Override
                public BlockPos get(int index) {
                    return null;
                }

                @Override
                public BlockPos set(int index, BlockPos element) {
                    return null;
                }

                @Override
                public void add(int index, BlockPos element) {

                }

                @Override
                public BlockPos remove(int index) {
                    return null;
                }

                @Override
                public int indexOf(Object o) {
                    return 0;
                }

                @Override
                public int lastIndexOf(Object o) {
                    return 0;
                }

                @NotNull
                @Override
                public ListIterator<BlockPos> listIterator() {
                    return null;
                }

                @NotNull
                @Override
                public ListIterator<BlockPos> listIterator(int index) {
                    return null;
                }

                @NotNull
                @Override
                public List<BlockPos> subList(int fromIndex, int toIndex) {
                    return null;
                }
            };
            for (int pI = 0; pI < positions.size(); pI++){
                BlockPos position = positions.get(pI);
                BlockPos[] neighbours = getNeighbours(position);
                for (int nI = 0; nI < neighbours.length; nI++)
                {
                    BlockPos neighbour = neighbours[nI];
                    if (!positions.contains(neighbour))
                    {newPositions.add(neighbour);}
                }
            }

            for (int nI = 0; nI < newPositions.size(); nI++)
            {
                BlockPos neighbour = newPositions.get(nI);
                positions.add(neighbour);
            }
        }

        for (int i = 0; i < positions.size(); i++){
            tryPlaceBlock(biConsumer, positions.get(i), state, configuration, randomSource);
        }


    }

    public int getRandomNumber(int min, int max){
        return (int)((Math.random() * (max - min)) + min);
    }

    public BlockPos[] getNeighbours(BlockPos pos)
    {
        BlockPos[] positions = new BlockPos[6];

        positions[0] = new BlockPos(pos.getX(), pos.getY() + 1f, pos.getZ());
        positions[1] = new BlockPos(pos.getX(), pos.getY() - 1f, pos.getZ());
        positions[2] = new BlockPos(pos.getX() + 1f, pos.getY(), pos.getZ());
        positions[3] = new BlockPos(pos.getX() - 1f, pos.getY(), pos.getZ());
        positions[4] = new BlockPos(pos.getX(), pos.getY(), pos.getZ() + 1f);
        positions[5] = new BlockPos(pos.getX(), pos.getY(), pos.getZ() - 1f);

        return positions;
    }

    private static void tryPlaceBlock(BiConsumer<BlockPos, BlockState> biConsumer, BlockPos pos, BlockState state, EtherealSapConfiguration configuration, RandomSource randomSource)
    {
        if (configuration.blockProvider.getState(randomSource, pos).isAir()){
            biConsumer.accept(pos, state);
        }
    }
}
