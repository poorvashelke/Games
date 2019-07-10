require_relative "pile"

class Foundation < Pile
  def self.all_foundations
    Card.suits.map { |suit| Foundation.new(suit) }
  end

  attr_reader :suit

  def initialize(suit, cards = [])
    @suit = suit
    @cards = cards
  end

  def complete?
    count == 13
  end

  def to_s
    empty? ? "[ #{Card::SUIT_STRINGS[suit]}]" : "[#{top_card}]"
  end

  def valid_move?(card)
    return false unless card.suit == suit

    if empty? && card.value == :ace
      true
    elsif !empty? && card.rank == top_card.rank + 1
      true
    else
      false
    end
  end
end
