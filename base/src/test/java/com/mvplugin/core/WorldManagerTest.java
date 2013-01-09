package com.mvplugin.core;

import com.mvplugin.core.minecraft.WorldEnvironment;
import com.mvplugin.core.minecraft.WorldType;
import com.mvplugin.core.world.MultiverseWorld;
import com.mvplugin.core.world.WorldCreationSettings;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class WorldManagerTest {

    private WorldManager worldManager;
    private MultiverseCoreAPI coreApi;
    private WorldUtil worldUtil;

    @Before
    public void setUp() throws Exception {
        coreApi = PowerMockito.mock(MultiverseCoreAPI.class);
        worldUtil = MockWorldUtil.getMockedWorldUtil();
        worldManager = new WorldManager<MultiverseWorld>(coreApi, worldUtil);
    }

    @After
    public void tearDown() throws Exception {
        worldManager = null;
    }

    @Test
    public void testAddWorld() throws Exception {
        // Set up some test values...
        final String testName = "test";
        final WorldEnvironment testWorldEnvironment = WorldEnvironment.NETHER;
        final String testSeedString = "testseed";
        final Long testSeed = new WorldCreationSettings(testName).seed(testSeedString).seed();
        if (testSeed == null) {
            throw new NullPointerException();
        }
        final WorldType testWorldType = WorldType.FLAT;
        final boolean testGenerateStructures = false;
        final String testGenerator = "testgenerator";
        final boolean testAdjustSpawn = false;
        // Create a mock WorldManager to test the addWorld methods that take several parameters and ensure
        // that they are creating a proper WorldCreationSettings object.
        WorldManager mockWorldManager = PowerMockito.spy(new WorldManager<MultiverseWorld>(coreApi, worldUtil));
        doAnswer(new Answer<Object>() {
            @Override
            public Object answer(final InvocationOnMock invocation) throws Throwable {
                WorldCreationSettings s = (WorldCreationSettings) invocation.getArguments()[0];
                assertEquals(testName, s.name());
                assertEquals(testWorldEnvironment, s.env());
                assertEquals(testSeed, s.seed());
                assertEquals(testWorldType, s.type());
                assertEquals(testGenerateStructures, s.generateStructures());
                assertEquals(testGenerator, s.generator());
                assertEquals(testAdjustSpawn, s.adjustSpawn());
                return worldUtil.createWorld(s);
            }
        }).when(mockWorldManager).addWorld(any(WorldCreationSettings.class));
        mockWorldManager.addWorld(testName, testWorldEnvironment, testSeedString, testWorldType, testGenerateStructures, testGenerator, testAdjustSpawn);

        MultiverseWorld w = worldManager.addWorld(testName, testWorldEnvironment, testSeedString, testWorldType, testGenerateStructures, testGenerator, testAdjustSpawn);
        assertEquals(testName, w.getName());
        assertEquals(testWorldEnvironment, w.getEnvironment());
        assertEquals(testSeed.longValue(), w.getSeed());
        assertEquals(testWorldType, w.getWorldType());
        assertEquals(testGenerator, w.getGenerator());
        assertEquals(testAdjustSpawn, w.getAdjustSpawn());
    }

    @Test
    public void testIsLoaded() throws Exception {

    }

    @Test
    public void testIsManaged() throws Exception {

    }

    @Test
    public void testGetWorld() throws Exception {

    }

    @Test
    public void testGetWorlds() throws Exception {

    }

    @Test
    public void testLoadWorld() throws Exception {

    }

    @Test
    public void testUnloadWorld() throws Exception {

    }

    @Test
    public void testRemovePlayersFromWorld() throws Exception {

    }

    @Test
    public void testGetUnloadedWorlds() throws Exception {

    }
}