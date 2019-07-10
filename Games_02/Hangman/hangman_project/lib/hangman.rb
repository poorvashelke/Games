class Hangman
  DICTIONARY = ["cat", "dog", "bootcamp", "pizza"]

  def self.random_word
    DICTIONARY.sample
  end

  def initialize
    @secret_word = Hangman.random_word
    @guess_word = Array.new(@secret_word.length, '_')
    @attempted_chars = []
    @remaining_incorrect_guesses = 5
  end

  def guess_word
    @guess_word
  end

  def attempted_chars
    @attempted_chars
  end

  def remaining_incorrect_guesses
    @remaining_incorrect_guesses
  end

  def already_attempted?(char)
    @attempted_chars.include?(char)
  end

  def get_matching_indices(char)
    found = []
    (0...@secret_word.length).each do |i|
      found << i if @secret_word[i] == char
    end
    found
  end

  def fill_indices(char, pos)
    pos.each do |i|
      @guess_word[i] = char
    end
  end

  def try_guess(char)
    # if self.already_attempted?(char)
    #   puts "that has already been attempted"
    #   return false
    # end
    # @attempted_chars << char
    # matches = self.get_matching_indices(char)
    # self.fill_indices(char, matches)
    # @remaining_incorrect_guessess -= 1 if matches.empty?
    # true
    if self.already_attempted?(char)
      puts "that char has already been guessed"
      return false
    end

    @attempted_chars << char

    matching_indices = self.get_matching_indices(char)
    if matching_indices.empty?
      @remaining_incorrect_guesses -= 1
    else
      self.fill_indices(char, matching_indices)
    end

    true
  end

  def ask_user_for_guess
    puts "Enter a char:"
    self.try_guess(gets.chomp)
  end

  def win?
    if @secret_word == @guess_word.join("")
      puts "WIN"
      return true
    end
    false
  end

  def lose?
    if @remaining_incorrect_guesses == 0
      puts "LOSE"
      return true
    end
    false
  end

  def game_over?
    if self.win? || self.lose?
      puts "Word was: #{@secret_word}"
      return true
    end
    false
  end
end
