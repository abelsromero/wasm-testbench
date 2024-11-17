use std::env;

fn main() {
    let args: Vec<String> = env::args().collect();
    dbg!(&args);

    if let Some(first_arg) = args.get(0) {
        println!("Hello {}!", first_arg);
    } else {
        println!("Hello World!");
    }
}
