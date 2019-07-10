class Code
  POSSIBLE_PEGS = {
    "R" => :red,
    "G" => :green,
    "B" => :blue,
    "Y" => :yellow
  }
  def self.valid_pegs?(chars)
    chars.all? {|char| POSSIBLE_PEGS.has_key?(char.upcase)}
    # ORRRR chars.all? {|char| Code::POSSIBLE_PEGS.has_key?(char.upcase)}
  end

  def self.random(n)
    random = []
    n.times {random << POSSIBLE_PEGS.keys.sample}
    Code.new(random)
  end

  def self.from_string(str)
    Code.new(str.split(""))
  end

  def initialize(chars)
    if Code.valid_pegs?(chars)
      @pegs = chars.map {|char| char.upcase}
    else
      raise "invalid"
    end
  end

  def pegs
    @pegs
  end

  def [](index)
    @pegs[index]
  end

  def length
    @pegs.length
  end

  def num_exact_matches(guess)
    count = 0
    (0...guess.length).each do |i|
      count += 1 if @pegs[i] == guess[i]
    end
    count
  end

  def num_near_matches(guess)
    count = 0
    (0...guess.length).each do |i|
      count += 1 if @pegs[i] != guess[i] && @pegs.include?(guess[i])
    end
    count
  end

  def ==(another_code)
    self.pegs == another_code.pegs
  end
end
