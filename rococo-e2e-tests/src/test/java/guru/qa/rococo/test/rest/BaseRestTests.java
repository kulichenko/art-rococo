package guru.qa.rococo.test.rest;

import guru.qa.rococo.jupiter.annotations.RestTest;

@RestTest
public abstract class BaseRestTests {

    protected static final String ID_REGEXP = "[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}";

//    @RegisterExtension
//    private static ApiLoginExtension apiLoginExtension = ApiLoginExtension.create(false);
}
