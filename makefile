# Variables
RUST_PATH := rust
RUST_FLAGS := --manifest-path $(RUST_PATH)/Cargo.toml
RUST_OUT := $(RUST_PATH)/target/wasm32-wasip1/release/random_numbers.wasm
JAVA_OUT := java-counterpart/build/libs/java-counterpart.jar

# Requirements
RUBY_WASM_URL := https://github.com/ruby/ruby.wasm/releases/download/2.7.0/ruby.wasm

clean:
	cargo clean $(RUST_FLAGS)
	./gradlew clean

build $(RUST_OUT):
	cargo build --bin random_numbers --release --target wasm32-wasip1 $(RUST_FLAGS)
	cargo build --bin hello --release --target wasm32-wasip1 $(RUST_FLAGS)

ifeq (run_wasmtime, $(firstword $(MAKECMDGOALS)))
  runargs := $(wordlist 2, $(words $(MAKECMDGOALS)), $(MAKECMDGOALS))
  $(eval $(runargs):;@true)
endif

rubywasm_download:
	$(eval TMP_DIR := $(shell mktemp -d))
	curl -sL --output-dir $(TMP_DIR) -O $(RUBY_WASM_URL)
	mv $(TMP_DIR)/ruby.wasm runtimes

# Run Ruby example with ruby.wasm

run_rubywasm:
	wasmtime --dir ruby ./runtimes/random_numbers.wasm -- ruby/hello.rb
# Run Rust examples

run_wasmtime: $(RUST_OUT)
	wasmtime run $(RUST_OUT) $(runargs)

run_wasmer: $(RUST_OUT)
	wasmer $(RUST_OUT)

java_build $(JAVA_OUT):
	./gradlew :java-counterpart:build

java_run: $(JAVA_OUT)
	java -jar $(JAVA_OUT)
