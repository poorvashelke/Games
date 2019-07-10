require_relative "pile"

class Tableau < Pile
  def to_s
    empty? ? "[  ]" : "[#{cards.join(', ')}]"
  end

  def valid_move?(card)
    return true if empty?

    if card.color != top_card.color && card.rank == top_card.rank - 1
      true
    else
      false
    end
  end
end
