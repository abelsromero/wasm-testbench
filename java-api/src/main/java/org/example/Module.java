package org.example;

import java.util.List;

/**
 * Runs WASM module.
 */
interface Module {

    String run(String modulePath);

    String run(String modulePath, List<String> args);

}
