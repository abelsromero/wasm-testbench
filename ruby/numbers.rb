puts "Hello from Ruby! 💎"

values_to_generate = 1_000
values = []
for _ in 1..values_to_generate do
  random_value = rand()
  puts "Here is a random value: #{random_value}"
  values << random_value
end

puts "values #{values.length}"
