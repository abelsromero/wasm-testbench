use std::env;
use rand::Rng;

const VALUES_TO_GENERATE: i32 = 1_000;

fn main() {
    let args: Vec<String> = env::args().collect();
    dbg!(args);

    println!("Hello from Rust! 🦀");

    let mut rng = rand::thread_rng();

    let mut values = Vec::new();
    for _ in 0..VALUES_TO_GENERATE {
        let random_value = rng.gen::<u32>();
        println!("Here is a random value: {}", random_value);
        values.push(random_value);
    }
    println!("Values generated: {}", values.len());
}
