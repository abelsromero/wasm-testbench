// no_mangle is required to make the method available as Chicory export
#[no_mangle]
fn add(left: u64, right: u64) -> u64 {
    left + right
}
