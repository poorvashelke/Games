require_relative "card"

class Game
  attr_reader :free_cells, :foundations, :tableau, :move_history

  def initialize
    @free_cells = Array.new(4) { FreeCell.new }
    @foundations = Foundation.all_foundations
    @tableau = Array.new(8) { Tableau.new }
    deal_tableau
    @move_history = []
  end

  def deal_tableau
    all_cards = Card.all_cards
    loop do
      tableau.each do |pile|
        pile << all_cards.pop
        return if all_cards.empty?
      end
    end
  end

  def move!(from_pile, to_pile)
    to_pile << from_pile.draw
  end

  def move(from_pile, to_pile)
    card = from_pile.top_card
    
    raise "invalid move" unless to_pile.valid_move?(card)
    move!(from_pile, to_pile)
    move_history << [from_pile, to_pile]
  end

  def undo_move
    raise "no moves to undo" if move_history.empty?
    to_pile, from_pile = move_history.pop
    move!(from_pile, to_pile)
  end

  def valid_move?(from_pile, to_pile)
    return false if from_pile.empty?

    to_pile.valid_move?(from_pile.top_card)
  end

  def won?
    foundations.all?(&:complete?)
  end
end
