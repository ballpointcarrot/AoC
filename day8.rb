module Day8
  WIDTH = 25
  HEIGHT = 6

  def self.image_layers(input)
    layer_counts = []
    input.split('').each_slice(WIDTH*HEIGHT) do |layer|
      layer_counts << layer
    end
    layer_counts
  end

  def self.fewest_zeroes(layers)
    frequencies = layers.map do |layer|
      layer.inject(Hash.new(0)) {|hash, n| hash[n] += 1; hash}
    end
    frequencies.sort_by {|l| l['0']}
  end

  def self.merge_layers(layers)
    layers.inject([]) do |top, layer|
      top = layer if top.empty?
      top.zip(layer).map do |p1, p2|
        if ['0','1'].include? p1
          p1
        elsif ['0','1'].include? p2
          p2
        else
          p1
        end
      end
    end
  end

  def self.print_layer(layer)
    layer.each_slice(WIDTH) {|line| puts line.join.gsub('0',' ')}
  end
end

# Execution
layers = Day8.image_layers(File.read('resources/2019-08-input').strip)
lowest = Day8.fewest_zeroes(layers)[0]

puts lowest['1'] * lowest['2']
puts ""
Day8.print_layer(Day8.merge_layers(layers))
