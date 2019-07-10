class Board

    def self.print_grid(arr)
        arr.each do |row|
            puts row.join(" ")
        end
    end

    def initialize(n)
        @grid = Array.new(n){Array.new(n, :N)}
        @size = n * n
    end

    def size
        @size
    end

    def [](pos)
        x, y = pos
        @grid[x][y]
    end

    def []=(pos, val)
        x, y = pos
        @grid[x][y] = val
    end

    def num_ships
        @grid.flatten.count(:S)
    end

    def attack(pos)
        if self[pos] == :S
            self[pos] = :H
            puts " you sunk my battleship!"
            return true
        else
            self[pos] = :X
            return false
        end
    end


    def place_random_ships
        max = self.size * 0.25
        
        while self.num_ships < max
            row = rand(0...@grid.length)
            col = rand(0...@grid.length)
            pos = [row, col]
            self[pos] = :S
        end
    end


    def hidden_ships_grid
        new_grid = @grid.map do |row|
            row.map do |ele|
                if ele == :S
                    :N
                else
                    ele
                end
            end
        end
        new_grid
    end

    def cheat
        Board.print_grid(@grid)
    end

    def print
        Board.print_grid(self.hidden_ships_grid)
    end
end
