# -*- coding: utf-8 -*-

class Card
  SUIT_STRINGS = {
    clubs: '♣',
    diamonds: '♦',
    hearts: '♥',
    spades: '♠'
  }

  VALUE_STRINGS = {
    ace: 'A',
    deuce: '2',
    three: '3',
    four: '4',
    five: '5',
    six: '6',
    seven: '7',
    eight: '8',
    nine: '9',
    ten: '10',
    jack: 'J',
    queen: 'Q',
    king: 'K'
  }

  # Returns an array of all suits.
  def self.suits
    SUIT_STRINGS.keys
  end

  # Returns an array of all values.
  def self.values
    VALUE_STRINGS.keys
  end

  def self.by_suit(suit)
    values.map { |val| Card.new(suit, val) }
  end

  def self.all_cards
    cards = []
    suits.each do |suit|
      values.each do |value|
        cards << Card.new(suit, value)
      end
    end
    cards
  end

  attr_reader :suit, :value

  def initialize(suit, value)
    raise "Bad initialization arguments" unless Card.suits.include?(suit) && Card.values.include?(value)
    
    @suit = suit
    @value = value
  end

  def color
    suit == :clubs || suit == :spades ? :black : :red
  end

  # Returns an integer value of card's rank (aces low). For example:
  # 2 of Clubs rank: 2. Jack of Hearts: 11. Ace of Spades: 1
  def rank
    if value == :ace
      1
    elsif value == :jack
      11
    elsif value == :queen
      12
    elsif value == :king
      13
    else
      VALUE_STRINGS[value].to_i
    end
  end

  # Compares two cards to see if they're equal in suit & value.
  # DO NOT CHANGE THIS METHOD
  def ==(other)
    other.is_a?(Card) && value == other.value && suit == other.suit
  end

  def to_s
    "#{VALUE_STRINGS[value]} #{SUIT_STRINGS[suit]}"
  end
end
