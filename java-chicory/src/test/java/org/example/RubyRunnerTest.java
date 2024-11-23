package org.example;

import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * WIP
 */
class RubyRunnerTest {

    public static final String RUBY_RUNTIME_PATH = "../runtimes/ruby.wasm";
    public static final String RUBY_SCRIPT = "../ruby/hello.rb";

    // TODO Needs extra memory only for constructor
    @Test
    void should_load_Ruby_runtime() {

        // wasmtime run --dir ruby runtimes/ruby.wasm ruby/hello.rb
//        final RubyRunner runner = new RubyRunner(RUBY_RUNTIME_PATH);
//        runner.run(RUBY_SCRIPT);

        ChicoryModuleRunner runner = new ChicoryModuleRunner();
        String output = runner.run(RUBY_RUNTIME_PATH, List.of("-h"));

        System.out.println("cosa");
    }

}
