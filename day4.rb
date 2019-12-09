def valid_numbers(input, &block)
  num_start, num_end = input.split('-').map(&:to_i)
  valid = []
  num_start.upto(num_end) do |num|
    increasing = true
    num.digits.each_cons(2) { |a, b| increasing &&= (b <= a) }
    if increasing
      frequencies = num.digits.inject(Hash.new(0)) { |hash, val| hash[val] += 1; hash }
      valid << num if frequencies.values.any? block
    end
  end
  valid
end

def p2019_04_part1(input)
  nums = valid_numbers(input) { |v| v > 1 }
  nums.count
end

def p2019_04_part2(input)
  nums = valid_numbers(input) { |v| v == 2 }
  nums.count
end
