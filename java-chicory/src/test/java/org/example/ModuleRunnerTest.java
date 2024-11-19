package org.example;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ModuleRunnerTest {

    private static final String MODULES_PATH = "../rust/target/wasm32-wasip1/release/";
    private static final String NUMBERS_MODULE = MODULES_PATH + "random_numbers.wasm";
    private static final String HELLO_MODULE = MODULES_PATH + "hello.wasm";

    private final ModuleRunner runner = new ModuleRunner();

    @Test
    void should_run_Rust_simple_cli() {
        String output = runner.run(NUMBERS_MODULE);

        assertThat(output)
            .startsWith("Hello from Rust!")
            .endsWith("Values generated: 1000\n");
    }

    @Test
    void should_run_Rust_with_args() {
        String output = runner.run(HELLO_MODULE, List.of("Testy"));

        assertThat(output).isEqualTo("Hello Testy!\n");
    }
}
