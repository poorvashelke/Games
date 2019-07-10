require_relative "board"
require_relative "player"

class Battleship

    def initialize(n)
        @player = Player.new()
        @board = Board.new(n)
        @remaining_misses = @board.size / 2
    end

    def board
        @board
    end

    def player
        @player
    end

    def start_game
        @board.place_random_ships
        puts "There are #{@board.num_ships} hidden ships on the board."
        @board.print
    end

    def lose?
        if @remaining_misses <= 0
            puts "you lose"
            return true
        else
            return false
        end
    end

    def win?
        num = @board.num_ships
        if num == 0
            puts "you win"
            return true
        end
        false
    end

    def game_over?
        return true if self.win? || self.lose?
        false
    end

    def turn
        pos = @player.get_move
        @remaining_misses -= 1 if !@board.attack(pos)
        @board.print
        
        puts "remaining misses: #{@remaining_misses}"
    end
end
