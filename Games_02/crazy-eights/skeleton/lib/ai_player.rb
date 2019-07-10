# Represents a computer Crazy Eights player.
class AIPlayer
  attr_reader :cards

  # Creates a new player and deals them a hand of eight cards.
  def self.deal_player_in(deck)
    AIPlayer.new(deck.take(8))
  end

  def initialize(cards)
    @cards = cards
  end

  # Returns the suit the player has the most of; this is the suit to
  # switch to if player gains control via eight.
  def favorite_suit
    suit_counts = Hash.new(0)
    cards.each { |card| suit_counts[card.suit] += 1 }
    fav_suite, _ = suit_counts.max_by { |k, v| v }
    fav_suite
    #hash.max_by{|k,v| v}
  end

  # Plays a card from hand to the pile, removing it from the hand. Use
  # the pile's `#play` and `#play_eight` methods.
  def play_card(pile, card)
    raise 'cannot play card outside your hand' if !@cards.include?(card)
    if card.value == :eight
      pile.play_eight(card, self.favorite_suit)
    else
      pile.play(card)
    end
    cards.delete(card)
  end

  # Draw a card from the deck into player's hand.
  def draw_from(deck)
    @cards.concat(deck.take(1))
  end

  # Choose any valid card from the player's hand to play; prefer
  # non-eights to eights (save those!). Return nil if no possible
  # play. Use `Pile#valid_play?` here; do not repeat the Crazy Eight
  # rules written in the `Pile`.
  def choose_card(pile)
    cards.find { |card| pile.valid_play?(card) && card.value != :eight } ||
      cards.find { |card| card.value == :eight }
  
  end

  # Try to choose a card; if AI has a valid play, play the card. Else,
  # draw from the deck and try again until there is a valid play.
  # If deck is empty, pass.
  def play_turn(pile, deck)
    chosen = choose_card(pile)

    until chosen || deck.empty?
      draw_from(deck)
      chosen = choose_card(pile)
    end

    play_card(pile, chosen) if chosen
  end
end
