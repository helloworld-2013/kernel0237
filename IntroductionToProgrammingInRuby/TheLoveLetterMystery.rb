# Solution to HackerRank problem : The Love-Letter Mystery
class TheLoveLetterMystery
  def read_inputs
    @total_inputs = gets.to_i
    @all_inputs = Array.new(@total_inputs)

    input_idx = 0
    while input_idx < @total_inputs
      @all_inputs[input_idx] = gets.chomp
      input_idx += 1
    end
  end

  def solve_problem
  	input_idx = 0
    while input_idx < @total_inputs
      something = @all_inputs[input_idx]

      total = 0
      start_idx = 0
      end_idx = something.length - 1

      while start_idx != end_idx
        total += (something[start_idx].ord - something[end_idx].ord).abs

        start_idx += 1
        end_idx -= 1

        if end_idx < start_idx 
          break
        end
      end

      input_idx += 1
      puts total
  	end
  end
end

love = TheLoveLetterMystery.new
love.read_inputs
love.solve_problem
