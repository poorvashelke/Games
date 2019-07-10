class Hand
  # This is a *factory method* that creates and returns a `Hand`
  # object.
  def self.deal_from(deck)
    Hand.new(deck.take(2))
  end

  attr_accessor :cards

  def initialize(cards)
    @cards = cards
  end

  def points
    add = 0
    ace = 0

    @cards.each do |card|
      if card.value == :ace
        add += 1
        ace += 1
      else
        add += card.blackjack_value
      end
    end

    ace.times do
      add += 10 if add + 10 <= 21
    end

    add
  end

  def busted?
    points > 21
  end

  def hit(deck)
    raise "already busted" if busted?
    @cards.concat(deck.take(1))
  end

  def beats?(other_hand)
    return false if busted?
    other_hand.points < self.points || other_hand.busted?
  end

  def return_cards(deck)
    deck.return(@cards)
    @cards = [] 
  end

  def to_s
    @cards.join(",") + " (#{points})"
  end
end