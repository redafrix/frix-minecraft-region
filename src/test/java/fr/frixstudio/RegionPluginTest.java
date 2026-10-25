package fr.frixstudio;

import java.io.File;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;
import static org.junit.jupiter.api.Assertions.*;

class RegionPluginTest {
    private ServerMock server;
    @BeforeEach void start() {
        server = MockBukkit.mock();
        MockBukkit.loadWith(RegionPlugin.class, new File("src/main/resources/plugin.yml"));
    }
    @AfterEach void stop() { MockBukkit.unmock(); }
    @Test void regionPluginLoadsAndRestrictsAdministrativePermission() {
        assertTrue(server.getPluginManager().isPluginEnabled("FrixRegionExample"));
        assertFalse(server.addPlayer().hasPermission("frixregion.admin"));
    }
    @Test void consoleCanReloadTheConfiguredRegion() {
        assertTrue(server.dispatchCommand(server.getConsoleSender(), "frixregion reload"));
    }
}
