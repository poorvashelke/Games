# FreeCell

## Rules

* You have **1 hour** for the assessment. Do not worry if you do not
  complete the entire assessment; finish as much as you can.
* Descriptions of each method to implement are written into the `lib/`
  files. For each `lib/` file, there is a corresponding `spec/` file.
* Run the specs as you solve the assessment. Solve the assessment in
  this order:

```
bundle exec rspec spec/card_spec.rb
bundle exec rspec spec/pile_spec.rb
bundle exec rspec spec/pile_freecell_spec.rb
bundle exec rspec spec/pile_foundation_spec.rb
bundle exec rspec spec/pile_tableau_spec.rb
bundle exec rspec spec/game_spec.rb
```

## Hint

* **USE THE SPECIFIED API**. We have carefully guided you on the methods you'll
  need in the files to let you easily write each class. Use these methods;
  you'll see that we use `expect(...).to receive(...)` in some files to setup
  objects for the test.

## Game Rules

* The goal is to move all the cards from the Tableau piles to the Foundation
  piles by moving one card at a time.

0. The cards are dealt evenly among the Tableau piles at the beginning of the
   game.
1. A card can be moved to a FreeCell pile if the receiving pile is empty.
2. A card can be moved to a Tableau pile if the top card is of the opposite
   color and has a value of one higher (i.e., if the top card of the pile is the
   Jack of clubs, we could move the ten of hearts onto the pile).
3. The first card moved to a Foundation pile must be an ace of the correct suit.
4. Additional cards can be moved to a Foundation pile if the card is of the
   correct suit and has a value one higher than the top card (i.e., if the top
   card of the pile is the two of hearts, we could move the three of hearts onto
   the pile).
5. The game is won when all of the cards have been moved to the Foundation
   piles.

