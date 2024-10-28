package se233.project2;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({GameLoopTest.class, MovingObjectTest.class, PlayerShipTest.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class JUnitTestSuite {
//    @BeforeAll
//    public static void initJfxRuntime() {
//        javafx.application.Platform.startup(() -> {}); }

}
